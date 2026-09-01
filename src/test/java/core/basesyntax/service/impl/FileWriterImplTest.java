package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class FileWriterImplTest {
    private FileWriter fileWriter;

    @TempDir
    private Path tempDir;

    @BeforeEach
    void setUp() {
        fileWriter = new FileWriterImpl();
    }

    @Test
    void write_validReport_writesFile() throws IOException {
        Path outputFile = tempDir.resolve("report.csv");
        String report = "fruit,quantity"
                + System.lineSeparator()
                + "banana,100";
        fileWriter.write(report, outputFile.toString());
        String actual = Files.readString(outputFile);
        assertEquals(report, actual);
    }

    @Test
    void write_invalidPath_throwsException() {
        Path invalidFile =
                tempDir.resolve("missing/report.csv");

        String report = "fruit,quantity";

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> fileWriter.write(
                        report,
                        invalidFile.toString()
                )
        );

        assertEquals(
                "Can't write data to file: " + invalidFile,
                exception.getMessage()
        );
    }
}
