package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FileReaderImplTest {
    private static final String INCORRECT_FILE = "incorrect.csv";
    private static final String CORRECT_FILE = "src/test/resources/transactions.csv";
    private static FileReaderImpl fileReader;

    @BeforeEach
    void setUp() {
        fileReader = new FileReaderImpl();
    }

    @Test
    void read_incorrectFile_notOk() {
        assertThrows(RuntimeException.class,
                () -> fileReader.read(INCORRECT_FILE));
    }

    @Test
    public void read_correctFilePath_ok() {
        fileReader.read(CORRECT_FILE);
    }
}
