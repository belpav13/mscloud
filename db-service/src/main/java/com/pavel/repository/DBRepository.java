package com.pavel.repository;


import com.pavel.contracts.model.DetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DBRepository extends JpaRepository<DetailsEntity, Long> {
}
