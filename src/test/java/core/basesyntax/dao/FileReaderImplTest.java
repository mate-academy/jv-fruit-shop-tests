package core.basesyntax.dao;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileReaderImplTest {
    private FileReaderImpl fileReader;

    @BeforeEach
    void setUp() {
        fileReader = new FileReaderImpl();
    }

    @Test
    void readFile_emptyFile_success() {
        try {
            Path emptyFile = Files.createTempFile("emptyTestFile", ".csv");
            Files.write(emptyFile, List.of());

            List<String> result = fileReader.readFile(emptyFile.toString());
            assertTrue(result.isEmpty());
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при создании временного файла", e);
        }
    }

    @Test
    void readFile_nonExistingFile_throwsException() {
        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                fileReader.readFile("non_existing_file.csv")
        );
        assertTrue(exception.getMessage().contains("Error while reading file")
                || exception.getMessage().contains("Resource not found"));
    }

    @Test
    void readFile_nullOrEmptyFileName_throwsException() {
        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                fileReader.readFile("")
        );
        assertTrue(exception.getMessage().contains("Error while reading file")
                || exception.getMessage().contains("Resource not found"));
    }
}
