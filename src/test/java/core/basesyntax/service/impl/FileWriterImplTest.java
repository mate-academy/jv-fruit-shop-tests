package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class FileWriterImplTest {
    @TempDir
    private Path tempDir;
    private FileWriterImpl fileWriter;

    @BeforeEach
    void setUp() {
        fileWriter = new FileWriterImpl();
    }

    @Test
    void writeValidReportCreatesFile() throws IOException {
        Path outputFile = tempDir.resolve("finalReport.csv");
        String expected = "fruit,quantity\nbanana,20\n";

        fileWriter.write(expected, outputFile.toString());

        assertEquals(expected, Files.readString(outputFile));
    }

    @Test
    void writeToInvalidPathThrowsException() {
        Path invalidPath = tempDir.resolve("missing").resolve("report.csv");

        assertThrows(RuntimeException.class,
                () -> fileWriter.write("report", invalidPath.toString()));
    }
}
