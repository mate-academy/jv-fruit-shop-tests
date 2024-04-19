package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.Reader;
import java.util.List;
import org.junit.jupiter.api.Test;

class ReaderImplTest {
    @Test
    void readFile_validDB_Ok() {
        String dataBasePath = "src/test/resources/database.csv";
        List<String> expected = List.of("type,fruit,quantity",
                "b,banana,20",
                "b,apple,100",
                "s,banana,100",
                "p,banana,13",
                "r,apple,10");
        Reader reader = new ReaderImpl();
        List<String> actual = reader.readFile(dataBasePath);
        assertEquals(expected, actual);
    }

    @Test
    void readFile_incorrectFilePath_notOk() {
        Reader reader = new ReaderImpl();
        String filePath = "";
        assertThrows(RuntimeException.class, () -> reader.readFile(filePath));
    }
}
