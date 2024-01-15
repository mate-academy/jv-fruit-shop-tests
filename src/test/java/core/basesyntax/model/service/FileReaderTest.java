package core.basesyntax.model.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.FileReader;
import core.basesyntax.service.impl.FileReaderImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FileReaderTest {
    private static final String INCORRECT_FILE_NAME = null;
    private static final String INCORRECT_FILE_PATH = "/resources/input.csv";

    private FileReader fileReader;

    @BeforeEach
    void setUp() {
        fileReader = new FileReaderImpl();
    }

    @Test
    void read_fileName_notOk() {
        assertThrows(NullPointerException.class, () ->
                fileReader.read(INCORRECT_FILE_NAME));
    }

    @Test
    void read_filePath_notOk() {
        assertThrows(RuntimeException.class, () -> fileReader.read(INCORRECT_FILE_PATH));
    }
}
