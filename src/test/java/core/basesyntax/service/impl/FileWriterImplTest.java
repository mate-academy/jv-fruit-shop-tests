package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class FileWriterImplTest {
    private static final String FILE_PATH =
            "src/test/resources/successWriting.txt";
    private static final String FILE_PATH_FOR_SECOND_TEST =
            "src/test/resources/successWriting2.txt";
    private static final String INVALID_PATH =
            "/invalid/path/to/file.txt";
    private final FileWriter fileWriter = new FileWriterImpl();

    @Test
    void writeReportWithoutPath_NotOk() {
        String report = "Test report content";

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> fileWriter.write(report, INVALID_PATH));

        assertEquals("Can't write a report " + INVALID_PATH, exception.getMessage());
    }

    @Test
    void writeReportWithSuccess_Ok() throws IOException {
        String report = "Writing to txt file is successful";
        Assertions.assertDoesNotThrow(() -> fileWriter.write(report,
                FILE_PATH));
        String content = Files.readString(Path.of(FILE_PATH));

        assertEquals(report, content, "The text must be the same");
    }

    @Test
    void writeReportWithDifferentData_OK() throws IOException {
        String report = "Different report content"
                + System.lineSeparator()
                + "With multiple lines";
        Assertions.assertDoesNotThrow(() -> fileWriter.write(report, FILE_PATH_FOR_SECOND_TEST));
        String content = Files.readString(Path.of(FILE_PATH_FOR_SECOND_TEST));
        Assertions.assertEquals(report, content, "The text must be the same");
    }
}
