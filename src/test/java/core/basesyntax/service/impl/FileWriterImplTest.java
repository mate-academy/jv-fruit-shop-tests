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
    private final FileWriter fileWriter = new FileWriterImpl();

    @Test
    void writeReportWithoutPath_NotOk() {
        String report = "Test report content";
        String invalidFileName = "/invalid/path/to/file.txt";

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> fileWriter.write(report, invalidFileName));

        assertEquals("Can't write a report " + invalidFileName, exception.getMessage());
    }

    @Test
    void writeReportWithSuccess_Ok() throws IOException {
        String report = "Writing to txt file is successful";
        Assertions.assertDoesNotThrow(() -> fileWriter.write(report,
                "src/test/resources/successWriting.txt"));
        String content = Files.readString(Path.of("src/test/resources/successWriting.txt"));

        assertEquals(report, content, "The text must be the same");
    }
}
