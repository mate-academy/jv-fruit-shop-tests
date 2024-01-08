package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.Mapper;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FruitTransactionMapperTest {
    private static Mapper<FruitTransaction> fruitTransactionMapper;

    @BeforeAll
    static void beforeAll() {
        fruitTransactionMapper = new FruitTransactionMapper();
    }

    @Test
    void getMappedObject_validData_Ok() {
        String[] lineFromFile = new String[]{"b", "banana", "20"};
        FruitTransaction expected = new FruitTransaction(FruitTransaction.Operation.BALANCE,
                "banana", 20);
        FruitTransaction actual = fruitTransactionMapper.getMappedObject(lineFromFile);
        assertEquals(expected, actual);
    }

    @Test
    void getMapOperation_validOperation_Ok() {
        List<String[]> validOperations = List.of(
                new String[]{"b", "banana", "20"},
                new String[]{"r", "banana", "20"},
                new String[]{"p", "banana", "20"},
                new String[]{"s", "banana", "20"});
        List<FruitTransaction> expected = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 20),
                new FruitTransaction(FruitTransaction.Operation.RETURN, "banana", 20),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 20),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 20));
        List<FruitTransaction> actual = new ArrayList<>();
        for (String[] line : validOperations) {
            actual.add(fruitTransactionMapper.getMappedObject(line));
        }
        assertIterableEquals(expected, actual);
    }

    @Test
    void getMapOperation_checkMessageAndMessageForInvalidOperation_Ok() {
        String[] lineFromFileWithInvalidOperation = new String[]{"q", "banana", "20"};
        RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> {
            fruitTransactionMapper.getMappedObject(lineFromFileWithInvalidOperation);
        });
        assertEquals("q operation is not supported", runtimeException.getMessage());
    }

    @Test
    void getMapOperation_invalidDataWithLengthMoreThanThree_notOk() {
        String[] line = new String[]{"q", "banana", "20", ".00"};
        RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> {
            fruitTransactionMapper.getMappedObject(line);
        });
        assertEquals("Incorrect input data: " + Arrays.toString(line),
                runtimeException.getMessage());
    }

    @Test
    void getMapOperation_invalidDataWithLengthLessThanThree_notOk() {
        String[] line = new String[]{"q", "banana"};
        RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> {
            fruitTransactionMapper.getMappedObject(line);
        });
        assertEquals("Incorrect input data: " + Arrays.toString(line),
                runtimeException.getMessage());
    }

    @Test
    void getMapOperation_invalidDataWithEmptyLines_notOk() {
        int columnNum = 3;
        String[] line = new String[]{"q", "banana", ""};
        RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> {
            fruitTransactionMapper.getMappedObject(line);
        });
        assertEquals("Data in " + columnNum + " column is empty.",
                runtimeException.getMessage());
    }

    @Test
    void getMapOperation_invalidDataWithNegativeQuantity_notOk() {
        String[] line = new String[]{"q", "banana", "-33"};
        RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> {
            fruitTransactionMapper.getMappedObject(line);
        });
        assertEquals("Quantity should be positive.",
                runtimeException.getMessage());
    }
}
