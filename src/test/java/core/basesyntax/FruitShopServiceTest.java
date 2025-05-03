package core.basesyntax;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.FileReaderService;
import service.FileWriterService;
import service.FruitShopService;
import service.StorageService;
import strategy.OperationStrategy;

public class FruitShopServiceTest {
    private static String INPUT_FILE_NAME = "test_input_file";
    private static String OUTPUT_FILE_NAME = "test_output_file";
    private static String FILE_TYPE = ".csv";
    private static final String FILE_CONTENT = "type,fruit,quantity"
            + System.lineSeparator()
            + "b,apple,100"
            + System.lineSeparator()
            + "p,apple,40";
    private FileReaderService fileReaderService;
    private OperationStrategy strategyFactory;
    private FileWriterService fileWriterService;
    private StorageService storageService;
    private FruitShopService fruitShopService;
    private Path tempInputFile;
    private Path tempOutputFile;

    @BeforeEach
    void setUp() throws IOException {
        fileReaderService = new FileReaderService();
        strategyFactory = new OperationStrategy();
        fileWriterService = new FileWriterService();
        storageService = new StorageService();
        fruitShopService = new FruitShopService(
                fileReaderService,
                strategyFactory,
                fileWriterService,
                storageService);
        tempInputFile = Files.createTempFile(INPUT_FILE_NAME, FILE_TYPE);
        tempOutputFile = Files.createTempFile(OUTPUT_FILE_NAME, FILE_TYPE);
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(tempInputFile);
        Files.deleteIfExists(tempOutputFile);
    }

    @Test
    void testProcessTransactionsAndGenerateReport_WithValidInput() throws IOException {
        Files.writeString(tempInputFile, FILE_CONTENT);
        fruitShopService.processTransactionsAndGenerateReport(
                tempInputFile.toString(),
                tempOutputFile.toString());
        List<String> actualLines = Files.readAllLines(tempOutputFile);
        List<String> expectedLines = new ArrayList<>();
        expectedLines.add("fruit,quantity");
        expectedLines.add("apple,60");
        Assertions.assertEquals(expectedLines, actualLines);
    }

    @Test
    void testProcessTransactionsAndGenerateReport_WithEmptyInput() throws IOException {
        fruitShopService.processTransactionsAndGenerateReport(
                tempInputFile.toString(),
                tempOutputFile.toString());
        List<String> actualLines = Files.readAllLines(tempOutputFile);
        List<String> expectedLines = Collections.singletonList("fruit,quantity");
        Assertions.assertEquals(expectedLines, actualLines);
    }
}
