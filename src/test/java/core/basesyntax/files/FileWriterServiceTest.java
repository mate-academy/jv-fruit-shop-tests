package core.basesyntax.files;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FileWriterServiceTest {
    private static final String expectedReport =
            "fruit,quantity" + System.lineSeparator()
                    + "banana,300" + System.lineSeparator()
                    + "apple,90";
    private static final String CORRECT_OUTPUT_DATA_FILEPATH = "src/test/resources/dateReport.csv";
    private static final String INCORRECT_OUTPUT_DATA_FILEPATH = "src/test/wrongWay/dateReport.csv";
    private static FileWriterService fileWriterService;

    @BeforeAll
    static void beforeAll() {
        fileWriterService = new FileWriterServiceImpl();
    }

    @Test
    void writeToFile_correctDataWriting_Ok() throws IOException {
        StringBuilder builder = new StringBuilder();
        boolean isFirstLine = true;
        String line;
        fileWriterService.write(expectedReport, CORRECT_OUTPUT_DATA_FILEPATH);
        try (BufferedReader reader = new BufferedReader(
                new FileReader(CORRECT_OUTPUT_DATA_FILEPATH))) {
            while ((line = reader.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                } else {
                    builder.append(System.lineSeparator());
                }
                builder.append(line);
            }
        }
        assertEquals(expectedReport, builder.toString());
    }

    @Test
    void writeToFile_NotCorrectFilePath_NotOk() {
        assertThrows(RuntimeException.class,
                () -> fileWriterService.write(expectedReport, INCORRECT_OUTPUT_DATA_FILEPATH));
    }
}
