package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.Reader;
import org.junit.jupiter.api.Test;

class ReaderImplTest {
    private final Reader reader = new ReaderImpl();

    @Test
    void readFromFile_noFile_notOk() {
        String path = "";
        assertThrows(RuntimeException.class, () -> reader.readFromFile(path),
                "Expected the file to be missing");
    }

    @Test
    void readFromFile_fileExists_ok() {
        String path = "src/test/resources/testOk.csv";
        assertFalse(reader.readFromFile(path).isEmpty(),
                "Expected file should contain data");
    }

    @Test
    void readFromFile_fileEmpty_notOk() {
        String path = "src/test/resources/testEmpty.csv";
        assertThrows(IllegalArgumentException.class, () -> reader.readFromFile(path),
                "Expected file to be empty");
    }
}
