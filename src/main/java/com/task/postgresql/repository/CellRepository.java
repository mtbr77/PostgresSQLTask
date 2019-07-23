package com.task.postgresql.repository;

import com.task.postgresql.model.Cell;
import org.springframework.data.repository.CrudRepository;

public interface CellRepository extends CrudRepository<Cell, Long> {
}
