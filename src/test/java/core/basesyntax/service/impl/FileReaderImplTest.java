package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileReaderImplTest {
    private FileReaderImpl fileReaderImpl;

    @BeforeEach
    void beforeEach() {
        fileReaderImpl = new FileReaderImpl();
    }

    @Test
    void readTransactionsFromFile_validFile_ok() {
        List<String> actual = fileReaderImpl.readTransactionsFromFile("testFile.csv");
        List<String> expected = new ArrayList<>();
        expected.add("a, apple,12");
        assertEquals(expected, actual);
    }

    @Test
    void readTransactionsFromFile_wrongFileName_notOk() {
        assertThrows(RuntimeException.class, () ->
                fileReaderImpl.readTransactionsFromFile("test.csv"));
    }

    @Test
    void readTransactionsFromFile_fileWithOnlyHeader_returnsEmptyList() {
        assertTrue(fileReaderImpl.readTransactionsFromFile("testFileEmptyList.csv").isEmpty());
    }
}
