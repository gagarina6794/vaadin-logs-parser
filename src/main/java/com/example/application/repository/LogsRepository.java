package com.example.application.repository;

import com.example.application.model.LogItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface LogsRepository extends JpaRepository<LogItem, Integer> {

}