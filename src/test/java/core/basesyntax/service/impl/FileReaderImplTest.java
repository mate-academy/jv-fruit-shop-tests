package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.service.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class FileReaderImplTest {

    private static final String LINE_1 = "line1";
    private static final String LINE_2 = "line2";
    private static final String LINE_3 = "line3";

    @TempDir
    private Path tempDir;
    private FileReader fileReader;
    private Path testFile;
    private Path emptyFile;

    @BeforeEach
    void setUp() throws IOException {
        fileReader = new FileReaderImpl();
        testFile = tempDir.resolve("test_file.txt");
        emptyFile = tempDir.resolve("empty_file.txt");

        String content = String.join(System.lineSeparator(), LINE_1, LINE_2, LINE_3);
        Files.writeString(testFile, content);

        Files.createFile(emptyFile);
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(testFile);
        Files.deleteIfExists(emptyFile);
    }

    @Test
    void read_validFile_success() {
        List<String> result = fileReader.read(testFile.toString());

        assertEquals(3, result.size(), "Expected file to have 3 lines");
        assertEquals(LINE_1, result.get(0));
        assertEquals(LINE_2, result.get(1));
        assertEquals(LINE_3, result.get(2));
    }

    @Test
    void read_emptyFile_returnsEmptyList() {
        List<String> result = fileReader.read(emptyFile.toString());
        assertTrue(result.isEmpty());
    }

    @Test
    void read_nonExistingFile_throwsException() {
        Path nonExistingFile = tempDir.resolve("non_existing.txt");

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> fileReader.read(nonExistingFile.toString()));

        assertTrue(exception.getMessage().contains("Can't read file at path"));
    }
}
