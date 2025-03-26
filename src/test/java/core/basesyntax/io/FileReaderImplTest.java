package core.basesyntax.io;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileReaderImplTest {
    private FileReader fileReader;
    private Path tempFile;

    @BeforeEach
    void setUp() {
        fileReader = new FileReaderImpl();
        tempFile = Path.of("src/test/java/core/basesyntax/resources/test.csv");

        try {
            Files.createDirectories(tempFile.getParent());

            if (!Files.exists(tempFile)) {
                List<String> lines = Arrays.asList(
                        "type,fruit,quantity",
                        "b,banana,20",
                        "s,apple,100"
                );
                Files.write(tempFile, lines);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to set up test file", e);
        }
    }

    @Test
    void readValidFile() {
        List<String> lines = fileReader.read(tempFile.toString());
        assertEquals(3, lines.size());
        assertEquals("type,fruit,quantity", lines.get(0));
        assertEquals("b,banana,20", lines.get(1));
    }

    @Test
    void readNonExistentFile() {
        assertThrows(RuntimeException.class, () -> fileReader.read("nonexistent.csv"));
    }
}
