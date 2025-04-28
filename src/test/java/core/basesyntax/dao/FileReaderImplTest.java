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
            throw new RuntimeException("Failed to create temporary file", e);
        }
    }

    @Test
    void readFile_nonExistingFile_throwsRuntimeException() {
        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                fileReader.readFile("non_existing_file.csv")
        );
        assertTrue(exception.getMessage().contains("Error reading file"));
    }

    @Test
    void readFile_nullFileName_throwsIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                fileReader.readFile(null)
        );
        assertTrue(exception.getMessage().contains("must not be null or blank"));
    }

    @Test
    void readFile_blankFileName_throwsIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                fileReader.readFile("")
        );
        assertTrue(exception.getMessage().contains("must not be null or blank"));
    }
}
