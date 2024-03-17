package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.Writer;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class WriterImplTest {
    private static final String OUTPUT_FILE = "outputData.csv";
    private static final String TITLE = "type,fruit,quantity";

    private Writer writer;
    @TempDir
    private Path temporaryDirectory;

    @BeforeEach
    void setUp() {
        writer = new WriterImpl();
    }

    @Test
    void successfulWriting_write_ok() throws IOException {
        String expectedReport = TITLE + System.lineSeparator() + "banana,20";
        Path outputPath = temporaryDirectory.resolve(OUTPUT_FILE);
        writer.write(expectedReport, outputPath.toString());
        String actualReport = Files.readString(outputPath);
        assertEquals(expectedReport, actualReport,
                "The written content should match the expected report.");
    }

    @Test
    void invalidPath_write_ThrowsRuntimeException() {
        String invalidPath = temporaryDirectory.resolve(
                "nonexistent/directory/outputData.csv").toString();
        Exception exception = assertThrows(RuntimeException.class,
                () -> writer.write("content", invalidPath));
        assertEquals("Can not write to: "
                        + invalidPath, exception.getMessage(),
                "Exception message should match the expected one.");
    }
}
