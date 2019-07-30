package com.task.postgresql.repository;

import com.task.postgresql.model.Cell;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository()
public interface CellRepository extends JpaRepository<Cell, Long> {
    @Query(value = "select * FROM cell where lon < :lon", nativeQuery = true)
    List<Cell> findByLon(@Param("lon") double lon);
}
