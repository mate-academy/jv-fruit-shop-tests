package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CustomFileWriterImplTest {
    private CustomFileWriterImpl fileWriter;

    @BeforeEach
    void setUp() {
        fileWriter = new CustomFileWriterImpl();
    }

    @Test
    void write_writeDataToFile_ok() throws IOException {
        Path tempFile = Files.createTempFile("testFile", ".csv");
        String data = "type,fruit,quantity\nb,banana,20\ns,banana,100";

        assertDoesNotThrow(() -> fileWriter.write(data, tempFile.toString()));

        String fileContent = Files.readString(tempFile);
        assertEquals(data, fileContent.trim());

        Files.delete(tempFile);
    }

    @Test
    void write_nullData_notOk() {
        assertThrows(IllegalArgumentException.class,
                () -> fileWriter.write(null, "valid/path.csv"), "Data can't be null or empty.");
    }

    @Test
    void write_emptyData_notOk() {
        assertThrows(IllegalArgumentException.class,
                () -> fileWriter.write("", "valid/path.csv"), "Data can't be null or empty.");
    }

    @Test
    void write_nullPath_notOk() {
        assertThrows(IllegalArgumentException.class,
                () -> fileWriter.write("some data", null), "Path can't be null or empty.");
    }

    @Test
    void write_emptyPath_notOk() {
        assertThrows(IllegalArgumentException.class,
                () -> fileWriter.write("some data", ""), "Path can't be null or empty.");
    }

    @Test
    void write_invalidPath_notOk() {
        Path invalidPath = Paths.get("/invalid/path/testFile.csv");
        String data = "type,fruit,quantity\nb,banana,20\ns,banana,100";

        assertThrows(IOException.class, () -> fileWriter.write(data, invalidPath.toString()));
    }
}

