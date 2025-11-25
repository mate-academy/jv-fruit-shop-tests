package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class CsvFileWriterTest {
    private static final String FILE_NAME = "tempFile.csv";
    private static final String FILE_DATA = """
            type,fruit,quantity
            b,banana,20
            b,apple,100
            s,banana,100
            p,banana,13
            r,apple,10
            p,apple,20
            p,banana,5
            s,banana,50
            """;
    private final FileDataWriter csvFileWriter = new CsvFileWriter();

    @TempDir
    private Path tempDir;

    @Test
    public void write_validPath_ok() throws IOException {
        Path fakePath = tempDir.resolve(FILE_NAME);
        String pathAsString = fakePath.toString();
        String expected = FILE_DATA;

        this.csvFileWriter.write(pathAsString, expected);

        String actual = Files.readString(fakePath);

        assertEquals(expected, actual);
    }

    @Test
    public void write_nullPath_notOk() {
        assertThrows(
                RuntimeException.class,
                () -> this.csvFileWriter.write(null, FILE_DATA)
        );
    }

    @Test void write_emptyPath_notOk() {
        assertThrows(
                RuntimeException.class,
                () -> this.csvFileWriter.write("", FILE_DATA)
        );
    }

    @Test
    public void write_failedToCreateDir_notOk() throws IOException {
        Path fakePath = tempDir.resolve(FILE_NAME);
        Files.writeString(fakePath, FILE_DATA);
        Path fakeFilePath = fakePath.resolve(FILE_NAME + "/" + FILE_NAME);
        String pathAsString = fakeFilePath.toString();

        assertThrows(
                RuntimeException.class,
                () -> this.csvFileWriter.write(pathAsString, FILE_DATA)
        );
    }

    @Test
    public void write_failedToWrite_notOk() throws IOException {
        Path fakePath = tempDir.resolve("folder.txt");
        String pathAsString = fakePath.toString();
        Files.createDirectories(fakePath);

        assertThrows(
                RuntimeException.class,
                () -> this.csvFileWriter.write(pathAsString, FILE_DATA)
        );
    }
}
