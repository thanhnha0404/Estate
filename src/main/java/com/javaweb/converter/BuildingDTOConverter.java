package com.javaweb.converter;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.javaweb.bean.BuildingDTO;
import com.javaweb.entity.BuildingEntity;
import com.javaweb.entity.DistrictEntity;
import com.javaweb.entity.RentAreaEntity;
import com.javaweb.repository.DistrictRepository;
import com.javaweb.repository.RentAreaRepository;

@Component
public class BuildingDTOConverter {
	@Autowired
	private DistrictRepository districtRepository;
	
	@Autowired
	private RentAreaRepository rentAreaRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	public BuildingDTO toBuildingDTO(BuildingEntity buildingEntity) {
		BuildingDTO buildingDTO = modelMapper.map(buildingEntity,BuildingDTO.class);
		
		DistrictEntity district = districtRepository.getDistrictById(buildingEntity.getDistrictid());
		buildingDTO.setAddress(buildingEntity.getStreet() + ", " + buildingEntity.getWard() + ", " + district.getName());
		
		List<RentAreaEntity> rentAreas = rentAreaRepository.getValueBuildingById(buildingEntity.getId());
		String areaResult = rentAreas.stream().map(it -> it.getValue().toString()).collect(Collectors.joining(","));
		buildingDTO.setRentArea(areaResult);
		
//		buildingDTO.setBrokerageFee(buildingEntity.getBrokerageFee());
//		buildingDTO.setEmptyArea(buildingEntity.getEmptyArea());
//		buildingDTO.setFloorArea(buildingEntity.getFloorArea());
//		buildingDTO.setName(buildingEntity.getName());
//		buildingDTO.setManagerName(buildingEntity.getManagerName());
//		buildingDTO.setManagerPhoneNumber(buildingEntity.getManagerPhoneNumber());
		
		return buildingDTO;
	}
	
}
