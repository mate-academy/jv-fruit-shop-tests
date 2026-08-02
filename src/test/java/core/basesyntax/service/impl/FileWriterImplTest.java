package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class FileWriterImplTest {
    private final FileWriter fileWriter = new FileWriterImpl();

    @TempDir
    private Path tempDir;

    @Test
    public void write_validData_ok() throws IOException {
        Path outputFile = tempDir.resolve("report.csv");
        String data = "fruit,quantity" + System.lineSeparator() + "banana,20";

        fileWriter.write(data, outputFile.toString());

        assertEquals(data, Files.readString(outputFile, StandardCharsets.UTF_8));
    }

    @Test
    public void write_nestedPath_createsDirectories() throws IOException {
        Path outputFile = tempDir.resolve("reports").resolve("final.csv");
        String data = "fruit,quantity";

        fileWriter.write(data, outputFile.toString());

        assertEquals(data, Files.readString(outputFile, StandardCharsets.UTF_8));
    }

    @Test
    public void write_nullData_notOk() {
        Path outputFile = tempDir.resolve("report.csv");

        assertThrows(IllegalArgumentException.class,
                () -> fileWriter.write(null, outputFile.toString()));
    }

    @Test
    public void write_blankPath_notOk() {
        assertThrows(IllegalArgumentException.class, () -> fileWriter.write("data", " "));
    }
}
