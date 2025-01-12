package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.FileWriter;
import org.junit.jupiter.api.Test;

class FileWriterTest {
    private FileWriter fileWriter = new FileWriterImpl();

    @Test
    void write_wrongContent_notOk() {
        assertThrows(RuntimeException.class,
                () -> fileWriter.write("", "src/test/resources/finalReport.csv"));
    }

    @Test
    void write_simpleFileWriting_Ok() {
        fileWriter.write("kuku", "src/test/resources/finalReport.csv");
    }
}
