package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import org.junit.Test;
import service.impl.FileReaderImpl;

public class FileReaderImplTest {
    @Test
    public void readValidFile_ok() throws IOException {
        String fileName = "testFile.txt";
        Files.write(Paths.get(fileName), List.of("Line 1", "Line 2"));

        FileReaderImpl fileReader = new FileReaderImpl();
        List<String> lines = fileReader.read(fileName);

        assertEquals(2, lines.size());
        assertEquals("Line 1", lines.get(0));
        assertEquals("Line 2", lines.get(1));

        Files.delete(Paths.get(fileName));
    }

    @Test
    public void readInvalidFile_notOk() {
        FileReaderImpl fileReader = new FileReaderImpl();
        assertThrows(IllegalArgumentException.class, () -> {
            fileReader.read("nonExistentFile.txt");
        });
    }

    @Test
    public void readEmptyFile() throws Exception {
        String fileName = "emptyFile.txt";
        Files.write(Paths.get(fileName), List.of());

        FileReaderImpl fileReader = new FileReaderImpl();
        List<String> lines = fileReader.read(fileName);

        assertTrue(lines.isEmpty(), "Expected the file to be empty");

        Files.delete(Paths.get(fileName));
    }
}
