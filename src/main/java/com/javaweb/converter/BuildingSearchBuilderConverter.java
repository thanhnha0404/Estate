package com.javaweb.converter;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.javaweb.builder.BuildingSearchBuilder;
import com.javaweb.utils.MapUtil;

@Component
public class BuildingSearchBuilderConverter {
	public BuildingSearchBuilder toBuildingSearchBuilder(Map<String, Object> params, List<String> typeCode){
        BuildingSearchBuilder buildingSearchBuilder = new BuildingSearchBuilder.Builder()
                .setName(MapUtil.getObject(params, "name", String.class))
                .setFloorArea(MapUtil.getObject(params, "floorArea", Integer.class))
                .setNumberOfBasement(MapUtil.getObject(params, "numberOfBasement", Integer.class))
                .setWard(MapUtil.getObject(params, "ward", String.class))
                .setDistrict(MapUtil.getObject(params, "districtId", String.class))
                .setStreet(MapUtil.getObject(params, "street", String.class))
                .setManagerName(MapUtil.getObject(params, "managerName", String.class))
                .setManagerPhoneNumber(MapUtil.getObject(params, "managerPhoneNumber", String.class))
                .setDirection(MapUtil.getObject(params, "direction", String.class))
                .setLevel(MapUtil.getObject(params, "level", String.class))
                .setRentPriceFrom(MapUtil.getObject(params, "rentPriceFrom", Integer.class))
                .setRentPriceTo(MapUtil.getObject(params, "rentPriceTo", Integer.class))
                .setAreaFrom(MapUtil.getObject(params, "areaFrom", Integer.class))
                .setAreaTo(MapUtil.getObject(params, "areaTo", Integer.class))
                .setStaffId(MapUtil.getObject(params, "staffId", Integer.class))
                .setTypeCode(typeCode)
                .build();
        return buildingSearchBuilder;
    }
}
