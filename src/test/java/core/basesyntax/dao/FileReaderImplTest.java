package core.basesyntax.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
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
    void read_validFile_ok() throws IOException {
        Path inputFile = tempDir.resolve("input.csv");

        List<String> expected = List.of(
                "type,fruit,quantity",
                "b,banana,20",
                "s,apple,10"
        );

        Files.write(inputFile, expected);

        List<String> actual = fileReader.read(inputFile.toString());

        assertEquals(expected, actual);
    }
}
