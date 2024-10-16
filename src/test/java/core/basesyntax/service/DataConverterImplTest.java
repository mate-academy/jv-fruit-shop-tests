package core.basesyntax.service;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.model.FruitTransaction;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class DataConverterImplTest {
    private final DataConverterImpl dataConverter = new DataConverterImpl();

    @Test
    void testConvertToTransactionWithValidData() {
        List<String> inputData = new ArrayList<>();
        inputData.add("b,apple,100");
        inputData.add("s,banana,50");
        inputData.add("p,orange,25");
        inputData.add("r,apple,10");

        List<FruitTransaction> transactions = dataConverter.convertToTransaction(inputData);

        assertEquals(transactions.get(0).getFruit(), "apple");
        assertEquals(transactions.get(1).getOperation(), FruitTransaction.Operation.SUPPLY);
        assertEquals(transactions.get(2).getQuantity(), 25);
        assertEquals(4, transactions.size(),
                "Size of the transactions list should match the input size");

    }

    @Test
    void testConvertToTransactionWithInvalidFormat() {
        List<String> inputData = new ArrayList<>();
        inputData.add("b,banana");
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            dataConverter.convertToTransaction(inputData);
        });
        assertTrue(exception.getMessage().contains("Invalid input format"),
                "Exception message should indicate an invalid input format");
    }

    @Test
    void testConvertToTransactionWithInvalidQuantity() {
        List<String> inputData = new ArrayList<>();
        inputData.add("b,apple,abc");

        NumberFormatException exception = assertThrows(NumberFormatException.class, () -> {
            dataConverter.convertToTransaction(inputData);
        });

        assertTrue(exception.getMessage().contains("For input string"),
                "Exception message should indicate an invalid number format for quantity");
    }
}
