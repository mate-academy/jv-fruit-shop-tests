package core.basesyntax.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
                new FruitTransaction(FruitTransaction.Operation.fromCode("b"), "apple", 100),
                new FruitTransaction(FruitTransaction.Operation.fromCode("s"), "banana", 50),
                new FruitTransaction(FruitTransaction.Operation.fromCode("p"), "orange", 30),
                new FruitTransaction(FruitTransaction.Operation.fromCode("r"), "apple", 20)
        );
    }

    @Test
    void shouldReturnCorrectTransactionList_WhenValidInputProvided() {
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
    void shouldSkipHeaderRow_WhenInputContainsHeaders() {
        List<FruitTransaction> actualTransactions = dataConverter.converterToTransaction(inputData);
        assertEquals(expectedTransactions.size(), actualTransactions.size());

        for (FruitTransaction transaction : actualTransactions) {
            assertFalse(transaction.getFruit().equals("type"), "Header should be skipped");
            assertFalse(transaction.getFruit().equals("fruit"), "Header should be skipped");
            assertFalse(String.valueOf(transaction.getQuantity()).equals("quantity"),
                    "Header should be skipped");
        }
    }
}
