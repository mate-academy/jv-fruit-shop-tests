package core.basesyntax;

import core.basesyntax.service.FileWriter;
import core.basesyntax.serviceimpl.FileWriterImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FileWriterTest {
    private FileWriter fileWriter;
    private Path path;
    private String dataToWrite;

    @BeforeEach
    void setUp() throws IOException {
        fileWriter = new FileWriterImpl();
        dataToWrite = "type,fruit,quantity b,banana,20 s,banana,100, p,banana,13";
        String tempFile = Files.createTempFile("report", ".txt").toString();
        path = Paths.get(tempFile);
    }

    @AfterEach
    void cleanUp() throws IOException {
        Files.deleteIfExists(path);
    }

    @Test
    void successWrite_ok() throws IOException {
        fileWriter.write(dataToWrite, path.toString());
        String dataFromFile = Files.readString(path);
        Assertions.assertTrue(dataFromFile.contains("b,banana,20"));
        Assertions.assertFalse(dataFromFile.isBlank());
    }

    @Test
    void writeWithNullData_notOk() {
        IllegalArgumentException ex = Assertions.assertThrows(IllegalArgumentException.class,
                () -> fileWriter.write(null, path.toString()));
        Assertions.assertTrue(ex.getMessage().contains("Data can't be null"));
    }

    @Test
    void writeWithNullFileName_notOk() {
        IllegalArgumentException ex = Assertions.assertThrows(IllegalArgumentException.class,
                () -> fileWriter.write(dataToWrite, null));
        Assertions.assertTrue(ex.getMessage().contains("FileName can't be null"));
    }

    @Test
    void writeWithEmptyFileName_notOk() {
        IllegalArgumentException ex = Assertions.assertThrows(IllegalArgumentException.class,
                () -> fileWriter.write(dataToWrite, ""));
        Assertions.assertTrue(ex.getMessage().contains("FileName can't be empty"));
    }
}
