package core.basesyntax.serviceimpltest;

import core.basesyntax.service.FileWriter;
import core.basesyntax.serviceimpl.FileWriterImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class FileWriterImplTest {

    private Path tempFile;

    @AfterEach
    void cleanup() throws IOException {
        if (tempFile != null && Files.exists(tempFile)) {
            Files.delete(tempFile);
        }
    }

    @Test
    void write_validData_shouldWriteToFile() throws IOException {
        tempFile = Files.createTempFile("testWrite", ".txt");
        String data = "apple,10\nbanana,5";
        FileWriter writer = new FileWriterImpl();
        writer.write(data, tempFile.toString());
        String fileContent = Files.readString(tempFile);
        Assertions.assertEquals(data, fileContent);
    }

    @Test
    void write_invalidPath_shouldThrowException() {
        FileWriter writer = new FileWriterImpl();
        String data = "apple,10";
        String invalidPath = "/invalid_path/test.txt";
        Assertions.assertThrows(RuntimeException.class,
                () -> writer.write(data, invalidPath));
    }
}
