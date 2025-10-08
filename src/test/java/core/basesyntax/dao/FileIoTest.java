package core.basesyntax.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;

import core.basesyntax.exception.FruitShopException;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class FileIoTest {
    @TempDir
    private Path tempDir;

    private Path getTempDir() {
        return tempDir;
    }

    @Test
    void fileReadWriteShouldWork() {
        FileWriter writer = new FileWriterImpl();
        FileReader reader = new FileReaderImpl();

        Path file = getTempDir().resolve("input.csv");
        String data = "type,fruit,quantity\nb,banana,2\n";
        writer.write(data, file.toString());

        List<String> lines = reader.read(file.toString());
        assertFalse(lines.isEmpty());
        assertEquals("type,fruit,quantity", lines.get(0));
    }

    @Test
    void fileReaderShouldThrowOnNull() {
        FileReader reader = new FileReaderImpl();
        assertThrows(FruitShopException.class, () -> reader.read(null));
    }

    @Test
    void fileWriterShouldThrowOnNull() {
        FileWriter writer = new FileWriterImpl();
        assertThrows(FruitShopException.class, () -> writer.write(null, null));
    }
}
