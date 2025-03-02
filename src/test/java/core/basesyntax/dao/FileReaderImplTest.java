package core.basesyntax.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.net.URISyntaxException;
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
    void readFile_validFile_success() {
        Path testFile = null;
        try {
            testFile = Path.of(getClass().getClassLoader()
                    .getResource("testFinalReport.csv").toURI());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        List<String> result = fileReader.readFile(testFile.toString());

        result.forEach(System.out::println);

        assertEquals(2, result.size());
        assertEquals("apple,10", result.get(0));
        assertEquals("banana,20", result.get(1));
    }

    @Test
    void readFile_emptyFile_success() throws IOException {
        Path emptyFile = Path.of("testFinalReport.csv");
        Files.write(emptyFile, List.of("header"));

        List<String> result = fileReader.readFile(emptyFile.toString());
        assertTrue(result.isEmpty());
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
    void readFile_nullOrEmptyFileName_usesDefaultFile() {
        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                fileReader.readFile("")
        );
        assertTrue(exception.getMessage().contains("Error while reading file")
                || exception.getMessage().contains("Resource not found"));
    }
}
