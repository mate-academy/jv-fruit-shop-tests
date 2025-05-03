package service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static service.impl.DataConverterImplTest.createTransaction;

import java.util.Arrays;
import java.util.List;
import model.FruitTransaction;
import org.junit.Before;
import org.junit.Test;
import service.DataConverter;

public class FileReaderImplTest {
    private static final String VALID_FILE = "src/test/resources/test.csv";
    private static final String EMPTY_FILE = "src/test/resources/empty.csv";
    private static final String NON_EXISTENT_FILE = "src/test/resources/nonExistentFile.csv";

    private FileReaderImpl fileReader;

    @Before
    public void setUp() {
        fileReader = new FileReaderImpl();
    }

    @Test
    public void readValidFile_ok() {
        List<String> lines = fileReader.read(VALID_FILE.toString());

        List<FruitTransaction> expected = Arrays.asList(
                createTransaction("b", "banana",20),
                createTransaction("b", "apple",100),
                createTransaction("s", "banana",100),
                createTransaction("p", "banana",13),
                createTransaction("r", "apple",10),
                createTransaction("p", "apple",20),
                createTransaction("p", "banana",5),
                createTransaction("s", "banana",50)
        );

        DataConverter dataConverter = new DataConverterImpl();
        List<FruitTransaction> transactions = dataConverter.convertToTransaction(lines);
        assertEquals(expected, transactions);
    }

    @Test
    public void readInvalidFile_notOk() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> fileReader.read(NON_EXISTENT_FILE));
        assertEquals("File not found: " + NON_EXISTENT_FILE, exception.getMessage());
    }

    @Test
    public void readEmptyFile_ok() {
        List<String> lines = fileReader.read(EMPTY_FILE);
        assertTrue(lines.isEmpty(), "Expected the file to be empty");
    }
}
