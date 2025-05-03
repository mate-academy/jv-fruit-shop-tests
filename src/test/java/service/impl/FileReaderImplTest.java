package service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static service.impl.ConverterImplTest.createTransaction;

import java.util.Arrays;
import java.util.List;
import model.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.Converter;
import service.FileReader;

class FileReaderImplTest {
    private static final String VALID_FILE = "src/test/resources/test.csv";
    private static final String EMPTY_FILE = "src/test/resources/empty.csv";
    private static final String INVALID_FILE = "src/test/resources/noSuchFile.csv";

    private FileReader reader;

    @BeforeEach
    void setUp() {
        reader = new FileReaderImpl();
    }

    @Test
    void readTestFile_ok() {
        List<String> actual = reader.read(VALID_FILE);

        List<Transaction> expected = Arrays.asList(
                createTransaction("b", "apple", 100),
                createTransaction("s", "banana", 50),
                createTransaction("s", "orange", 60),
                createTransaction("r", "grape", 40),
                createTransaction("b", "banana", 90),
                createTransaction("p", "orange", 30),
                createTransaction("p", "grape", 25),
                createTransaction("r", "apple", 15),
                createTransaction("s", "apple", 80),
                createTransaction("p", "banana", 40)
        );

        Converter converter = new ConverterImpl();

        List<Transaction> transactionList = converter.convertTransaction(actual);

        assertEquals(expected, transactionList);

    }

    @Test
    void readEmptyFile_ok() {
        List<String> lines = reader.read(EMPTY_FILE);
        assertTrue(lines.isEmpty(), "Empty file expected");
    }

    @Test
    void readInvalidFile_notOk() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> reader.read(INVALID_FILE));
        assertEquals("File not found: " + INVALID_FILE, exception.getMessage());
    }
}
