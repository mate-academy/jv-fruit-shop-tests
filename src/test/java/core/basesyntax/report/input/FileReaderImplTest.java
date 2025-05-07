package core.basesyntax.report.input;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.model.FruitOperation;
import core.basesyntax.report.convertdata.DataConvertorImpl;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileReaderImplTest {
    private static final String VALID_FILE = "src/test/resources/test_data.csv";
    private static final String EMPTY_FILE = "src/test/resources/empty_file.csv";
    private static final String INVALID_FILE = "src/test/resources/non_existing_file.csv";
    private FileReaderImpl fileReader;

    @BeforeEach
    void setUp() {
        fileReader = new FileReaderImpl();
    }

    @Test
    void readTestFile_ok() {
        List<String> actual = fileReader.read(VALID_FILE);

        List<FruitOperation> expected = Arrays.asList(
                new FruitOperation(FruitOperation.Operation.BALANCE, "banana", 20),
                new FruitOperation(FruitOperation.Operation.BALANCE, "apple", 35),
                new FruitOperation(FruitOperation.Operation.RETURN, "apple", 10),
                new FruitOperation(FruitOperation.Operation.PURCHASE, "apple", 24),
                new FruitOperation(FruitOperation.Operation.PURCHASE, "banana", 5),
                new FruitOperation(FruitOperation.Operation.SUPPLY, "banana", 50),
                new FruitOperation(FruitOperation.Operation.SUPPLY, "banana", 70),
                new FruitOperation(FruitOperation.Operation.PURCHASE, "banana", 13),
                new FruitOperation(FruitOperation.Operation.PURCHASE, "apple", 22),
                new FruitOperation(FruitOperation.Operation.PURCHASE, "banana", 15),
                new FruitOperation(FruitOperation.Operation.SUPPLY, "banana", 50)
        );

        DataConvertorImpl converter = new DataConvertorImpl();
        List<FruitOperation> transactionList = converter.convertToTransaction(actual);
        assertEquals(expected, transactionList);
    }

    @Test
    void readEmptyFile_returnsEmptyList() {
        List<String> actual = fileReader.read(EMPTY_FILE);
        assertTrue(actual.isEmpty(), "Expected an empty list when reading an empty file");
    }

    @Test
    void readInvalidFile_throwsRuntimeException() {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            fileReader.read(INVALID_FILE);
        });

        assertNotNull(exception.getCause());
        assertTrue(exception.getCause() instanceof java.io.FileNotFoundException);
    }
}
