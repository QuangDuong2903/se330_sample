package com.quangduong.SE330backend.mapper;

import com.quangduong.SE330backend.dto.notification.NotificationDTO;
import com.quangduong.SE330backend.entity.sql.NotificationEntity;
import org.springframework.stereotype.Component;

@Component
public class NotificationMapper {

    public NotificationDTO toDTO(NotificationEntity entity) {
        NotificationDTO dto = new NotificationDTO();
        dto.setId(entity.getId());
        dto.setAccept(entity.isAccept());
        dto.setReject(entity.isReject());
        dto.setMessage(entity.getMessage());
        dto.setRead(entity.isRead());
        dto.setThumbnail(entity.getThumbnail());
        dto.setBoardId(entity.getUser().getId());
        dto.setType(entity.getType());
        return dto;
    }

}
