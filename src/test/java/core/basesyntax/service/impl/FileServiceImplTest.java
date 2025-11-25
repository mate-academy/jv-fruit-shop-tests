package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.CsvFileReader;
import core.basesyntax.CsvFileWriter;
import core.basesyntax.service.FileService;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class FileServiceImplTest {
    private static final String FILE_NAME = "tempFile.txt";
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
    private final FileService fileServiceImpl = new FileServiceImpl(
            new CsvFileReader(),
            new CsvFileWriter()
    );

    @TempDir
    private Path tempDir;

    @Test
    public void read_validInput_ok() throws IOException {
        Path fakePath = this.tempDir.resolve(FILE_NAME);
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
        List<String> actual = this.fileServiceImpl.read(pathAsString);
        assertEquals(expected, actual);
    }

    @Test
    public void read_pathIsNull_notOk() {
        assertThrows(
                RuntimeException.class,
                () -> this.fileServiceImpl.read(null)
        );
    }

    @Test
    public void read_pathIsEmpty_notOk() {
        assertThrows(
                RuntimeException.class,
                () -> this.fileServiceImpl.read("")
        );
    }

    @Test
    public void read_failedToRead_notOk() {
        assertThrows(
                RuntimeException.class,
                () -> this.fileServiceImpl.read(FILE_NAME)
        );
    }

    @Test
    public void write_validInput_ok() throws IOException {
        Path fakePath = tempDir.resolve(FILE_NAME);
        String pathAsString = fakePath.toString();
        String expected = FILE_DATA;

        this.fileServiceImpl.write(pathAsString, expected);

        String actual = Files.readString(fakePath);

        assertEquals(expected, actual);
    }

    @Test
    public void write_pathIsNull_notOk() {
        assertThrows(
                RuntimeException.class,
                () -> this.fileServiceImpl.write(null, "")
        );
    }

    @Test
    public void write_pathIsEmpty_notOk() {
        assertThrows(
                RuntimeException.class,
                () -> this.fileServiceImpl.write("", null)
        );
    }

    @Test
    public void write_failedToWrite_notOk() throws IOException {
        Path fakePath = tempDir.resolve("folder.txt");
        String pathAsString = fakePath.toString();
        Files.createDirectories(fakePath);

        assertThrows(
                RuntimeException.class,
                () -> this.fileServiceImpl.write(pathAsString, FILE_DATA)
        );
    }
}
