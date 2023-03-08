package com.pavel.repository;

import com.pavel.model.DetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DBRepository extends JpaRepository<DetailsEntity, Long> {
}
