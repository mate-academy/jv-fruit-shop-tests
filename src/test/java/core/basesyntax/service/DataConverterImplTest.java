package core.basesyntax.service;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.model.FruitTransaction;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class DataConverterImplTest {
    private static final String BALANCE_APPLE_100 = "b,apple,100";
    private static final String SUPPLY_BANANA_50 = "s,banana,50";
    private static final String PURCHASE_ORANGE_25 = "p,orange,25";
    private static final String RETURN_APPLE_10 = "r,apple,10";
    private static final String INVALID_FORMAT_BANANA = "b,banana";
    private static final String INVALID_QUANTITY_APPLE = "b,apple,abc";

    private final DataConverterImpl dataConverter = new DataConverterImpl();

    @Test
    void shouldConvertToTransactions_whenDataIsValid() {
        List<String> inputData = new ArrayList<>();
        inputData.add(BALANCE_APPLE_100);
        inputData.add(SUPPLY_BANANA_50);
        inputData.add(PURCHASE_ORANGE_25);
        inputData.add(RETURN_APPLE_10);

        List<FruitTransaction> transactions = dataConverter.convertToTransaction(inputData);

        assertEquals(transactions.get(0).getFruit(), "apple");
        assertEquals(transactions.get(1).getOperation(), FruitTransaction.Operation.SUPPLY);
        assertEquals(transactions.get(2).getQuantity(), 25);
        assertEquals(4, transactions.size(),
                "Size of the transactions list should match the input size");

    }

    @Test
    void shouldThrowException_whenDataHasInvalidFormat() {
        List<String> inputData = new ArrayList<>();
        inputData.add(INVALID_FORMAT_BANANA);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            dataConverter.convertToTransaction(inputData);
        });
        assertTrue(exception.getMessage().contains("Invalid input format"),
                "Exception message should indicate an invalid input format");
    }
}
