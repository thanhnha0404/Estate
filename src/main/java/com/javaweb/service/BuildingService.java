package com.javaweb.service;
import java.util.List;
import java.util.Map;

import com.javaweb.bean.BuildingDTO;

public interface BuildingService {
	public List<BuildingDTO> getBuilding(String name);
	public List<BuildingDTO> findAll(Map<String,Object> params, List<String> typeCode);
}
