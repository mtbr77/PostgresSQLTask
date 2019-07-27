package com.task.postgresql;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBeanBuilder;
import com.task.postgresql.model.Cell;
import com.task.postgresql.repository.CellRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;
import java.util.zip.GZIPInputStream;

@SpringBootApplication
@Component("CellidApplication")
public class CellidApplication {
	private static String csvDir;

	private static final String csvPattern = ".csv.gz";

	private static CellRepository repo;

	@Autowired
	public void setRepo(CellRepository repo){
		CellidApplication.repo = repo;
	}

	@Value("${csvFiles.path}")
	public void setCsvDir(String csvDir) {
		CellidApplication.csvDir = csvDir;
	}

	private static void parseCSVFile(Path path) {
		try(BufferedReader br =	new BufferedReader(new InputStreamReader(new GZIPInputStream(new FileInputStream(path.toString()))))) {
			CSVReader reader = new CSVReader(br);
			//reader.getLinesRead();
			long startTime = System.nanoTime();

			CellCvsToBeanFilter csvFilter = new CellCvsToBeanFilter(reader);
			List<Cell> cells = new CsvToBeanBuilder(reader).withType(Cell.class).withFilter(csvFilter).build()/*.forEach();setFilter()*/.parse();

			long elapsedTime = System.nanoTime() - startTime;
			//System.out.println("lines/sec processed = " + linesAmount*1000000/elapsedTime);
			repo.saveAll(cells);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		SpringApplication.run(CellidApplication.class, args);

		try(Stream<Path> paths = Files.walk(Paths.get(csvDir))){
			paths.filter(path -> path.toString().endsWith(csvPattern))
				 .forEach(CellidApplication::parseCSVFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
