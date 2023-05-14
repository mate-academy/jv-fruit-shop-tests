package service.impl;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;
import service.WriterDataService;

public class CsvFileWriterServiceTest {
    private static final String PATH_TO_FILE = "src/test/resources/OutputFile.csv";
    private static final String INVALID_PATH_TO_FILE = "src/test/resources/invalidPath.csv";
    private static WriterDataService writerDataService;
    private static StringBuilder DATA_FOR_OUTPUT;
    private static List<String> EXPECTED_DATA;

    @BeforeClass
    public static void beforeClass() {
        DATA_FOR_OUTPUT = new StringBuilder();
        EXPECTED_DATA = new ArrayList<>();
        writerDataService = new CsvFileWriterService();
        DATA_FOR_OUTPUT.append("fruit,quantity").append(System.lineSeparator())
                .append("banana,152").append(System.lineSeparator())
                .append("apple,90");
        EXPECTED_DATA.add("fruit,quantity");
        EXPECTED_DATA.add("banana,152");
        EXPECTED_DATA.add("apple,90");
    }

    @Test
    public void write_toFile_Ok() {
        writerDataService.write(DATA_FOR_OUTPUT.toString(), PATH_TO_FILE);
        List<String> actual = null;
        try {
            actual = Files.readAllLines(Path.of(PATH_TO_FILE));
        } catch (IOException e) {
            throw new RuntimeException("Test exception. Can't read file: " + PATH_TO_FILE);
        }
        assertEquals(EXPECTED_DATA, actual);
    }

    @Test
    public void write_to_invalid_file_NotOk() {
        writerDataService.write(DATA_FOR_OUTPUT.toString(), INVALID_PATH_TO_FILE);
    }
}
