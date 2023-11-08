package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import core.basesyntax.data.FruitTransaction;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BalanceDataHandlerTest {
    private static final int QUANTITY_BANANA = 75;
    private static final String FRUIT_BANANA = "banana";
    private BalanceDataHandler balanceDataHandler;
    private FruitTransaction validTransaction;
    private FruitTransaction invalidTransaction;
    private Map<String, Integer> validData;

    @BeforeEach
    void setUp() {
        balanceDataHandler = new BalanceDataHandler();
    }

    @Test
    void validBalanceOperationInTransaction_Ok() {
        validTransaction =
                new FruitTransaction(FruitTransaction.Operation.BALANCE,
                        FRUIT_BANANA, QUANTITY_BANANA);
        assertEquals(validTransaction.getOperation(),
                 (FruitTransaction.Operation.BALANCE));
    }

    @Test
    void invalidBalanceOperationInTransaction_notOk() {
        invalidTransaction =
                new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                        FRUIT_BANANA, QUANTITY_BANANA);
        assertNotEquals(FruitTransaction.Operation.BALANCE,
                invalidTransaction.getOperation());
    }

    @Test
    void validProcessWithData_Ok() {
        validData = Map.of(
                FRUIT_BANANA,QUANTITY_BANANA
        );
        validTransaction =
                new FruitTransaction(FruitTransaction.Operation.BALANCE,
                        FRUIT_BANANA, QUANTITY_BANANA);
        assertTrue(validData.containsKey(validTransaction.getFruit()));
    }

    @Test
    void transactionIsNull_notOk() {
        validData = Map.of(
                FRUIT_BANANA,QUANTITY_BANANA
        );
        assertThrows(
                IllegalArgumentException.class, () -> balanceDataHandler
                        .processWithData(null, validData));
    }

    @Test
    void mapDataIsNull_notOk() {
        validTransaction =
                new FruitTransaction(FruitTransaction.Operation.BALANCE,
                        FRUIT_BANANA, QUANTITY_BANANA);
        assertThrows(
                IllegalArgumentException.class, () -> balanceDataHandler
                        .processWithData(validTransaction, null));
    }
}
