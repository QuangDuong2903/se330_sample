package com.quangduong.SE330backend.repository.sql;

import com.quangduong.SE330backend.entity.LabelEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LabelRepository extends JpaRepository<LabelEntity, Long> {
}