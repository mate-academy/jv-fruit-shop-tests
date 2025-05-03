package core.basesyntax.service.operation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SupplyOperationHandlerTest {
    private static final String DEFAULT_FRUIT_NAME = "apple";
    private static final Integer INITIAL_FRUIT_QUANTITY = 40;
    private static final Integer SUPPLIED_FRUIT_QUANTITY = 100;
    private static final Operation BALANCE_OPERATION = Operation.BALANCE;
    private static final Operation SUPPLY_OPERATION = Operation.SUPPLY;
    private OperationHandler operationHandler;

    @BeforeEach
    void setUp() {
        Storage.storage.clear();
        operationHandler = new SupplyOperationHandler();
        FruitTransaction fruitTransaction = new FruitTransaction(DEFAULT_FRUIT_NAME,
                INITIAL_FRUIT_QUANTITY,
                BALANCE_OPERATION);
        OperationHandler balanceOperation = new BalanceOperationHandler();
        balanceOperation.execute(fruitTransaction);
    }

    @Test
    void execute_suppliedFruitTransaction_ok() {
        FruitTransaction fruitTransaction = new FruitTransaction(DEFAULT_FRUIT_NAME,
                SUPPLIED_FRUIT_QUANTITY,
                SUPPLY_OPERATION);
        operationHandler.execute(fruitTransaction);
        int expected = INITIAL_FRUIT_QUANTITY + SUPPLIED_FRUIT_QUANTITY;
        int actual = Storage.storage.get(DEFAULT_FRUIT_NAME);
        assertEquals(expected, actual);
    }
}
