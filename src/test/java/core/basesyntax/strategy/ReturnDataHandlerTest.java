package core.basesyntax.strategy;

import core.basesyntax.data.FruitTransaction;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ReturnDataHandlerTest {
    private static final int QUANTITY_APPLE = 90;
    private static final String FRUIT_APPLE = "apple";
    private ReturnDataHandler returnDataHandler = new ReturnDataHandler();
    private FruitTransaction validTransaction =
            new FruitTransaction(FruitTransaction.Operation.RETURN, FRUIT_APPLE, QUANTITY_APPLE);
    private FruitTransaction invalidTransaction =
            new FruitTransaction(FruitTransaction.Operation.PURCHASE, FRUIT_APPLE, QUANTITY_APPLE);
    private Map<String, Integer> validData = Map.of(
            FRUIT_APPLE,QUANTITY_APPLE
    );

    @Test
    void validReturnOperationInTransaction_Ok() {
        Assertions.assertTrue(validTransaction.getOperation()
                .equals(FruitTransaction.Operation.RETURN));
    }

    @Test
    void invalidReturnOperationInTransaction_notOk() {
        Assertions.assertFalse(FruitTransaction.Operation.RETURN
                .equals(invalidTransaction.getOperation()));
    }

    @Test
    void validProcessWithData_Ok() {
        Assertions.assertTrue(
                validData.containsKey(validTransaction.getFruit()));
    }

    @Test
    void transactionIsNull_notOk() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> returnDataHandler.processWithData(null, validData));
    }

    @Test
    void mapDataIsNull_notOk() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> returnDataHandler.processWithData(validTransaction, null));
    }
}
