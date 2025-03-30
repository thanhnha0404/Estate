package com.javaweb.repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.javaweb.entity.RentAreaEntity;
import com.javaweb.utils.ConnectionJDBCUtil;

@Repository
public class RentAreaRepositoryImpl implements RentAreaRepository{

	@Override
	public List<RentAreaEntity> getValueBuildingById(Long id) {
		List<RentAreaEntity> rentAreas = new ArrayList<>();
		String query = "SELECT * FROM rentarea WHERE rentarea.buildingid = " + id;
		try(Connection conn = ConnectionJDBCUtil.getConnection();
				Statement sm = conn.createStatement();
				ResultSet rs = sm.executeQuery(query);){
			while(rs.next()){
				RentAreaEntity rentArea = new RentAreaEntity();
				rentArea.setValue(rs.getString("value"));
				rentAreas.add(rentArea);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rentAreas;
	}

}
