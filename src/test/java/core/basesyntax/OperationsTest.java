package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.handler.BalanceOperation;
import core.basesyntax.strategy.handler.PurchaseOperation;
import core.basesyntax.strategy.handler.ReturnOperation;
import core.basesyntax.strategy.handler.SupplyOperation;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class OperationsTest {
    private static final FruitTransaction FOR_OPERATION_TRANSACTION
            = new FruitTransaction();

    private static final String DEFAULT_NAME = "apple";
    private static final int INIT_FRUIT_QUANTITY = 10;
    private static final int DEFAULT_TRANSACTION_QUANTITY = 5;

    private static final FruitTransaction.Operation BALANCE_ENUM
            = FruitTransaction.Operation.BALANCE;
    private static final FruitTransaction.Operation PURCHASE_ENUM
            = FruitTransaction.Operation.PURCHASE;
    private static final FruitTransaction.Operation RETURN_ENUM
            = FruitTransaction.Operation.RETURN;
    private static final FruitTransaction.Operation SUPPLY_ENUM
            = FruitTransaction.Operation.SUPPLY;

    private static final Map<String, Integer> STORAGE = Storage.getFruits();

    private static final OperationHandler BALANCE_OPERATION
            = new BalanceOperation();
    private static final OperationHandler PURCHASE_OPERATION
            = new PurchaseOperation();
    private static final OperationHandler RETURN_OPERATION
            = new ReturnOperation();
    private static final OperationHandler SUPPLY_OPERATION
            = new SupplyOperation();

    @BeforeEach
    void setUp() {
        STORAGE.clear();
        STORAGE.put(DEFAULT_NAME, INIT_FRUIT_QUANTITY);

        FOR_OPERATION_TRANSACTION.setQuantity(DEFAULT_TRANSACTION_QUANTITY);
        FOR_OPERATION_TRANSACTION.setName(DEFAULT_NAME);
    }

    @Test
    void balanceOperationTest_ok() {
        FOR_OPERATION_TRANSACTION.setOperation(BALANCE_ENUM);

        BALANCE_OPERATION.execute(FOR_OPERATION_TRANSACTION, STORAGE);

        assertEquals(DEFAULT_TRANSACTION_QUANTITY,
                STORAGE.get(DEFAULT_NAME));
    }

    @Test
    void purchaseOperationTest_ok() {
        FOR_OPERATION_TRANSACTION.setOperation(PURCHASE_ENUM);

        PURCHASE_OPERATION.execute(FOR_OPERATION_TRANSACTION, STORAGE);

        assertEquals(INIT_FRUIT_QUANTITY - DEFAULT_TRANSACTION_QUANTITY,
                STORAGE.get(DEFAULT_NAME));
    }

    @Test
    void returnOperationTest_ok() {
        FOR_OPERATION_TRANSACTION.setOperation(RETURN_ENUM);

        RETURN_OPERATION.execute(FOR_OPERATION_TRANSACTION, STORAGE);

        assertEquals(INIT_FRUIT_QUANTITY + DEFAULT_TRANSACTION_QUANTITY,
                STORAGE.get(DEFAULT_NAME));
    }

    @Test
    void supplyOperationTest_ok() {
        FOR_OPERATION_TRANSACTION.setOperation(SUPPLY_ENUM);

        SUPPLY_OPERATION.execute(FOR_OPERATION_TRANSACTION, STORAGE);

        assertEquals(INIT_FRUIT_QUANTITY + DEFAULT_TRANSACTION_QUANTITY,
                STORAGE.get(DEFAULT_NAME));
    }

    @Test
    void purchaseOperation_notEnoughFruit_notOk() {
        FOR_OPERATION_TRANSACTION.setOperation(PURCHASE_ENUM);
        FOR_OPERATION_TRANSACTION.setQuantity(100);

        assertThrows(RuntimeException.class,
                () -> PURCHASE_OPERATION.execute(FOR_OPERATION_TRANSACTION,
                        STORAGE));
    }

    @Test
    void returnOperation_fruitAbsent_notOk() {
        STORAGE.clear();

        FOR_OPERATION_TRANSACTION.setOperation(RETURN_ENUM);

        assertThrows(RuntimeException.class,
                () -> RETURN_OPERATION.execute(FOR_OPERATION_TRANSACTION,
                        STORAGE));
    }

}
