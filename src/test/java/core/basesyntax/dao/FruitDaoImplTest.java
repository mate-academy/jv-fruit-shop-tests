package core.basesyntax.dao;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class FruitDaoImplTest {
    private FruitDao fruitDao = new FruitDaoImpl();

    @Test
    void getCsv_NotOk() {
        String actual = "invalid_FILE_NAME";
        assertThrows(NoSuchFileException.class, () -> Files.readAllLines(Path.of(actual)));
    }

    @Test
    void getCsvIs_Ok() {
        List<String> csvData = fruitDao.getCsv();
        Assertions.assertNotNull(csvData);
        Assertions.assertFalse(csvData.isEmpty());
    }
}
