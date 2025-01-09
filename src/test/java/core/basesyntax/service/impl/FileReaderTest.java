package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.FileReader;
import org.junit.jupiter.api.Test;

class FileReaderTest {
    private FileReader fileReader = new FileReaderImpl();

    @Test
    void read_wrongPath_NotOk() {
        assertThrows(RuntimeException.class, ()
                -> fileReader.read("src/main/fruits.csv"),"bad path");
    }

    @Test
    void read_Ok() {
        assertEquals(11, fileReader.read("src/main/resources/fruits.csv").size());
    }
}
