package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.FileWriter;
import org.junit.jupiter.api.Test;

class FileWriterImplTest {
    private final FileWriter fileWriter = new FileWriterImpl();

    @Test
    void reportIsNull_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            fileWriter.writeToFile(null,"correctPath.csv");
        });
    }

    @Test
    void testHandlingInvalidPath() {
        assertThrows(RuntimeException.class, () -> {
            fileWriter.writeToFile("Hello, world!","C:/invalid_path/file.csv");
        });

    }

}
