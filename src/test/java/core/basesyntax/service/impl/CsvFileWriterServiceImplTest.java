package core.basesyntax.service.impl;

import core.basesyntax.service.CsvFileWriterService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CsvFileWriterServiceImplTest {
    private static final String REPORT_FIRST_LINE = "fruit,quantity";
    private static final String VALID_FILE_EXTENSION_TYPE = "src/test/resources/valid.csv";
    private static final String INVALID_FILE_EXTENSION_TYPE = "invalid.txt";
    private static CsvFileWriterService writerService;

    @BeforeAll
    static void beforeAll() {
        writerService = new CsvFileWriterServiceImpl();
    }

    @Test
    void writeToNewCsvFile_nullInput_notOk() {
        String nullFileName = null;
        List<String> validInputList = generateValidInputList();
        assertThrows(RuntimeException.class,
                ()-> writerService.writeToNewCsvFile(nullFileName, validInputList));

        List<String> nullInputList = null;
        assertThrows(RuntimeException.class,
                ()-> writerService.writeToNewCsvFile(VALID_FILE_EXTENSION_TYPE, nullInputList));
    }

    @Test
    void writeToNewCsvFile_wrongFileExtension_notOk() {
        List<String> validInputList = generateValidInputList();
        assertThrows(RuntimeException.class,
                ()-> writerService.writeToNewCsvFile(INVALID_FILE_EXTENSION_TYPE, validInputList));
    }

    @Test
    void writeToNewCsvFile_validData_ok() {
        List<String> validInputList = generateValidInputList();
        assertDoesNotThrow(
                () -> writerService.writeToNewCsvFile(VALID_FILE_EXTENSION_TYPE, validInputList));

        Path filePath = Paths.get(VALID_FILE_EXTENSION_TYPE);
        assertTrue(Files.exists(filePath));
    }

    private List<String> generateValidInputList() {
        List<String> validList = new ArrayList<>();
        validList.add(REPORT_FIRST_LINE);
        validList.add("banana,33");
        validList.add("apple,0");
        validList.add("pear,52");
        validList.add("peach,133");
        return validList;
    }
}