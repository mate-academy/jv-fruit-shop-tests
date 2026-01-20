package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FileReaderImplTest {

    private static FileReaderImpl fileReader;

    @BeforeAll
    static void init() {
        fileReader = new FileReaderImpl();
    }

    @Test
    void read_ShouldReturnLines_WhenFileExists() throws IOException {
        Path tempFile = Files.createTempFile("test", ".csv");
        Files.write(tempFile, List.of("a,b,c"));

        List<String> actual = fileReader.read(tempFile.toString());

        assertEquals(List.of("a,b,c"), actual);
    }

    @Test
    void read_ShouldReturnEmptyList_WhenFileIsEmpty() throws IOException {
        Path tempFile = Files.createTempFile("empty", ".csv");

        List<String> actual = fileReader.read(tempFile.toString());

        assertEquals(List.of(), actual);
    }

    @Test
    void read_ShouldThrowException_WhenFileDoesNotExist() {
        assertThrows(RuntimeException.class,
                () -> fileReader.read("wrong/path/file.csv"));
    }
}
