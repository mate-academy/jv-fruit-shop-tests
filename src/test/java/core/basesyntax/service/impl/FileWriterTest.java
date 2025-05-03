package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FileWriterTest {
    private static final String REPORT_PATH = "src/test/java/resource/report.csv";
    private static final String INCORRECT_REPORT_PATH = "ssrc/test/java/resource/report.csv";
    private static List<String> inputData;
    private static FileWriterImpl fileWriter;
    private static String expected;

    @BeforeAll
    static void beforeAll() {
        inputData = Arrays.asList("fruits,quantity", "apple,50",
                "banana,230", "watermelon,3");
        fileWriter = new FileWriterImpl();
        expected = "fruits,quantity" + System.lineSeparator()
                + "apple,50" + System.lineSeparator()
                + "banana,230" + System.lineSeparator()
                + "watermelon,3" + System.lineSeparator();
    }

    @Test
    void write_filePath_Ok() {
        fileWriter.write(inputData, REPORT_PATH);
        String actualPath;

        try {
            actualPath = Files.readString(Path.of(REPORT_PATH));
        } catch (IOException e) {
            throw new RuntimeException("Can't find file for this path: " + REPORT_PATH, e);
        }
        assertEquals(expected, actualPath);
    }

    @Test
    void write_incorrectFilePath_NotOk() {
        assertThrows(RuntimeException.class, () -> fileWriter.write(inputData,
                INCORRECT_REPORT_PATH));
    }
}
