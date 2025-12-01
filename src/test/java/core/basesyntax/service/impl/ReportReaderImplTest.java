package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.service.ReportReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class ReportReaderImplTest {
    private ReportReader reader;
    @TempDir
    private Path tempDir;

    @BeforeEach
    void setUp() {
        reader = new ReportReaderImpl();
    }

    @Test
    void read_validFile_ok() throws IOException {
        Path testFile = tempDir.resolve("test.csv");
        List<String> content = Arrays.asList(
                "type,fruit,quantity",
                "b,banana,20",
                "s,apple,100"
        );
        Files.write(testFile, content);
        List<String> result = reader.read(testFile.toString());
        assertEquals(3, result.size());
        assertEquals("type,fruit,quantity", result.get(0));
        assertEquals("b,banana,20", result.get(1));
        assertEquals("s,apple,100", result.get(2));
    }

    @Test
    void read_emptyFile_ok() throws IOException {
        Path testFile = tempDir.resolve("empty.csv");
        Files.write(testFile, Arrays.asList());
        List<String> result = reader.read(testFile.toString());
        assertTrue(result.isEmpty());
    }

    @Test
    void read_nonExistentFile_throwsException() {
        String nonExistentPath = tempDir.resolve("nonexistent.csv").toString();
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> reader.read(nonExistentPath));
        assertTrue(exception.getMessage().contains("Can't find file by path"));
    }

    @Test
    void read_singleLine_ok() throws IOException {
        Path testFile = tempDir.resolve("single.csv");
        Files.write(testFile, Arrays.asList("type,fruit,quantity"));
        List<String> result = reader.read(testFile.toString());
        assertEquals(1, result.size());
        assertEquals("type,fruit,quantity", result.get(0));
    }

    @Test
    void read_multipleLines_ok() throws IOException {
        Path testFile = tempDir.resolve("multiple.csv");
        List<String> content = Arrays.asList(
                "type,fruit,quantity",
                "b,apple,100",
                "s,banana,50",
                "p,apple,20",
                "r,banana,10"
        );
        Files.write(testFile, content);
        List<String> result = reader.read(testFile.toString());
        assertEquals(5, result.size());
    }

    @Test
    void read_fileWithEmptyLines_ok() throws IOException {
        Path testFile = tempDir.resolve("withEmpty.csv");
        List<String> content = Arrays.asList(
                "type,fruit,quantity",
                "",
                "b,apple,100",
                ""
        );
        Files.write(testFile, content);
        List<String> result = reader.read(testFile.toString());
        assertEquals(4, result.size());
    }

    @Test
    void read_fileWithSpecialCharacters_ok() throws IOException {
        Path testFile = tempDir.resolve("special.csv");
        List<String> content = Arrays.asList(
                "type,fruit,quantity",
                "b,apple-red,100"
        );
        Files.write(testFile, content);
        List<String> result = reader.read(testFile.toString());
        assertEquals(2, result.size());
        assertEquals("b,apple-red,100", result.get(1));
    }
}
