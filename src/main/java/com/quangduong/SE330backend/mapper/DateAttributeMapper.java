package com.quangduong.SE330backend.mapper;

import com.quangduong.SE330backend.dto.attribute.DateAttributeDTO;
import com.quangduong.SE330backend.entity.sql.DateAttributeEntity;
import org.springframework.stereotype.Component;

@Component
public class DateAttributeMapper {

    public DateAttributeEntity toEntity(DateAttributeDTO dto) {
        DateAttributeEntity entity = new DateAttributeEntity();
        entity.setName(dto.getName());
        entity.setValue(dto.getValue());
        return entity;
    }

    public DateAttributeEntity toEntity(DateAttributeDTO dto, DateAttributeEntity entity) {
        entity.setName(dto.getName());
        entity.setValue(dto.getValue());
        return entity;
    }

    public DateAttributeDTO toDTO(DateAttributeEntity entity) {
        DateAttributeDTO dto = new DateAttributeDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(entity.getValue());
//        calendar.add(Calendar.HOUR_OF_DAY, - 7);
//        dto.setValue(calendar.getTime());
        dto.setValue(entity.getValue());
        return dto;
    }
}
