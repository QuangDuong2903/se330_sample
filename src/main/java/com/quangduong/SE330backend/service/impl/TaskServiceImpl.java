package com.quangduong.SE330backend.service.impl;

import com.quangduong.SE330backend.dto.task.TaskDTO;
import com.quangduong.SE330backend.dto.task.TaskDetailsDTO;
import com.quangduong.SE330backend.dto.task.TaskUpdateDTO;
import com.quangduong.SE330backend.entity.sql.TaskEntity;
import com.quangduong.SE330backend.exception.NoPermissionException;
import com.quangduong.SE330backend.exception.ResourceNotFoundException;
import com.quangduong.SE330backend.mapper.TaskMapper;
import com.quangduong.SE330backend.repository.sql.*;
import com.quangduong.SE330backend.service.TaskService;
import com.quangduong.SE330backend.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private SecurityUtils securityUtils;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TextAttributeRepository textAttributeRepository;

    @Autowired
    private NumberAttributeRepository numberAttributeRepository;

    @Autowired
    private DateAttributeRepository dateAttributeRepository;

    @Autowired
    private LabelAttributeRepository labelAttributeRepository;

    @Autowired
    private TaskMapper taskMapper;

    @Override
    @Transactional
    public TaskDetailsDTO createTask(TaskDTO dto) {
        TaskEntity entity = taskRepository.save(taskMapper.toEntity(dto));
        entity.getTextAttributes().forEach(m -> m.setTask(entity));
        entity.getNumberAttributes().forEach(m -> m.setTask(entity));
        entity.getDateAttributes().forEach(m -> m.setTask(entity));
        entity.getLabelAttributes().forEach(m -> m.setTask(entity));
        return taskMapper.toDetailsDTO(entity);
    }

    @Override
    @Transactional
    public TaskDetailsDTO updateTask(TaskUpdateDTO dto) {
        TaskEntity entity = taskRepository.findById(dto.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Not found task with id: " + dto.getId()));
        List<Long> textAttributeIds = entity.getTextAttributes().stream().map(t -> t.getId()).toList();
        List<Long> numberAttributeIds = entity.getNumberAttributes().stream().map(t -> t.getId()).toList();
        List<Long> dateAttributeIds = entity.getDateAttributes().stream().map(t -> t.getId()).toList();
        List<Long> labelAttributeIds = entity.getLabelAttributes().stream().map(t -> t.getId()).toList();
        entity = taskMapper.toEntity(dto, entity);
        TaskEntity finalEntity = entity;
        textAttributeIds.forEach(i -> {
            if (finalEntity.getTextAttributes().stream().noneMatch(t -> t.getId() == i))
                textAttributeRepository.deleteById(i);
        });
        numberAttributeIds.forEach(i -> {
            if (finalEntity.getNumberAttributes().stream().noneMatch(t -> t.getId() == i))
                numberAttributeRepository.deleteById(i);
        });
        dateAttributeIds.forEach(i -> {
            if (finalEntity.getDateAttributes().stream().noneMatch(t -> t.getId() == i))
                dateAttributeRepository.deleteById(i);
        });
        labelAttributeIds.forEach(i -> {
            if (finalEntity.getLabelAttributes().stream().noneMatch(t -> t.getId() == i))
                labelAttributeRepository.deleteById(i);
        });
        taskRepository.save(entity);
        return taskMapper.toDetailsDTO(taskRepository.findById(dto.getId()).get());
    }

    @Override
    @Transactional
    public void deleteTask(long id) {
        TaskEntity entity = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found task with id: " + id));
        if (entity.getTable().getMembers().stream().noneMatch(m -> m.getId() == securityUtils.getCurrentUserId())
                && !entity.getTable().getCreatedBy().equals(securityUtils.getCurrentUser().getEmail()))
            throw new NoPermissionException("Not allowed");
        taskRepository.deleteById(id);
    }
}
