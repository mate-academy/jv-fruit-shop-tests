package core.basesyntax.writer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileWriterImplTest {
    private FileWriter writer;

    @BeforeEach
    void setUp() {
        writer = new FileWriterImpl();
    }

    @Test
    void write_validFile_Ok() throws IOException {
        String filePath = "src/test/resources/testWrite.txt";
        String content = "Hello World";
        writer.write(filePath, content);

        List<String> actual = Files.readAllLines(Path.of(filePath));
        List<String> expected = List.of("Hello World");
        assertEquals(expected, actual);
    }

    @Test
    void write_invalidFile_NotOk() {
        String filePath = "wrongFolder/testWrite.txt";
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> writer.write(filePath, "Hello World"));
        String expectedMessage = "Can't write data to file: " + filePath;
        assertEquals(expectedMessage, exception.getMessage());
    }
}
