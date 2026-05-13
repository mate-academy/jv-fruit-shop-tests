package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class FileReaderImplTest {
    @TempDir
    private Path tempDir;
    private FileReaderImpl fileReader;

    @BeforeEach
    void setUp() {
        fileReader = new FileReaderImpl();
    }

    @Test
    void read_existingFile_returnsLines() throws IOException {
        Path inputFile = tempDir.resolve("report.csv");
        List<String> expected = List.of("type,fruit,quantity", "b,banana,20");
        Files.write(inputFile, expected);

        List<String> actual = fileReader.read(inputFile.toString());

        assertEquals(expected, actual);
    }

    @Test
    void read_missingFile_throwsException() {
        Path missingFile = tempDir.resolve("missing.csv");

        assertThrows(RuntimeException.class, () -> fileReader.read(missingFile.toString()));
    }

    @Test
    void read_emptyFile_returnsEmptyList() throws IOException {
        Path emptyFile = tempDir.resolve("empty.csv");
        Files.createFile(emptyFile);

        List<String> actual = fileReader.read(emptyFile.toString());

        assertEquals(List.of(), actual);
    }
}
