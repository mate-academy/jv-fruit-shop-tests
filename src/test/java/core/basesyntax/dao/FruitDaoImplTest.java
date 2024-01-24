package core.basesyntax.dao;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;

class FruitDaoImplTest {

    @Test
    void filePathIs_NotOk() {
        String actual = "invalid_FILE_NAME";
        assertThrows(NoSuchFileException.class, () -> Files.readAllLines(Path.of(actual)));
    }
}
