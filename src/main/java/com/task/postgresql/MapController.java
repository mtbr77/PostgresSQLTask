package com.task.postgresql;

import com.task.postgresql.model.Cell;
import com.task.postgresql.repository.CellRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


import java.util.Map;

@Controller
public class MapController {

        @Autowired
        private CellRepository repository;

        @GetMapping("/map")
        public String map(Map<String, Object> model) {
            Iterable<Cell> cells = repository.findAll();

            model.put("cells", cells);

            return "map";
        }
}
