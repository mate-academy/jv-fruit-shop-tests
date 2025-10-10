package core.basesyntax.dao;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.exception.FruitShopException;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class FileWriterTest {
    @TempDir
    private Path tempDir;

    @Test
    void writeShouldThrow_whenDataOrPathIsNull() {
        FileWriter writer = new FileWriterImpl();
        assertThrows(FruitShopException.class, () -> writer.write(null, null));
    }
}
