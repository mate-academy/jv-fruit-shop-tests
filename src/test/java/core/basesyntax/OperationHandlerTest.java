package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.impl.BalanceOperationStrategy;
import core.basesyntax.strategy.impl.PurchaseOperationStrategy;
import core.basesyntax.strategy.impl.ReturnOperationStrategy;
import core.basesyntax.strategy.impl.SupplyOperationStrategy;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class OperationHandlerTest {
    private static final String VALID_NAME = "apple";
    private FruitDao fruitDao = new FruitDaoImpl();
    private OperationHandler balanceOperation = new BalanceOperationStrategy(fruitDao);
    private OperationHandler purchaseOperation = new PurchaseOperationStrategy(fruitDao);
    private OperationHandler supplyOperation = new SupplyOperationStrategy(fruitDao);
    private OperationHandler returnOperation = new ReturnOperationStrategy(fruitDao);

    @Test
    void execute_balanceOperation_success() {
        balanceOperation.execute(VALID_NAME, 20);
        assertEquals(20, fruitDao.get(VALID_NAME).getQuantity());
    }

    @Test
    void execute_purchaseOperation_success() {
        fruitDao.add(new Fruit(VALID_NAME, 20));
        purchaseOperation.execute(VALID_NAME, 10);
        assertEquals(10, fruitDao.get(VALID_NAME).getQuantity());
    }

    @Test
    void execute_purchaseOperation_notEnoughFruit_throwsException() {
        String expectedMessage = "You cannot sell more than you have in the store";
        fruitDao.add(new Fruit(VALID_NAME, 5));
        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                purchaseOperation.execute(VALID_NAME, 10));
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void execute_supplyOperation_success() {
        fruitDao.add(new Fruit(VALID_NAME, 20));
        supplyOperation.execute(VALID_NAME, 10);
        assertEquals(30, fruitDao.get(VALID_NAME).getQuantity());
    }

    @Test
    void execute_supplyOperation_negativeQuantity_throwsException() {
        String expectedMessage = "Supply value can`t be negative";
        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                supplyOperation.execute(VALID_NAME, -10));
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void execute_returnOperation_success() {
        fruitDao.add(new Fruit(VALID_NAME, 20));
        returnOperation.execute(VALID_NAME, 10);
        assertEquals(30, fruitDao.get(VALID_NAME).getQuantity());
    }

    @AfterEach
    void clearStorage() {
        Storage.getInstance().getStorage().clear();
    }
}
