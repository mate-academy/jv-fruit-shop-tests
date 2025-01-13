package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.FileWriter;
import org.junit.jupiter.api.Test;

class FileWriterTest {
    private FileWriter fileWriter = new FileWriterImpl();
    private static final String correctPath = "src/test/resources/finalReport.csv";

    @Test
    void write_wrongContent_notOk() {
        assertThrows(RuntimeException.class,
                () -> fileWriter.write(null, correctPath));
    }

    @Test
    void write_simpleFileWriting_Ok() {
        String result = "fruit,quantity" + System.lineSeparator() + "banana,55";
        assertDoesNotThrow(() -> fileWriter.write(result, correctPath));
    }
}
