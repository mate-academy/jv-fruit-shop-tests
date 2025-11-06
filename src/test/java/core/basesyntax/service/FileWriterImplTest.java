package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.impl.FileWriterImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;

class FileWriterImplTest {
    private final FileWriter fileWriter = new FileWriterImpl();

    @Test
    void write_validFile_ok() throws IOException {
        Path tempFile = Files.createTempFile("testOutput", ".csv");
        String content = "fruit,quantity\nbanana,10";
        fileWriter.write(content, tempFile.toString());
        String read = Files.readString(tempFile);
        assertEquals(content, read);
    }

    @Test
    void write_invalidPath_throws() {
        assertThrows(RuntimeException.class,
                () -> fileWriter.write("data", "/wrong_path/forbidden/file.txt"));
    }

    @Test
    void write_validData_writesFile() throws IOException {
        Path tempFile = Files.createTempFile("test", ".csv");
        try {
            FileWriter fileWriter = new FileWriterImpl();
            fileWriter.write("banana,10", tempFile.toString());

            String result = Files.readString(tempFile);
            assertEquals("banana,10", result);
        } finally {
            Files.deleteIfExists(tempFile);
        }
    }

    @Test
    void write_invalidFilePath_throwsException() {
        FileWriter writer = new FileWriterImpl();
        assertThrows(RuntimeException.class,
                () -> writer.write("banana,10", "Z:/path/does/not/exist/file.csv"));
    }
}
