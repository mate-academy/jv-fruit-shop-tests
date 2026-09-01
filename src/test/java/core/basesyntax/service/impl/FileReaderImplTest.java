package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class FileReaderImplTest {
    private FileReader fileReader;

    @TempDir
    private Path tempDir;

    @BeforeEach
    void setUp() {
        fileReader = new FileReaderImpl();
    }

    @Test
    void read_validFile_returnsLines() throws IOException {
        List<String> expected = List.of(
                "type,fruit,quantity",
                "b,banana,20",
                "s,apple,100"
        );

        Path inputFile = tempDir.resolve("report.csv");

        Files.write(inputFile, expected);

        List<String> actual =
                fileReader.read(inputFile.toString());

        assertEquals(expected, actual);
    }

    @Test
    void read_missingFile_throwsException() {
        Path missingFile =
                tempDir.resolve("missing.csv");

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> fileReader.read(missingFile.toString())
        );

        assertEquals(
                "Can't read file: " + missingFile,
                exception.getMessage()
        );
    }
}
