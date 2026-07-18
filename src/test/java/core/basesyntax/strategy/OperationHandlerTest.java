package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.impl.BalanceOperationHandler;
import core.basesyntax.strategy.impl.PurchaseOperationHandler;
import core.basesyntax.strategy.impl.ReturnOperationHandler;
import core.basesyntax.strategy.impl.SupplyOperationHandler;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OperationHandlerTest {
    private static final FruitTransaction FOR_OPERATION_TRANSACTION
            = new FruitTransaction();

    private static final String DEFAULT_NAME = "banana";
    private static final int INIT_FRUIT_QUANTITY = 100;
    private static final int DEFAULT_TRANSACTION_QUANTITY = 5;

    private static final FruitTransaction.Operation BALANCE_ENUM
            = FruitTransaction.Operation.BALANCE;
    private static final FruitTransaction.Operation PURCHASE_ENUM
            = FruitTransaction.Operation.PURCHASE;
    private static final FruitTransaction.Operation RETURN_ENUM
            = FruitTransaction.Operation.RETURN;
    private static final FruitTransaction.Operation SUPPLY_ENUM
            = FruitTransaction.Operation.SUPPLY;

    private static final Map<String, Integer> STORAGE = Storage.fruits;

    private static final OperationHandler BALANCE_OPERATION
            = new BalanceOperationHandler();
    private static final OperationHandler PURCHASE_OPERATION
            = new PurchaseOperationHandler();
    private static final OperationHandler RETURN_OPERATION
            = new ReturnOperationHandler();
    private static final OperationHandler SUPPLY_OPERATION
            = new SupplyOperationHandler();

    @BeforeEach
    void setUp() {
        STORAGE.clear();
        STORAGE.put(DEFAULT_NAME, INIT_FRUIT_QUANTITY);

        FOR_OPERATION_TRANSACTION.setQuantity(DEFAULT_TRANSACTION_QUANTITY);
        FOR_OPERATION_TRANSACTION.setFruit(DEFAULT_NAME);
    }

    @AfterEach
    void tearDown() {
        Storage.fruits.clear();
    }

    @Test
    void balanceOperationTest_ok() {
        FOR_OPERATION_TRANSACTION.setOperation(BALANCE_ENUM);

        BALANCE_OPERATION.handle(FOR_OPERATION_TRANSACTION);

        assertEquals(DEFAULT_TRANSACTION_QUANTITY, STORAGE.get(DEFAULT_NAME).intValue());
    }

    @Test
    void purchaseOperationTest_ok() {
        FOR_OPERATION_TRANSACTION.setOperation(PURCHASE_ENUM);

        PURCHASE_OPERATION.handle(FOR_OPERATION_TRANSACTION);

        assertEquals(INIT_FRUIT_QUANTITY - DEFAULT_TRANSACTION_QUANTITY,
                STORAGE.get(DEFAULT_NAME).intValue());
    }

    @Test
    void returnOperationTest_ok() {
        FOR_OPERATION_TRANSACTION.setOperation(RETURN_ENUM);

        RETURN_OPERATION.handle(FOR_OPERATION_TRANSACTION);

        assertEquals(INIT_FRUIT_QUANTITY + DEFAULT_TRANSACTION_QUANTITY,
                STORAGE.get(DEFAULT_NAME).intValue());
    }

    @Test
    void supplyOperationTest_ok() {
        FOR_OPERATION_TRANSACTION.setOperation(SUPPLY_ENUM);

        SUPPLY_OPERATION.handle(FOR_OPERATION_TRANSACTION);

        assertEquals(INIT_FRUIT_QUANTITY + DEFAULT_TRANSACTION_QUANTITY,
                STORAGE.get(DEFAULT_NAME).intValue());
    }

    @Test
    void purchaseOperation_notEnoughFruit_notOk() {
        FOR_OPERATION_TRANSACTION.setOperation(PURCHASE_ENUM);
        FOR_OPERATION_TRANSACTION.setQuantity(100);

        PURCHASE_OPERATION.handle(FOR_OPERATION_TRANSACTION);
        assertThrows(RuntimeException.class,
                () -> PURCHASE_OPERATION.handle(FOR_OPERATION_TRANSACTION));
    }

}
