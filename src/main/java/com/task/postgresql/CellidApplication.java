package com.task.postgresql;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBeanBuilder;
import com.task.postgresql.model.Cell;
import com.task.postgresql.repository.CellRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

@SpringBootApplication
@Component("CellidApplication")
public class CellidApplication {
	private static final String PROJECTDIR = System.getProperty("user.dir");
	private static final String pattern = ".csv";

	private static CellRepository repo;

	@Autowired
	public void setRepo(CellRepository repo){
		CellidApplication.repo = repo;
	}

	private static void parseCSVFile(Path path) {
		try(BufferedReader bReader = Files.newBufferedReader(path)){
			CSVReader reader = new CSVReader(bReader);
			List<Cell> cells = new CsvToBeanBuilder(reader).withType(Cell.class).build().set.parse();
			repo.saveAll(cells);
		} catch (IOException e) {
			e.printStackTrace();
		}


	}

	public static void main(String[] args) {
		SpringApplication.run(CellidApplication.class, args);

		try(Stream<Path> paths = Files.walk(Paths.get(PROJECTDIR))){

			paths
					.filter(path -> path.toString().endsWith(pattern))
					.forEach(CellidApplication::parseCSVFile);




		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public CellidApplication() {

	}
}
