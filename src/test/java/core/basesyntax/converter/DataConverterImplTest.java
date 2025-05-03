package core.basesyntax.converter;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.transaction.FruitTransaction;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DataConverterImplTest {
    private DataConverter dataConverter = new DataConverterImpl();
    private List<String> inputData;
    private List<FruitTransaction> expectedTransactions;

    @BeforeEach
    void setUp() {
        inputData = Arrays.asList(
                "type, fruit, quantity",
                "b,apple,100",
                "s,banana,50",
                "p,orange,30",
                "r,apple,20"
        );
        expectedTransactions = Arrays.asList(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 100),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 50),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "orange", 30),
                new FruitTransaction(FruitTransaction.Operation.RETURN, "apple", 20)
        );
    }

    @Test
    void converterToTransaction_returnTransactionList_success() {
        List<FruitTransaction> actualTransactions = dataConverter.converterToTransaction(inputData);
        assertEquals(expectedTransactions.size(), actualTransactions.size());
        assertNotNull(actualTransactions);
        for (int i = 0; i < expectedTransactions.size(); i++) {
            FruitTransaction expected = expectedTransactions.get(i);
            FruitTransaction actual = actualTransactions.get(i);
            assertEquals(expected, actual);
        }
    }

    @Test
    void converterToTransaction_skipHeader_success() {
        List<String> inputData = List.of("type,fruit,quantity");
        List<FruitTransaction> actualTransaction = dataConverter.converterToTransaction(inputData);
        assertTrue("The list should be empty when input contains only headers",
                actualTransaction.isEmpty());
    }

    @Test
    void converterToTransaction_invalidColumnCount_shouldThrowException() {
        List<String> invalidInputData = List.of("type, fruit", "b, apple");
        assertThrows(IllegalArgumentException.class, () ->
                dataConverter.converterToTransaction(invalidInputData),
                "Expected IllegalArgumentException due to incorrect column count");
    }

    @Test
    void converterToTransaction_invalidQuantity_shouldThrowException() {
        List<String> invalidQuantityData = List.of("type,fruit,quantity",
                "type,fruit,invalidQuantity");
        assertThrows(IllegalArgumentException.class, () ->
                dataConverter.converterToTransaction(invalidQuantityData),
                "Expected IllegalArgumentException due to invalid quantity");
    }
}
