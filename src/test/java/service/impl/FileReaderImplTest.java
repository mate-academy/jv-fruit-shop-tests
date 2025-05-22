package service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;
import model.FruitTransaction;
import org.junit.Before;
import org.junit.Test;

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
        List<String> lines = Arrays.asList(
                "b,banana,20",
                "b,apple,100",
                "s,banana,100",
                "p,banana,13",
                "r,apple,10",
                "p,apple,20",
                "p,banana,5",
                "s,banana,50"
        );

        List<FruitTransaction> expected = Arrays.asList(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 20),
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 100),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 100),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 13),
                new FruitTransaction(FruitTransaction.Operation.RETURN, "apple", 10),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", 20),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 5),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 50)
        );

        assertEquals(expected.size(), lines.size(),
                "Number of lines in file doesn't match expected");
        for (int i = 0; i < expected.size(); i++) {
            FruitTransaction expectedTransaction = expected.get(i);
            String[] line = lines.get(i).split(",");

            FruitTransaction actualTransaction = new FruitTransaction(
                    FruitTransaction.Operation.getOperationFromCode(line[0]),
                    line[1],
                    Integer.parseInt(line[2])
            );

            assertEquals(expectedTransaction, actualTransaction,
                    "Transaction at index " + i + " does not match expected.");
        }
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
