package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class CsvFileReaderTest {
    private static final String FILE_NAME = "tempFile.csv";
    private final FileDataReader csvFileReader = new CsvFileReader();

    @TempDir
    private Path tempPath;

    @Test
    public void read_validInput() throws IOException {
        Path fakePath = this.tempPath.resolve(FILE_NAME);
        String pathAsString = fakePath.toString();
        List<String> expected = List.of(
                "type,fruit,quantity",
                "b,banana,20",
                "b,apple,100",
                "s,banana,100",
                "p,banana,13",
                "r,apple,10",
                "p,apple,20",
                "p,banana,5",
                "s,banana,50"
        );

        Files.write(fakePath, expected, StandardCharsets.UTF_8);
        List<String> actual = this.csvFileReader.read(pathAsString);
        assertEquals(expected, actual);
    }

    @Test
    public void read_nullPath_notOk() {
        assertThrows(
                RuntimeException.class,
                () -> this.csvFileReader.read(null)
        );
    }

    @Test
    public void read_emptyPath_notOk() {
        assertThrows(
                RuntimeException.class,
                () -> this.csvFileReader.read("")
        );
    }

    @Test
    public void read_failedToRead_notOk() {
        assertThrows(
                RuntimeException.class,
                () -> this.csvFileReader.read(FILE_NAME)
        );
    }
}
