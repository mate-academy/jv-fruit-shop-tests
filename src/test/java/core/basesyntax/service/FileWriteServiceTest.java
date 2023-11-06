package core.basesyntax.service;

import core.basesyntax.service.imp.FileWriteServiceImp;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FileWriteServiceTest {
    private static final String PATH_CONSUMER_FILE = "TestConsumerFile.csv";
    private static final String LINE_SEPARATOR = System.lineSeparator();
    private static final String REPORT_HEAD = "fruit,quantity";
    private static final String STOCK_BANANA_QUANTITY = "banana,50";
    private static final String STOCK_APPLE_QUANTITY = "apple,100";

    private static FileWriteService fileWriteService;
    private static File file;

    @BeforeAll
    static void beforeAll() {
        fileWriteService = new FileWriteServiceImp();
        file = new File(PATH_CONSUMER_FILE);
    }

    @AfterAll
    static void afterAll() {
        try {
            Files.deleteIfExists(Path.of(PATH_CONSUMER_FILE));
        } catch (IOException e) {
            throw new RuntimeException("Can't correctly clear result files after test ", e);
        }
    }

    @Test
    void writeCsvToFile_allValid_ok() {
        List<String> inputData = List.of(
                REPORT_HEAD + LINE_SEPARATOR,
                STOCK_BANANA_QUANTITY + LINE_SEPARATOR,
                STOCK_APPLE_QUANTITY + LINE_SEPARATOR);
        List<String> expectedList = List.of(REPORT_HEAD,
                STOCK_BANANA_QUANTITY,
                STOCK_APPLE_QUANTITY);
        fileWriteService.writeCsvToFile(inputData, PATH_CONSUMER_FILE);
        List<String> actualList = readFile(PATH_CONSUMER_FILE);
        for (int i = 0; i < expectedList.size(); i++) {
            Assertions.assertEquals(expectedList.get(i), actualList.get(i));
        }
    }

    @Test
    void writeCsvToFile_emptyData_ok() {
        List<String> inputData = List.of(REPORT_HEAD);
        List<String> expectedList = List.of(REPORT_HEAD);
        fileWriteService.writeCsvToFile(inputData, PATH_CONSUMER_FILE);
        List<String> actualList = readFile(PATH_CONSUMER_FILE);
        for (int i = 0; i < actualList.size(); i++) {
            Assertions.assertEquals(expectedList.get(i), actualList.get(i));
        }
    }

    @Test
    void writeCsvToFile_nullData_notOk() {
        Assertions.assertThrows(NullPointerException.class,
                () -> fileWriteService.writeCsvToFile(null, PATH_CONSUMER_FILE),
                "Method should throw NullPointerException with null input text");
    }

    private List<String> readFile(String path) {
        List<String> filesLines = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(
                new FileReader(path))) {
            String line = bufferedReader.readLine();
            while (line != null) {
                filesLines.add(line);
                line = bufferedReader.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Can't read file " + file, e);
        }
        return filesLines;
    }
}
