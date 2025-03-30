package com.javaweb.repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.stereotype.Repository;

import com.javaweb.entity.DistrictEntity;
import com.javaweb.entity.RentAreaEntity;
import com.javaweb.utils.ConnectionJDBCUtil;

@Repository
public class DistrictRepositoryImpl implements DistrictRepository{

	@Override
	public DistrictEntity getDistrictById(Long id) {
		DistrictEntity districtEntity = new DistrictEntity();
		String query = "SELECT * FROM district where district.id = " + id;
		try(Connection conn = ConnectionJDBCUtil.getConnection();
				Statement sm = conn.createStatement();
				ResultSet rs = sm.executeQuery(query);){
			while(rs.next()){
				districtEntity.setName(rs.getString("name"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return districtEntity;
	}

}
