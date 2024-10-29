package core.basesyntax.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import core.basesyntax.transaction.FruitTransaction;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

class DataConverterImplTest {
    private DataConverter dataConverter = new DataConverterImpl();

    @Test
    void shouldReturnCorrectTransactionList_WhenValidInputProvided() {
        List<String> inputData = Arrays.asList(
                "type, fruit, quantity",
                "b,apple,100",
                "s,banana,50",
                "p,orange,30",
                "r,apple,20"
        );
        List<FruitTransaction> expectedTransactions = Arrays.asList(
                new FruitTransaction(FruitTransaction.Operation.fromCode("b"),
                        "apple", 100),
                new FruitTransaction(FruitTransaction.Operation.fromCode("s"),
                        "banana", 50),
                new FruitTransaction(FruitTransaction.Operation.fromCode("p"),
                        "orange", 30),
                new FruitTransaction(FruitTransaction.Operation.fromCode("r"),
                        "apple", 20)
        );
        List<FruitTransaction> actualTransactions = dataConverter.converterToTransaction(inputData);

        assertNotNull(actualTransactions);
        for (int i = 0; i < expectedTransactions.size(); i++) {
            FruitTransaction expected = expectedTransactions.get(i);
            FruitTransaction actual = actualTransactions.get(i);
            assertEquals(expected.getOperation(), actual.getOperation(),
                    "Operation should be "
                            + expected.getOperation() + " but was " + actual.getOperation());
            assertEquals(expected.getFruit(), actual.getFruit(),
                    "Fruit should be "
                            + expected.getFruit() + " but was " + actual.getFruit());
            assertEquals(expected.getQuantity(), actual.getQuantity(),
                    "Quantity should be "
                            + expected.getQuantity() + " but was " + actual.getQuantity());
        }
    }

    @Test
    void shouldSkipHeaderRow_WhenInputContainsHeaders() {
        List<String> inputData = Arrays.asList(
                "type, fruit, quantity",
                "b,apple,100",
                "s,banana,50",
                "p,orange,30",
                "r,apple,20"
        );
        List<FruitTransaction> expectedTransactions = Arrays.asList(
                new FruitTransaction(FruitTransaction.Operation.fromCode("b"),
                        "apple", 100),
                new FruitTransaction(FruitTransaction.Operation.fromCode("s"),
                        "banana", 50),
                new FruitTransaction(FruitTransaction.Operation.fromCode("p"),
                        "orange", 30),
                new FruitTransaction(FruitTransaction.Operation.fromCode("r"),
                        "apple", 20)
        );
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
