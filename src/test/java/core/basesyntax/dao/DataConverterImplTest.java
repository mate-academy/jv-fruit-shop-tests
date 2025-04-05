package core.basesyntax.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class DataConverterImplTest {
    protected static DataConverter dataConverter;

    @BeforeAll
    public static void setUp() {
        dataConverter = new DataConverterImpl();
    }

    @Test
    void convertToTransaction_validListOfTransaction() {
        List<String> inputData = List.of("type,fruit,quantity", "b,banana,20", "b,apple,100",
                "s,banana,100", "p,banana,13", "r,apple,10", "p,apple,20", "p,banana,5");
        List<FruitTransaction> fruitTransactions = dataConverter.convertToTransaction(inputData);
        int expected = inputData.size() - 1;
        assertEquals(expected, fruitTransactions.size(), String.format("Invalid quantity "
                + "of operations! Expected: " + expected + "Actual: "
                + fruitTransactions.size()));

        assertEquals(new FruitTransaction(Operation.BALANCE, "banana", 20),
                fruitTransactions.get(0));
        assertEquals(new FruitTransaction(Operation.BALANCE, "apple", 100),
                fruitTransactions.get(1));
        assertEquals(new FruitTransaction(Operation.SUPPLY, "banana", 100),
                fruitTransactions.get(2));
        assertEquals(new FruitTransaction(Operation.PURCHASE, "banana", 13),
                fruitTransactions.get(3));
        assertEquals(new FruitTransaction(Operation.RETURN, "apple", 10),
                fruitTransactions.get(4));
        assertEquals(new FruitTransaction(Operation.PURCHASE, "apple", 20),
                fruitTransactions.get(5));
        assertEquals(new FruitTransaction(Operation.PURCHASE, "banana", 5),
                fruitTransactions.get(6));
    }

    @Test
    public void convertToTransaction_differentFruits_returnsListOfTransactions() {
        List<String> input = List.of("type,fruit,quantity", "b,banana,10", "s,apple,20",
                "p,orange,5", "r,grape,3", "b,kiwi,15");
        List<FruitTransaction> transactions = dataConverter.convertToTransaction(input);
        assertEquals(5, transactions.size());
        assertEquals(new FruitTransaction(Operation.BALANCE, "banana", 10), transactions.get(0));
        assertEquals(new FruitTransaction(Operation.SUPPLY, "apple", 20), transactions.get(1));
        assertEquals(new FruitTransaction(Operation.PURCHASE, "orange", 5), transactions.get(2));
        assertEquals(new FruitTransaction(Operation.RETURN, "grape", 3), transactions.get(3));
        assertEquals(new FruitTransaction(Operation.BALANCE, "kiwi", 15), transactions.get(4));
    }

    @Test
    public void convertToTransaction_nullInput_returnsEmptyList() {
        List<FruitTransaction> transactions = dataConverter.convertToTransaction(null);
        assertEquals(0, transactions.size());
    }

    @Test
    public void convertToTransaction_invalidOperationCode_throwsIllegalArgumentException() {
        List<String> input = List.of("type,fruit,quantity", "x,banana,10");
        assertThrows(IllegalArgumentException.class,
                () -> dataConverter.convertToTransaction(input));
    }

    @Test
    public void convertToTransaction_invalidQuantity_throwsNumberFormatException() {
        List<String> input = List.of("type,fruit,quantity", "b,banana,abc");
        assertThrows(NumberFormatException.class, () -> dataConverter.convertToTransaction(input));
    }
}
