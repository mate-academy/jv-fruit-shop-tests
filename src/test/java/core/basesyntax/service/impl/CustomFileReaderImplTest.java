package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CustomFileReaderImplTest {
    private CustomFileReaderImpl fileReader;

    @BeforeEach
    void setUp() {
        fileReader = new CustomFileReaderImpl();
    }

    @Test
    void read_returnEmptyListForSingleLineFile_ok() throws IOException {
        Path filePath = Files.createTempFile("single_line_file", ".csv");
        Files.write(filePath, Collections.singletonList("type,fruit,quantity"));

        List<String> result = fileReader.read(filePath.toString());

        assertTrue(result.isEmpty());
        Files.delete(filePath);
    }

    @Test
    void read_returnCorrectContentForValidFile_okk() throws IOException {
        Path filePath = Files.createTempFile("valid_file", ".csv");
        Files.write(filePath, List.of(
                "type,fruit,quantity",
                "b,banana,20",
                "s,banana,100",
                "p,banana,13"
        ));

        List<String> result = fileReader.read(filePath.toString());

        assertEquals(3, result.size());
        assertEquals("b,banana,20", result.get(0));
        assertEquals("s,banana,100", result.get(1));
        assertEquals("p,banana,13", result.get(2));

        Files.delete(filePath);
    }

    @Test
    void read_nullPath_notOk() {
        assertThrows(IllegalArgumentException.class,
                () -> fileReader.read(null), "Path can't be null or empty.");
    }

    @Test
    void read_emptyPath_notOk() {
        assertThrows(IllegalArgumentException.class,
                () -> fileReader.read(""), "Path can't be null or empty.");
    }

    @Test
    void read_invalidPath_notOk() {
        assertThrows(IOException.class,
                () -> fileReader.read("invalid/path.csv"));
    }
}
