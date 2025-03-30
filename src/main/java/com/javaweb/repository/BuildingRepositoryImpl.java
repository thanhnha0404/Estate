package com.javaweb.repository;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.javaweb.builder.BuildingSearchBuilder;
import com.javaweb.entity.BuildingEntity;
import com.javaweb.utils.ConnectionJDBCUtil;
import com.javaweb.utils.NumberUtil;
import com.javaweb.utils.StringUtil;

@Repository
public class BuildingRepositoryImpl implements BuildingRepository{
	
	@Override
	public List<BuildingEntity> getBuilding(String name){
		
		try(Connection connection = ConnectionJDBCUtil.getConnection()){
			Statement statement = connection.createStatement();
			String query = "SELECT * FROM building";
			ResultSet rs = statement.executeQuery(query);
			List<BuildingEntity> buildings = new ArrayList<>();
			
			while(rs.next()) {
				BuildingEntity buildingEntity = new BuildingEntity();
				buildingEntity.setName(rs.getString("name"));
				buildingEntity.setStreet(rs.getString("street"));
				buildingEntity.setWard(rs.getString("ward"));
				buildings.add(buildingEntity);
			}		
			return buildings;
			
		} catch(SQLException e) {
			e.printStackTrace();
			return null;
		}		
	}
	
	public void joinTable(BuildingSearchBuilder buildingSearchBuilder, StringBuilder sql) {
		String staffId = String.valueOf(buildingSearchBuilder.getStaffId());
		if(StringUtil.CheckEmpty(staffId)) {
			sql.append(" INNER JOIN assignmentbuilding ON b.id = assignmentbuilding.buildingid ");
		}
		String rentAreaForm = String.valueOf(buildingSearchBuilder.getAreaFrom());
		String rentAreaTo = String.valueOf(buildingSearchBuilder.getAreaTo());
		if(StringUtil.CheckEmpty(rentAreaForm) || StringUtil.CheckEmpty(rentAreaTo)) {
			sql.append(" INNER JOIN rentarea ON b.id = rentarea.buildingid ");
		}
		List<String> typeCode = buildingSearchBuilder.getTypeCode();
		if(typeCode != null && typeCode.size() != 0) {
			sql.append(" INNER JOIN buildingrenttype ON b.id = buildingrenttype.buildingid ");
			sql.append(" INNER JOIN renttype ON renttype.id = buildingrenttype.renttypeid ");
		}
	}
	
	public void queryNomal(BuildingSearchBuilder buildingSearchBuilder, StringBuilder where) {
//		for(Map.Entry<String, Object> it : params.entrySet()) {
//			if(!it.getKey().equals("staffId") && !it.getKey().equals("typeCode") && 
//					!it.getKey().startsWith("rentArea") && !it.getKey().startsWith("rentprice")) {
//				if(StringUtil.CheckEmpty(it.getValue().toString())) {
//					if(NumberUtil.isNumber(it.getValue().toString())) {
//						where.append(" AND b." + it.getKey() + " = " + it.getValue());
//					} else {
//						where.append(" AND b." + it.getKey() + " LIKE '%" + it.getValue() + "%' ");
//					}
//				}
//			}
//		}
		
		try {
			Field[] fields = BuildingSearchBuilder.class.getDeclaredFields();
			for(Field item : fields) {
				item.setAccessible(true);
				String fieldName = item.getName();
				if(!fieldName.equals("staffId") && !fieldName.equals("typeCode") && 
					!fieldName.startsWith("area") && !fieldName.startsWith("rentPrice")) {
					String value = String.valueOf(item.get(buildingSearchBuilder));
					if(StringUtil.CheckEmpty(value) == true) {
						if(NumberUtil.isNumber(value) == true) {
							where.append(" AND b." + fieldName + " = " + value);
						} else {
							where.append(" AND b." + fieldName + " LIKE '%" + value + "%' ");
						}
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
	}
	
	public void querySpecial(BuildingSearchBuilder buildingSearchBuilder, StringBuilder where) {
		String rentAreaFrom = String.valueOf(buildingSearchBuilder.getAreaFrom());
		if(StringUtil.CheckEmpty(rentAreaFrom)) {
			where.append(" AND rentarea.value >= " + rentAreaFrom);
		}
		
		String rentAreaTo =	String.valueOf(buildingSearchBuilder.getAreaTo());
		if(StringUtil.CheckEmpty(rentAreaTo)) {
			where.append(" AND rentarea.value <= " + rentAreaTo);
		}
		
		String staffId = String.valueOf(buildingSearchBuilder.getStaffId());
		if(StringUtil.CheckEmpty(staffId)) {
			where.append(" AND assignmentbuilding.staffid = " + staffId);
		}
		
		String rentPriceFrom = String.valueOf(buildingSearchBuilder.getRentPriceFrom());
		if(StringUtil.CheckEmpty(rentPriceFrom)) {
			where.append(" AND b.rentprice >= " + rentPriceFrom);
		}
		
		String rentPriceTo = String.valueOf(buildingSearchBuilder.getRentPriceTo());
		if(StringUtil.CheckEmpty(rentPriceTo)) {
			where.append(" AND b.rentprice <= " + rentPriceTo);
		}
		
		List<String> typeCode = buildingSearchBuilder.getTypeCode();
		if(typeCode != null && typeCode.size() != 0) {
//			List<String> code = new ArrayList<>();
//			for(String it : typeCode) {
//				code.add("'" + it + "'");
//			}
//			where.append(" AND renttype.code IN(" + String.join(",", code) + ") " );
			where.append(" AND(");
			String sql = typeCode.stream().map(it -> " renttype.code like" + "'%" + it + "%' ").collect(Collectors.joining(" OR "));
			where.append(sql);
			where.append(" ) ");
		}

	}

	@Override
	public List<BuildingEntity> findAll(BuildingSearchBuilder buildingSearchBuilder) {
		StringBuilder sql = new StringBuilder("SELECT b.id, b.name, b.districtId, b.street, b.ward, b.numberofbasement, b.floorarea, b.rentprice, b.managername, b.managerphonenumber,b.servicefee, b.brokeragefee FROM BUILDING b");
		joinTable(buildingSearchBuilder,sql);
		StringBuilder where = new StringBuilder(" WHERE 1=1 ");
		queryNomal(buildingSearchBuilder,where);
		querySpecial(buildingSearchBuilder,where);	
		where.append(" GROUP BY b.id");
		sql.append(where);
		
		try(Connection connection = ConnectionJDBCUtil.getConnection()){
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sql.toString());
			List<BuildingEntity> buildingEntities = new ArrayList<>();
			while(rs.next()) {
				BuildingEntity buildingEntity = new BuildingEntity();
				buildingEntity.setId(rs.getLong("id"));
				buildingEntity.setName(rs.getString("name"));
				buildingEntity.setStreet(rs.getString("street"));
				buildingEntity.setWard(rs.getString("ward"));
				buildingEntity.setDistrictid(rs.getLong("districtid"));
				buildingEntity.setNumberOfBasement(rs.getLong("numberOfBasement"));
				buildingEntity.setFloorArea(rs.getLong("floorarea"));
				buildingEntity.setRentPrice(rs.getLong("rentprice"));
				buildingEntity.setServiceFee(rs.getString("servicefee"));
				buildingEntity.setBrokerageFee(rs.getLong("brokeragefee"));
				buildingEntity.setManagerName(rs.getString("managername"));
				buildingEntity.setManagerPhoneNumber(rs.getString("managerphonenumber"));
				buildingEntities.add(buildingEntity);
			}
			return buildingEntities;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
}
