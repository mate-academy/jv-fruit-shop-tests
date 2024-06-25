package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import core.basesyntax.errors.ErrorMessages;
import core.basesyntax.service.FileWriterService;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Testing FileWriterServiceImpl")
class FileWriterServiceImplTest {
    private static final String ERROR_READING_FILE = "Error reading file";
    private static final String FILE_DOESNT_EXIST = "File doesn't create";
    private static final String TEST_FILE_NAME = "testFilename.csv";
    private static final String FAILED_TO_DELETE_TEST_FILE = "Failed to delete test file: ";
    private static final String FILE_PATH = "./";
    private FileWriterService fileWriterService;

    @BeforeEach
    void setUp() {
        fileWriterService = new FileWriterServiceImpl(FILE_PATH + TEST_FILE_NAME);
    }

    @Test
    void write_validData_ok() {
        String dataToWrite = getDataForWriteToFile();
        fileWriterService.write(dataToWrite);
        File file = new File(TEST_FILE_NAME);
        assertTrue(file.exists(), FILE_DOESNT_EXIST);
        verifyWrittenContent(file, dataToWrite);
    }

    @Test
    void write_nullData_notOk() {
        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                fileWriterService.write(null));
        assertEquals(ErrorMessages.DATA_FOR_WRITING_CANNOT_BE_NULL,
                exception.getMessage());
    }

    @Test
    void write_invalidPath_notOk() {
        fileWriterService = new FileWriterServiceImpl(FILE_PATH);
        String dataToWrite = getDataForWriteToFile();
        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                fileWriterService.write(dataToWrite));
        assertEquals(ErrorMessages.CAN_T_WRITE_DATA_TO_THE_FILE + FILE_PATH,
                exception.getMessage());
    }

    @AfterEach
    void tearDown() {
        deleteTestFile();
    }

    private String getDataForWriteToFile() {
        String headFile = "type,fruit,quantity";
        String testData = getTestData().stream()
                .map(line -> line + System.lineSeparator())
                .collect(Collectors.joining());
        return headFile + System.lineSeparator() + testData;
    }

    private List<String> getTestData() {
        return List.of(
                "b,banana,20",
                "b,apple,100",
                "s,banana,100",
                "p,banana,13",
                "r,apple,10",
                "p,apple,20",
                "p,banana,5",
                "s,banana,50"
        );
    }

    private void verifyWrittenContent(File file, String dataToWrite) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String lines = bufferedReader
                    .lines()
                    .map(line -> line + System.lineSeparator())
                    .collect(Collectors.joining());
            assertEquals(dataToWrite, lines);
        } catch (IOException e) {
            fail(ERROR_READING_FILE);
        }
    }

    private void deleteTestFile() {
        File file = new File(TEST_FILE_NAME);
        if (file.exists() && !file.delete()) {
            fail(FAILED_TO_DELETE_TEST_FILE + TEST_FILE_NAME);
        }
    }
}
