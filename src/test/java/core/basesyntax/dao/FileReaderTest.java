package core.basesyntax.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.exception.FruitShopException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.charset.StandardCharsets;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class FileReaderTest {
    @TempDir
    private Path tempDir;

    private FileReader reader;

    @BeforeEach
    void setUp() {
        reader = new FileReaderImpl();
    }

    @Test
    void readShouldReturnLines_whenFileExists() throws Exception {
        Path file = tempDir.resolve("input.csv");
        String data = String.join(System.lineSeparator(),
                "type,fruit,quantity",
                "b,banana,2"
        ) + System.lineSeparator();

        Files.writeString(file, data, StandardCharsets.UTF_8);

        List<String> lines = reader.read(file.toString());

        assertFalse(lines.isEmpty());
        assertEquals("type,fruit,quantity", lines.get(0));
        assertEquals("b,banana,2", lines.get(1));
    }

    @Test
    void readShouldThrowOnNull() {
        assertThrows(FruitShopException.class, () -> reader.read(null));
    }
}
