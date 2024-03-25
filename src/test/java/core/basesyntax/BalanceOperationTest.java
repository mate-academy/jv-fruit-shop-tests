package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.NotEnoughFruitsException;
import core.basesyntax.exceptions.NullDataException;
import core.basesyntax.models.FruitTransaction;
import core.basesyntax.service.OperationHandler;
import core.basesyntax.service.strategy.operations.BalanceStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BalanceOperationTest {
    private static final String FRUIT_NAME = "apple";
    private static final int QUANTITY = 10;
    private static final int NEGATIVE_QUANTITY = -10;
    private FruitTransaction fruitTransaction;
    private OperationHandler operationHandler;

    @BeforeEach
    void init() {
        Storage.fruitStorage.clear();

        fruitTransaction = new FruitTransaction();
        fruitTransaction.setFruit(FRUIT_NAME);
        fruitTransaction.setOperation(null);
        fruitTransaction.setQuantity(QUANTITY);
    }

    @Test
    void handleBalance_updateDB_Ok() {
        operationHandler = new BalanceStrategy();
        int result = operationHandler.handle(fruitTransaction);
        assertEquals(Storage.fruitStorage.get(fruitTransaction.getFruit()), result,
                "Added fruit count, and result in DB must be the same");
    }

    @Test
    void handleBalance_nullData_notOk() {
        operationHandler = new BalanceStrategy();
        assertThrows(NullDataException.class,
                () -> operationHandler.handle(null),
                "Fruit transaction can't be null");
    }

    @Test
    void handleBalance_negativeData_notOk() {
        operationHandler = new BalanceStrategy();
        fruitTransaction.setQuantity(NEGATIVE_QUANTITY);

        assertThrows(NotEnoughFruitsException.class,
                () -> operationHandler.handle(fruitTransaction),
                "Can't process negative amount of fruits");
    }
}
