package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import core.basesyntax.errors.ErrorMessages;
import core.basesyntax.service.FileReaderService;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Testing FileReaderServiceImpl")
class FileReaderServiceImplTest {
    private static final String CAN_T_CREATE_TEST_FILE = "Can't create test file";
    private static final String TEST_FILE_NAME = "testFilename.csv";
    private static final String FAILED_TO_DELETE_TEST_FILE = "Failed to delete test file: ";
    private FileReaderService fileReaderService;

    @BeforeEach
    void setUp() {
        fileReaderService = new FileReaderServiceImpl();
    }

    @Test
    void read_existFile_ok() {
        createTestFile();
        List<String> expected = getTestData();
        List<String> actual = fileReaderService.read(TEST_FILE_NAME);
        assertEquals(expected, actual);
    }

    @Test
    void read_nonExistFile_notOk() {
        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                fileReaderService.read(TEST_FILE_NAME));
        String expectedMessage = ErrorMessages.CAN_T_READ_FROM_FILE + TEST_FILE_NAME;
        String actualErrorMessage = exception.getMessage();
        assertEquals(expectedMessage,actualErrorMessage,
                getInfoMessage(expectedMessage, actualErrorMessage)
        );
    }

    @AfterEach
    void tearDown() {
        deleteTestFile();
    }

    private void createTestFile() {
        String testData = getDataForWriteToFile();
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(TEST_FILE_NAME))) {
            bufferedWriter.write(testData);
        } catch (IOException e) {
            fail(CAN_T_CREATE_TEST_FILE + TEST_FILE_NAME);
        }
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

    private String getInfoMessage(String expectedMessage, String actualMessage) {
        return "Test failed, expected: " + expectedMessage + " but actual " + actualMessage;
    }

    private void deleteTestFile() {
        File file = new File(TEST_FILE_NAME);
        if (file.exists() && !file.delete()) {
            fail(FAILED_TO_DELETE_TEST_FILE + TEST_FILE_NAME);
        }
    }
}
