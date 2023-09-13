package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.impl.FileWriterServiceImpl;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FileWriterServiceTest {
    private static final String CORRECT_PATH = "src/test/resources/report.csv";
    private static final String INCORRECT_PATH = "src/test/wrongresources/report.csv";
    private static final String EXPECTED_REPORT = "fruit,quantity" + System.lineSeparator()
            + "banana,110" + System.lineSeparator()
            + "apple,160" + System.lineSeparator();
    private static FileWriterService fileWriterService;

    @BeforeAll
    static void beforeAll() {
        fileWriterService = new FileWriterServiceImpl();
    }

    @Test
    void write_validFilePath_Ok() throws IOException {
        StringBuilder builder = new StringBuilder();
        fileWriterService.write(EXPECTED_REPORT, CORRECT_PATH);
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(CORRECT_PATH))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                builder.append(line).append(System.lineSeparator());
            }
        }
        assertEquals(EXPECTED_REPORT, builder.toString());
    }

    @Test
    void write_invalidFilePath_NotOk() {
        assertThrows(RuntimeException.class,
                () -> fileWriterService.write(EXPECTED_REPORT, INCORRECT_PATH));
    }
}
