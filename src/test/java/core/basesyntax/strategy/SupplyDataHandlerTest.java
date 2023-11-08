package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import core.basesyntax.data.FruitTransaction;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SupplyDataHandlerTest {
    private static final int QUANTITY_BANANA = 75;
    private static final String FRUIT_BANANA = "banana";
    private SupplyDataHandler supplyDataHandler;
    private FruitTransaction validTransaction;
    private FruitTransaction invalidTransaction;
    private Map<String, Integer> validData;

    @BeforeEach
    void setUp() {
        supplyDataHandler = new SupplyDataHandler();
    }

    @Test
    void validPurchaseOperationInTransaction_Ok() {
        validTransaction =
                new FruitTransaction(FruitTransaction.Operation.BALANCE,
                        FRUIT_BANANA, QUANTITY_BANANA);
        assertTrue(validTransaction.getOperation()
                .equals(FruitTransaction.Operation.BALANCE));
    }

    @Test
    void invalidPurchaseOperationInTransaction_notOk() {
        invalidTransaction =
                new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                        FRUIT_BANANA, QUANTITY_BANANA);
        assertFalse(FruitTransaction.Operation.BALANCE
                .equals(invalidTransaction.getOperation()));
    }

    @Test
    void validProcessWithData_Ok() {
        validData = Map.of(
                FRUIT_BANANA,QUANTITY_BANANA);
        validTransaction =
                new FruitTransaction(FruitTransaction.Operation.BALANCE,
                        FRUIT_BANANA, QUANTITY_BANANA);
        assertEquals(
                FRUIT_BANANA, validTransaction.getFruit());
    }

    @Test
    void transactionIsNull_notOk() {
        validData = Map.of(
                FRUIT_BANANA,QUANTITY_BANANA
        );
        assertThrows(
                IllegalArgumentException.class,
                () -> supplyDataHandler.processWithData(null, validData));
    }

    @Test
    void mapDataIsNull_notOk() {
        validTransaction =
                new FruitTransaction(FruitTransaction.Operation.BALANCE,
                        FRUIT_BANANA, QUANTITY_BANANA);
        assertThrows(
                IllegalArgumentException.class, () -> supplyDataHandler
                        .processWithData(validTransaction, null));
    }
}
