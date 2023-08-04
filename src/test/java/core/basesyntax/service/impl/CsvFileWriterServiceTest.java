package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.service.FileWriterService;
import java.io.File;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CsvFileWriterServiceTest {
    private static final String DEFAULT_PATH = "src/test/resources/";
    private static final String DEFAULT_REPORT_NAME = "report.csv";
    private static final String NOT_EXISTING_PATH = "source/test/resources/";
    private static final String LINE_SEPARATOR = System.lineSeparator();
    private static final String STANDARD_HEADER = "fruit,quantity" + LINE_SEPARATOR;
    private static final String BANANA_FRUIT_NAME = "banana";
    private static final Integer BANANA_FRUIT_QUANTITY = 150;
    private static final String APPLE_FRUIT_NAME = "apple";
    private static final Integer APPLE_FRUIT_QUANTITY = 250;
    private static final String TANGERINE_FRUIT_NAME = "tangerine";
    private static final Integer TANGERINE_FRUIT_QUANTITY = 10;
    private static final String ERROR_MESSAGE = "Can't create a new report!";
    private String generatedReport;
    private FileWriterService fileWriter;

    @BeforeEach
    void setUp() {
        fileWriter = new CsvFileWriterService();
        Storage.storage.put(BANANA_FRUIT_NAME, BANANA_FRUIT_QUANTITY);
        Storage.storage.put(APPLE_FRUIT_NAME, APPLE_FRUIT_QUANTITY);
        Storage.storage.put(TANGERINE_FRUIT_NAME, TANGERINE_FRUIT_QUANTITY);
        StringBuilder report = new StringBuilder(STANDARD_HEADER);
        for (Map.Entry<String, Integer> entry : Storage.storage.entrySet()) {
            report.append(entry.getKey());
            report.append(",");
            report.append(entry.getValue());
            report.append(System.lineSeparator());
        }
        generatedReport = report.toString();
    }

    @Test
    void writeToFile_existingPath_ok() {
        fileWriter.writeToFile(DEFAULT_PATH, generatedReport);
        File rootDirectory = new File(DEFAULT_PATH + DEFAULT_REPORT_NAME);
        assertTrue(rootDirectory.exists());
    }

    @Test
    void writeToFile_notExistingPath_notOk() {
        RuntimeException actualException = assertThrows(RuntimeException.class,
                () -> fileWriter.writeToFile(NOT_EXISTING_PATH,
                                            generatedReport));
        assertEquals(ERROR_MESSAGE, actualException.getMessage());
    }

    @AfterEach
    void tearDown() {
        File file = new File(DEFAULT_PATH + DEFAULT_REPORT_NAME);
        file.delete();
        Storage.storage.clear();
    }
}
