package core.basesyntax.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileReaderImplTest {
    private static final String NON_EXISTING_FILE_PATH = "non_existing_file.csv";

    private FileReader fileReader;
    private Path emptyFile;

    @BeforeEach
    void setUp() throws IOException {
        fileReader = new FileReaderImpl();
        emptyFile = Files.createTempFile("emptyTestFile", ".csv");
        Files.write(emptyFile, List.of());
    }

    @Test
    void readFile_emptyFile_success() throws IOException {
        List<String> result = fileReader.readFile(emptyFile.toString());
        assertTrue(result.isEmpty(), "Expected empty list for empty file");
    }

    @Test
    void readFile_nonExistingFile_throwsRuntimeException() {
        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                fileReader.readFile(NON_EXISTING_FILE_PATH)
        );
        assertEquals("Error reading file " + NON_EXISTING_FILE_PATH, exception.getMessage());

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
