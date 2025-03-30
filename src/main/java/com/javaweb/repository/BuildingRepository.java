package com.javaweb.repository;
import java.util.List;
import java.util.Map;

import com.javaweb.bean.SearchBuildingDTO;
import com.javaweb.builder.BuildingSearchBuilder;
import com.javaweb.entity.BuildingEntity;
import com.javaweb.entity.ToaNhaEntity;

public interface BuildingRepository {
	public List<BuildingEntity> getBuilding(String name);
	public List<BuildingEntity> findAll(BuildingSearchBuilder buildingSearchBuilder);
}
