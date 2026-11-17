package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.FileReader;
import org.junit.jupiter.api.Test;

class FileReaderImplTest {
    private final FileReader fileReader = new FileReaderImpl();

    @Test
    void read_nullPath_notOk() {
        assertThrows(IllegalArgumentException.class, () -> {
            fileReader.read(null);
        });
    }

    @Test
    void read_nonExistentFile_notOk() {
        assertThrows(RuntimeException.class, () -> {
            fileReader.read("non_existent_file.csv");
        });
    }
}
