package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.FileWriter;
import org.junit.jupiter.api.Test;

class FileWriterTest {
    private FileWriter fileWriter = new FileWriterImpl();

    @Test
    void write_wrongContent_NotOk() {
        assertThrows(RuntimeException.class, () -> fileWriter.write("", "finalReport.csv"));
    }

    @Test
    void write_wrongPath_NotOk() {
        fileWriter.write("yy", "C:/Windows/System32/test.txt");
    }

    @Test
    void write_Ok() {
        fileWriter.write("kuku", "finalReport.csv");
    }
}
