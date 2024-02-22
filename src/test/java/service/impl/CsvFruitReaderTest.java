package service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import org.junit.jupiter.api.Test;
import service.Reader;

class CsvFruitReaderTest {
    private static final String TEST_READ_FILE_NAME = "FruitsTest.csv";
    private static final List<String> TEST_TEXT = List.of(
            "type,fruit,quantity",
            "    b,banana,20",
            "    b,apple,100",
            "    s,banana,100",
            "    p,banana,13",
            "    r,apple,10",
            "    p,apple,20",
            "    p,banana,5",
            "    s,banana,50");

    @Test
    void read_Ok() {
        Reader fruitReader = new CsvFruitReader();
        List<String> actual = fruitReader.read(TEST_READ_FILE_NAME);
        List<String> expected = TEST_TEXT;
        assertEquals(expected, actual);
    }

    @Test
    void read_NotOk() {
        Reader fruitReader = new CsvFruitReader();
        assertThrows(RuntimeException.class, () -> {
            fruitReader.read("BadFileName");
        });
    }
}
