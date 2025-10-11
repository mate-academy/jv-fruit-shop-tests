package service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Test;
import service.ReaderService;

public class ReaderServiceImplTest {

    private final ReaderService readerService = new ReaderServiceImpl();

    @Test
    void readFromFile_validFile_returnsAllLines() throws IOException {
        Path tempFile = Files.createTempFile("test-file", ".csv");
        try (FileWriter writer = new FileWriter(tempFile.toFile())) {
            writer.write("type,fruit,quantity\n");
            writer.write("b,banana,20\n");
            writer.write("s,apple,15\n");
        }

        List<String> result = readerService.readFromFile(tempFile.toString());

        assertEquals(3, result.size());
        assertEquals("type,fruit,quantity", result.get(0));
        assertEquals("b,banana,20", result.get(1));
        assertEquals("s,apple,15", result.get(2));
    }

    @Test
    void readFromFile_fileDoesNotExist_throwsRuntimeException() {
        String nonExistentPath = "not_existing_file.csv";
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> readerService.readFromFile(nonExistentPath)
        );
        assertTrue(exception.getMessage().contains("Can't read file by path"));
    }
}
