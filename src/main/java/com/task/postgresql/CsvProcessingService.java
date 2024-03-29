package com.task.postgresql;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.MappingStrategy;
import com.task.postgresql.model.Cell;
import com.task.postgresql.repository.CellRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Stream;
import java.util.zip.GZIPInputStream;

import static java.lang.System.out;

@Service
public class CsvProcessingService {
    private final String csvPattern = "test_with_corrupted_data.csv.gz";

    @Value("${csvFiles.path}")
    private String csvDir;

    @Value("${dbwriters.amount}")
    private int dbwritersAmount;

    private long startTime, elapsedTime;

    private CsvToBean<Cell> csvToBeanConverter;

    @Autowired
    private CellRepository repository;
    private long lines, storedLines, skippedLines, failedLines;
    private CSVReader reader;
    private List<Cell> cells = Collections.emptyList();

    public void start() {
        try(Stream<Path> paths = Files.walk(Paths.get(csvDir))){
            paths.filter(path -> path.toString().endsWith(csvPattern))
                    .forEach(this::parseCSVFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void parseCSVFile(Path path) {
        try(BufferedReader br =	new BufferedReader(new InputStreamReader(new GZIPInputStream(new FileInputStream(path.toString()))))) {
            reader = new CSVReader(br);

            convertCsvToBeans(reader);
            printLinesStatistic();

            saveToDB(cells);
            printDBwriteStatistic();
            //readFromDB();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void convertCsvToBeans(CSVReader reader) throws IOException {
        CsvToBeanBuilder<Cell> builder = new CsvToBeanBuilder(reader).withType(Cell.class).withThrowExceptions(false);
        csvToBeanConverter = builder.build();
        MappingStrategy<Cell> strategy = getMappingStrategy(builder);
        CvsToBeanFilterImpl csvFilter = new CvsToBeanFilterImpl(strategy);
        csvToBeanConverter.setFilter(csvFilter);
        startTime = System.nanoTime();
        cells = csvToBeanConverter.parse();
        elapsedTime = System.nanoTime() - startTime;
    }

    private void saveToDB(List<Cell> cells) {
        ExecutorService pool = Executors.newFixedThreadPool(dbwritersAmount);
        startTime = System.nanoTime();
        for(Cell cell : cells) {
            pool.execute(() -> repository.save(cell));
        }
        elapsedTime = System.nanoTime() - startTime;
        pool.shutdown();
    }

    private void printDBwriteStatistic() {
        out.println("elapsed time of saving to DB = " + elapsedTime/1_000_000_000.0 + " sec");
        out.println("records/sec inserted in DB = " + (long)(storedLines * (1_000_000_000.0/elapsedTime)));
        out.println("**************************************************************");
    }

    private void printLinesStatistic() {
        lines = reader.getLinesRead();
        storedLines = cells.size();
        skippedLines = lines - storedLines;
        failedLines = csvToBeanConverter.getCapturedExceptions().size();
        out.println("*************************STATISTIC****************************");
        out.println("lines/sec processed = " + (long)(lines *(1_000_000_000.0/elapsedTime)));
        out.println("All lines = " + lines);
        out.println("failed lines = " + failedLines);
        out.println("skipped (not in filter) lines = " + skippedLines);
        out.println("lines in filter = " + storedLines);
    }

    private void readFromDB() {
        for (Cell cell : repository.findByLon(55)) {
            out.println(cell.toString());
        }
    }


    private MappingStrategy<Cell> getMappingStrategy(CsvToBeanBuilder<Cell> builder){
        MappingStrategy<Cell> strategy = null;
        try {
            Field field = builder.getClass().getDeclaredField("mappingStrategy");
            field.setAccessible(true);
            strategy = (MappingStrategy<Cell>)field.get(builder);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        return strategy;
    }
}
