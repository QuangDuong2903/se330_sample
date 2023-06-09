package com.quangduong.SE330backend.api;

import com.quangduong.SE330backend.dto.task.TaskDTO;
import com.quangduong.SE330backend.dto.task.TaskDetailsDTO;
import com.quangduong.SE330backend.dto.task.TaskUpdateDTO;
import com.quangduong.SE330backend.repository.sql.TextAttributeRepository;
import com.quangduong.SE330backend.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("tasks")
public class TaskAPI {

    @Autowired
    private TaskService taskService;

    @Autowired
    private TextAttributeRepository textAttributeRepository;

    @PostMapping
    public ResponseEntity<TaskDetailsDTO> createTask(@RequestBody @Valid TaskDTO dto) {
        return new ResponseEntity<>(taskService.createTask(dto), HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<TaskDetailsDTO> updateTask(@PathVariable("id") long id, @RequestBody TaskUpdateDTO dto) {
        dto.setId(id);
        return ResponseEntity.ok(taskService.updateTask(dto));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable("id") long id) {
        taskService.deleteTask(id);
        return ResponseEntity.ok().build();
    }
}
