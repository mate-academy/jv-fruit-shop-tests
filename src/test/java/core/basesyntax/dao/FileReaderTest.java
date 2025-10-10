package core.basesyntax.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.exception.FruitShopException;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class FileReaderTest {
    @TempDir
    private Path tempDir;

    @Test
    void readShouldReturnLines_whenFileExists() {
        FileWriter writer = new FileWriterImpl();
        FileReader reader = new FileReaderImpl();

        Path file = tempDir.resolve("input.csv");
        String data = "type,fruit,quantity\nb,banana,2\n";
        writer.write(data, file.toString());

        List<String> lines = reader.read(file.toString());
        assertFalse(lines.isEmpty());
        assertEquals("type,fruit,quantity", lines.get(0));
    }

    @Test
    void readShouldThrow_whenPathIsNull() {
        FileReader reader = new FileReaderImpl();
        assertThrows(FruitShopException.class, () -> reader.read(null));
    }
}
