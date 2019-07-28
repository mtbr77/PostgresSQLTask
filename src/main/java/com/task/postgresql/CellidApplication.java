package com.task.postgresql;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.MappingStrategy;
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
import java.lang.reflect.Field;
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

			long startTime = System.nanoTime();

			CsvToBeanBuilder<Cell> builder = new CsvToBeanBuilder(reader).withType(Cell.class);
			CsvToBean<Cell> converter = builder.build();
			Field field = builder.getClass().getDeclaredField("mappingStrategy");
			field.setAccessible(true);
			MappingStrategy<Cell> strategy = (MappingStrategy<Cell>)field.get(builder);
			CellCvsToBeanFilter csvFilter = new CellCvsToBeanFilter(strategy);
			converter.setFilter(csvFilter);
			List<Cell> cells = converter.parse();

			long elapsedTime = System.nanoTime() - startTime;
			long linesAmount = reader.getLinesRead();
			System.out.println("*************************STATISTIC****************************");
			System.out.println("lines/sec processed = " + linesAmount*1000000/elapsedTime);
			System.out.println("*************************STATISTIC****************************");
			repo.saveAll(cells);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
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
