package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.FileService;
import core.basesyntax.service.FruitParser;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class FileServiceImplTest {
    private static final String VALID_INPUT_FILE_PATH = "src/test/resources/input.csv";
    private static final String EMPTY_REPORT_FILE_PATH = "src/test/resources/empty_report.csv";
    private static final String INVALID_PATH_FILE = "invalid_path.csv";
    private static final int FIRST_INDEX_OF_LIST = 0;

    @TempDir
    private Path tempDir;

    private FileService fileService;
    private final Map<String, Integer> mapOfTotalTransactions = new LinkedHashMap<>();

    @BeforeEach
    void setUp() {
        FruitParser fruitParser = new FruitParser() {
            @Override
            public FruitTransaction parse(String line) {
                return FruitTransaction.of(Operation.B, "test_fruit", 50);
            }
        };
        fileService = new FileServiceImpl(fruitParser);
        mapOfTotalTransactions.put("banana", 132);
        mapOfTotalTransactions.put("apple", 90);
    }

    @Test
    void loadFromFile_validInputFile_ok() {
        List<FruitTransaction> transactions = fileService.loadFromFile(VALID_INPUT_FILE_PATH);
        Assertions.assertNotEquals(
                0, transactions.size(), "Size can't be 0");
        Assertions.assertEquals(8, transactions.size(), "List size should be 8");
        Assertions.assertEquals("test_fruit", transactions.get(FIRST_INDEX_OF_LIST).getFruitName());
    }

    @Test
    void loadFromFile_wrongPath_notOk() {
        Assertions.assertThrows(RuntimeException.class,
                () -> fileService.loadFromFile(INVALID_PATH_FILE));
    }

    @Test
    void loadFromFile_emptyFile_ok() {
        List<FruitTransaction> transactions = fileService.loadFromFile(EMPTY_REPORT_FILE_PATH);
        Assertions.assertTrue(transactions.isEmpty());
    }

    @Test
    void saveToFile_validInputFile_ok() throws IOException {
        Path tempSaveFile = tempDir.resolve("report.csv");
        fileService.saveToFile(mapOfTotalTransactions, tempSaveFile.toString());
        List<String> actualLines = Files.readAllLines(tempSaveFile);
        Assertions.assertEquals(3, actualLines.size());
        Assertions.assertEquals("fruit,quantity", actualLines.get(0));
        Assertions.assertEquals("banana,132", actualLines.get(1));
        Assertions.assertEquals("apple,90", actualLines.get(2));
    }

    @Test
    void saveToFile_emptyMap_ok() throws IOException {
        Path tempEmptyFile = tempDir.resolve("report.csv");
        fileService.saveToFile(Collections.emptyMap(), tempEmptyFile.toString());
        List<String> lines = Files.readAllLines(tempEmptyFile);
        Assertions.assertEquals(1, lines.size());
        Assertions.assertEquals("fruit,quantity", lines.get(0));
    }
}
