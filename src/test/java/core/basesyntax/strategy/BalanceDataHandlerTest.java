package core.basesyntax.strategy;

import core.basesyntax.data.FruitTransaction;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BalanceDataHandlerTest {
    private static final int QUANTITY_BANANA = 75;
    private static final String FRUIT_BANANA = "banana";
    private BalanceDataHandler balanceDataHandler = new BalanceDataHandler();
    private FruitTransaction validTransaction =
            new FruitTransaction(FruitTransaction.Operation.BALANCE,
                    FRUIT_BANANA, QUANTITY_BANANA);
    private FruitTransaction invalidTransaction =
            new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                    FRUIT_BANANA, QUANTITY_BANANA);
    private Map<String, Integer> validData = Map.of(
            FRUIT_BANANA,QUANTITY_BANANA
    );

    @Test
    void validBalanceOperationInTransaction_Ok() {
        Assertions.assertTrue(validTransaction.getOperation()
                .equals(FruitTransaction.Operation.BALANCE));
    }

    @Test
    void invalidBalanceOperationInTransaction_notOk() {
        Assertions.assertFalse(FruitTransaction.Operation.BALANCE
                .equals(invalidTransaction.getOperation()));
    }

    @Test
    void validProcessWithData_Ok() {
        Assertions.assertTrue(
                validData.containsKey(validTransaction.getFruit()));
    }

    @Test
    void transactionIsNull_notOk() {
        Assertions.assertThrows(
                IllegalArgumentException.class, () -> balanceDataHandler
                        .processWithData(null, validData));
    }

    @Test
    void mapDataIsNull_notOk() {
        Assertions.assertThrows(
                IllegalArgumentException.class, () -> balanceDataHandler
                        .processWithData(validTransaction, null));
    }
}
