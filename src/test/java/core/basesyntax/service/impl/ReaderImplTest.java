package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.Reader;
import org.junit.jupiter.api.Test;

class ReaderImplTest {
    private final Reader reader = new ReaderImpl();
    private String path;

    @Test
    void readFromFile_noFile_notOk() {
        path = "";
        assertThrows(RuntimeException.class, () -> reader.readFromFile(path),
                "File to read should not have been found");
    }

    @Test
    void readFromFile_fileExists_ok() {
        path = "src/test/resources/testOk.csv";
        assertFalse(reader.readFromFile(path).isEmpty(),
                "The provided file: " + path + " is empty");
    }

    @Test
    void readFromFile_fileEmpty_notOk() {
        path = "src/test/resources/testEmpty.csv";
        assertThrows(IllegalArgumentException.class, () -> reader.readFromFile(path),
                "The provided file: " + path + " is not empty");
    }
}
