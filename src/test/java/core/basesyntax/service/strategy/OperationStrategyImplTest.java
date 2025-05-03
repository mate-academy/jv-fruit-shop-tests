package core.basesyntax.service.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.TransactionSupplier;
import core.basesyntax.dao.FruitStorageDao;
import core.basesyntax.dao.FruitStorageDaoImpl;
import core.basesyntax.exception.NotEnoughProductsException;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import java.util.Map;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OperationStrategyImplTest {
    private static final String FRUIT_ORANGE = "orange";
    private static final String FRUIT_APPLE = "apple";
    private static final int APPLE_QUANTITY = 15;
    private static final int NEW_APPLE_QUANTITY = 10;
    private static final int RETURN_APPLE = 2;
    private static final int SUPPLY_APPLE = 30;
    private static final int PURCHASE_APPLE = 10;
    private static final int OVER_LIMIT_QUANTITY = 30;
    private FruitStorageDao storageDao;
    private Map<Operation, OperationHandler> operationHandlers;
    private OperationStrategy operationStrategy;

    @BeforeEach
    void setUp() {
        storageDao = new FruitStorageDaoImpl();
        operationHandlers = Map.of(
                Operation.PURCHASE, new PurchaseOperation(storageDao),
                Operation.BALANCE, new BalanceOperation(storageDao),
                Operation.RETURN, new ReturnOperation(storageDao),
                Operation.SUPPLY, new SupplyOperation(storageDao)
        );
        operationStrategy = new OperationStrategyImpl(operationHandlers);
        storageDao.add(FRUIT_APPLE, APPLE_QUANTITY);
    }

    @Test
    void operationBalance_ok() {
        FruitTransaction transactionBalance =
                TransactionSupplier.of(Operation.BALANCE, FRUIT_APPLE, NEW_APPLE_QUANTITY);

        int beforeOperation =
                storageDao.getFruitQuantity(FRUIT_APPLE);

        OperationHandler operationBalance =
                operationHandlers.get(transactionBalance.getOperation());
        operationBalance.handle(transactionBalance);

        int afterOperation = storageDao.getFruitQuantity(FRUIT_APPLE);

        assertTrue(beforeOperation > afterOperation);
        assertNotEquals(beforeOperation, afterOperation);
    }

    @Test
    void operationReturn_ok() {
        FruitTransaction transactionReturn =
                TransactionSupplier.of(Operation.RETURN, FRUIT_APPLE, RETURN_APPLE);

        OperationHandler operationReturn =
                operationStrategy.getOperation(transactionReturn.getOperation());

        int expectedQuantity = APPLE_QUANTITY + RETURN_APPLE;
        operationReturn.handle(transactionReturn);

        int actualQuantity = storageDao.getFruitQuantity(FRUIT_APPLE);
        assertEquals(expectedQuantity, actualQuantity);
    }

    @Test
    void operationSupply_ok() {
        FruitTransaction transactionSupply =
                TransactionSupplier.of(Operation.SUPPLY, FRUIT_APPLE, SUPPLY_APPLE);
        OperationHandler operationSupply =
                operationStrategy.getOperation(transactionSupply.getOperation());

        int expectedQuantity = SUPPLY_APPLE + APPLE_QUANTITY;
        operationSupply.handle(transactionSupply);

        int actualQuantity = storageDao.getFruitQuantity(FRUIT_APPLE);
        assertEquals(expectedQuantity, actualQuantity);
    }

    @Test
    void operationPurchase_ok() {
        FruitTransaction transactionPurchase =
                TransactionSupplier.of(Operation.PURCHASE, FRUIT_APPLE, PURCHASE_APPLE);

        OperationHandler operationPurchase =
                operationStrategy.getOperation(transactionPurchase.getOperation());

        int expected = APPLE_QUANTITY - PURCHASE_APPLE;
        operationPurchase.handle(transactionPurchase);

        int actual = storageDao.getFruitQuantity(FRUIT_APPLE);
        assertEquals(expected, actual);
    }

    @Test
    void operationPurchaseWithOverLimitNumber_notOk() {
        FruitTransaction transactionPurchase =
                TransactionSupplier.of(Operation.PURCHASE, FRUIT_APPLE, OVER_LIMIT_QUANTITY);

        OperationHandler operationPurchase =
                operationStrategy.getOperation(transactionPurchase.getOperation());

        assertThrows(NotEnoughProductsException.class, () ->
                operationPurchase.handle(transactionPurchase));
    }

    @Test
    void operationPurchaseWithNonExistElement_notOk() {
        FruitTransaction transactionPurchase =
                TransactionSupplier.of(Operation.PURCHASE, FRUIT_ORANGE, OVER_LIMIT_QUANTITY);

        OperationHandler operationPurchase =
                operationStrategy.getOperation(transactionPurchase.getOperation());

        assertThrows(NoSuchElementException.class, () ->
                operationPurchase.handle(transactionPurchase));
    }

    @AfterEach
    void tearDown() {
        storageDao.getAllFruits().clear();
    }
}
