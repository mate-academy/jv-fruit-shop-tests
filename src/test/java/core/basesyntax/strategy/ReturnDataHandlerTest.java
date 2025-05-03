package core.basesyntax.strategy;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import core.basesyntax.data.FruitTransaction;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReturnDataHandlerTest {
    private static final int QUANTITY_APPLE = 90;
    private static final String FRUIT_APPLE = "apple";
    private ReturnDataHandler returnDataHandler;
    private FruitTransaction validTransaction;
    private FruitTransaction invalidTransaction;
    private Map<String, Integer> validData = Map.of(
            FRUIT_APPLE,QUANTITY_APPLE
    );

    @BeforeEach
    void setUp() {
        returnDataHandler = new ReturnDataHandler();
    }

    @Test
    void validReturnOperationInTransaction_Ok() {
        validTransaction =
                new FruitTransaction(
                        FruitTransaction.Operation.RETURN, FRUIT_APPLE, QUANTITY_APPLE);
        assertTrue(validTransaction.getOperation()
                .equals(FruitTransaction.Operation.RETURN));
    }

    @Test
    void invalidReturnOperationInTransaction_notOk() {
        invalidTransaction =
                new FruitTransaction(
                        FruitTransaction.Operation.PURCHASE, FRUIT_APPLE, QUANTITY_APPLE);
        assertFalse(FruitTransaction.Operation.RETURN
                .equals(invalidTransaction.getOperation()));
    }

    @Test
    void validProcessWithData_Ok() {
        validData = Map.of(
                FRUIT_APPLE,QUANTITY_APPLE);
        validTransaction =
                new FruitTransaction(
                        FruitTransaction.Operation.RETURN, FRUIT_APPLE, QUANTITY_APPLE);
        assertTrue(
                validData.containsKey(validTransaction.getFruit()));
    }

    @Test
    void transactionIsNull_notOk() {
        assertThrows(IllegalArgumentException.class,
                () -> returnDataHandler.processWithData(null, validData));
    }

    @Test
    void mapDataIsNull_notOk() {
        validTransaction =
                new FruitTransaction(
                        FruitTransaction.Operation.RETURN, FRUIT_APPLE, QUANTITY_APPLE);
        assertThrows(IllegalArgumentException.class,
                () -> returnDataHandler.processWithData(validTransaction, null));
    }
}
