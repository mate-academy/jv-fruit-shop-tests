package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.FileReader;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class FileReaderTest {
    private FileReader fileReader = new FileReaderImpl();

    @Test
    void read_wrongPath_notOk() {
        assertThrows(RuntimeException.class,
                () -> fileReader.read("src/main/fruits.csv"),"bad path");
    }

    @Test
    void read_severalLinesReading_ok() {
        List<String> expected = new ArrayList<>();
        expected.add("type,fruit,quantity");
        expected.add("b,banana,20");
        expected.add("s,banana,100");
        assertEquals(expected, fileReader.read("src/test/resources/fruits.csv"));
    }
}
