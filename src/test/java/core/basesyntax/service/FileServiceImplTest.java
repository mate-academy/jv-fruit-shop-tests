package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class FileServiceImplTest {

    private static final String HEADER_LINE = "type,fruit,quantity";
    private static final String FIRST_BANANA_EXPECTED_LINE = "b,banana,20";
    private static final String SECOND_BANANA_EXPECTED_LINE = "s,banana,100";
    private static final String THIRD_BANANA_EXPECTED_LINE = "p,banana,13";
    private static final String FOURTH_BANANA_EXPECTED_LINE = "p,banana,5";
    private static final String FIFTH_BANANA_EXPECTED_LINE = "s,banana,50";
    private static final String FIRST_APPLE_EXPECTED_LINE = "b,apple,100";
    private static final String SECOND_APPLE_EXPECTED_LINE = "r,apple,10";
    private static final String THIRD_APPLE_EXPECTED_LINE = "p,apple,20";
    private static final String EXPECTED_WRITE_CONTENT = "apple,10";
    private static final String ACTUAL_CSV_FILE_NAME = "fruits.csv";
    private static final String RESULT_TEST_CSV_FILE_NAME = "resultTest.csv";
    private static final String INVALID_CSV_FILE_NAME = "invalidFile.csv";
    private static final String JOIN_DELIMITER = "";

    @Test
    @DisplayName("Read from file test")
    void readFile_ok() {
        List<String> expectedReadLines = new ArrayList<>();
        expectedReadLines.add(HEADER_LINE);
        expectedReadLines.add(FIRST_BANANA_EXPECTED_LINE);
        expectedReadLines.add(FIRST_APPLE_EXPECTED_LINE);
        expectedReadLines.add(SECOND_BANANA_EXPECTED_LINE);
        expectedReadLines.add(THIRD_BANANA_EXPECTED_LINE);
        expectedReadLines.add(SECOND_APPLE_EXPECTED_LINE);
        expectedReadLines.add(THIRD_APPLE_EXPECTED_LINE);
        expectedReadLines.add(FOURTH_BANANA_EXPECTED_LINE);
        expectedReadLines.add(FIFTH_BANANA_EXPECTED_LINE);
        FileService fileService = new FileServiceImpl();
        List<String> actualReadLines = fileService.read(ACTUAL_CSV_FILE_NAME);
        assertEquals(actualReadLines, expectedReadLines);
    }

    @Test
    @DisplayName("Read null file test")
    void readNullFile_notOk() {
        FileService fileService = new FileServiceImpl();
        assertThrows(NullPointerException.class, () -> fileService.read(null));
    }

    @Test
    @DisplayName("Read non-existent file test")
    void readNonExistentFile_notOk() {
        FileService fileService = new FileServiceImpl();
        assertThrows(RuntimeException.class, () -> fileService.read(INVALID_CSV_FILE_NAME));
    }

    @Test
    @DisplayName("Write to file test")
    void writeToFile_ok() {
        FileService fileService = new FileServiceImpl();
        fileService.writeToFile(EXPECTED_WRITE_CONTENT, RESULT_TEST_CSV_FILE_NAME);
        List<String> readContent = fileService.read(RESULT_TEST_CSV_FILE_NAME);
        String actualContent = String.join(JOIN_DELIMITER, readContent);
        assertEquals(EXPECTED_WRITE_CONTENT, actualContent);
    }

    @Test
    @DisplayName("Write to null file test")
    void writeToNullFile_notOk() {
        FileService fileService = new FileServiceImpl();
        assertThrows(NullPointerException.class,
                     () -> fileService.writeToFile(EXPECTED_WRITE_CONTENT, null));
    }

    @Test
    @DisplayName("Write empty content to file")
    void writeEmptyContent_notOk() {
        FileService fileService = new FileServiceImpl();
        assertThrows(RuntimeException.class,
                     () -> fileService.writeToFile("", ACTUAL_CSV_FILE_NAME));
    }
}
