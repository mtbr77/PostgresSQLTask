package com.task.postgresql.repository;

import com.task.postgresql.model.Cell;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CellRepository extends CrudRepository<Cell, Long> {
}
