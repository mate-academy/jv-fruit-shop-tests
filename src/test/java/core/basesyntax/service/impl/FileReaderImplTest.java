package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class FileReaderImplTest {
    private final FileReader fileReader = new FileReaderImpl();

    @TempDir
    private Path tempDir;

    @Test
    public void read_existingFile_ok() throws IOException {
        Path inputFile = tempDir.resolve("input.csv");
        List<String> inputLines = List.of("type,fruit,quantity", "b,banana,20");
        Files.write(inputFile, inputLines, StandardCharsets.UTF_8);

        List<String> actual = fileReader.read(inputFile.toString());

        assertEquals(inputLines, actual);
    }

    @Test
    public void read_blankPath_notOk() {
        assertThrows(IllegalArgumentException.class, () -> fileReader.read(" "));
    }

    @Test
    public void read_missingFile_notOk() {
        Path missingFile = tempDir.resolve("missing.csv");

        assertThrows(RuntimeException.class, () -> fileReader.read(missingFile.toString()));
    }
}
