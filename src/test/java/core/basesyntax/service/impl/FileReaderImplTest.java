package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.FileReader;
import org.junit.jupiter.api.Test;

class FileReaderImplTest {
    private final FileReader fileReader = new FileReaderImpl();

    @Test
    void wrongPath_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            fileReader.readFromFile("incorrectPath.csv");
        });
    }
}
