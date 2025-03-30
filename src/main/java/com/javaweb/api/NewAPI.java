package com.javaweb.api;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.javaweb.bean.BuildingDTO;
import com.javaweb.bean.DetailError;
import com.javaweb.bean.InforDTO;
import com.javaweb.bean.SearchBuildingDTO;
import com.javaweb.bean.ToaNhaDTO;
import com.javaweb.entity.ToaNhaEntity;
import com.javaweb.service.BuildingService;


@RestController
@RequestMapping(value = "/api/building")
public class NewAPI {
	@Autowired
	private BuildingService buildingService;
	
	@GetMapping
	public List<BuildingDTO> findAll(@RequestParam Map<String,Object> params,
									@RequestParam(name="typeCode", required = false) List<String> typeCode) {
		List<BuildingDTO> result = buildingService.findAll(params,typeCode);
		return result;
	}
	
	@PostMapping
	public List<BuildingDTO> SearchBuilding(@RequestParam(required = false) String name) {
		List<BuildingDTO> buildings = new ArrayList<>();
		buildings = buildingService.getBuilding(name);
		return buildings;
	}
}
