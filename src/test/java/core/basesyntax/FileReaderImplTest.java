package core.basesyntax;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.impl.FileReaderImpl;
import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FileReaderImplTest {
    private FileReaderImpl fileReader;

    @BeforeEach
    public void setUp() {
        fileReader = new FileReaderImpl();
    }

    @Test
    public void testReadTransactionsFileNotFound() {
        IOException thrown = assertThrows(IOException.class, () -> {
            fileReader.readTransactions("nonexistentfile.csv");
        });
        assertEquals("Error reading file: nonexistentfile.csv", thrown.getMessage());
    }
}
