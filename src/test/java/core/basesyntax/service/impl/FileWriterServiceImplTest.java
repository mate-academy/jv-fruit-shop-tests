package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import core.basesyntax.service.FileReaderService;
import core.basesyntax.service.FileWriterService;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileWriterServiceImplTest {
    private static final String INCORRECT_PATH_TO_FILE =
            "src/main/resources/res/final-report.csv";
    private static final String CORRECT_FILE_PATH = "src/main/resources/after-test-report.csv";
    private static final String EMPTY_PATH = "";
    private static final String CORRECT_FINAL_REPORT = "fruit,quantity"
            + System.lineSeparator() + "apple,11"
            + System.lineSeparator() + "banana,20";
    private static final List<String> EXPECTED_RESULT_AFTER_WRITING = List.of(
            "fruit,quantity", "apple,11", "banana,20"
    );
    private static FileReaderService fileReader;
    private static FileWriterService fileWriter;

    @BeforeEach
    void setUp() {
        fileWriter = new FileWriterServiceImpl();
        fileReader = new FileReaderImpl();
    }

    @Test
    void write_IncorrectPathToFile_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            fileWriter.write(CORRECT_FINAL_REPORT, INCORRECT_PATH_TO_FILE);
        });
    }

    @Test
    void write_EmptyFile_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            fileWriter.write(CORRECT_FINAL_REPORT, EMPTY_PATH);
        });
    }

    @Test
    void write_AllCorrectParameters_Ok() {
        fileWriter.write(CORRECT_FINAL_REPORT, CORRECT_FILE_PATH);
        List<String> actual = fileReader.getDataFromFile(CORRECT_FILE_PATH);
        List<String> expected = EXPECTED_RESULT_AFTER_WRITING;
        assertEquals(expected, actual);
    }
}
