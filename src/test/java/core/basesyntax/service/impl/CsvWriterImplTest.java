package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.service.CsvWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CsvWriterImplTest {
    private static CsvWriter csvWriter;

    @BeforeEach
    void setUp() {
        csvWriter = new CsvWriterImpl();
    }

    @Test
    void write_validFile_Ok() {
        csvWriter.write(
                "fruit;quantity\nbanana;30\napple;0",
                "src/test/resources/test_output");
        Path newFile = Paths.get("src/test/resources/test_output.csv");
        Path checkFile = Paths.get("src/test/resources/checkToWrite.csv");
        try {
            List<String> newContent = Files.readAllLines(newFile);
            List<String> checkContent = Files.readAllLines(checkFile);
            assertEquals(newContent, checkContent);
        } catch (IOException e) {
            throw new RuntimeException("Unable to read files", e);
        }
    }
}
