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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OperationStrategyTest {
    private FruitDao fruitDao;
    private OperationHandler balanceOperation;
    private OperationHandler purchaseOperation;
    private OperationHandler supplyOperation;
    private OperationHandler returnOperation;

    @BeforeEach
    void setUp() {
        fruitDao = new FruitDaoImpl();
        balanceOperation = new BalanceOperationStrategy(fruitDao);
        purchaseOperation = new PurchaseOperationStrategy(fruitDao);
        supplyOperation = new SupplyOperationStrategy(fruitDao);
        returnOperation = new ReturnOperationStrategy(fruitDao);
        Storage.getInstance().getStorage().clear(); // Clear the storage before each test
    }

    @Test
    void execute_balanceOperation_success() {
        balanceOperation.execute("apple", 20);
        assertEquals(20, fruitDao.get("apple").getQuantity());
    }

    @Test
    void execute_purchaseOperation_success() {
        fruitDao.add(new Fruit("apple", 20));
        purchaseOperation.execute("apple", 10);
        assertEquals(10, fruitDao.get("apple").getQuantity());
    }

    @Test
    void execute_purchaseOperation_notEnoughFruit_throwsException() {
        fruitDao.add(new Fruit("apple", 5));
        assertThrows(RuntimeException.class, () -> purchaseOperation.execute("apple", 10));
    }

    @Test
    void execute_supplyOperation_success() {
        fruitDao.add(new Fruit("apple", 20));
        supplyOperation.execute("apple", 10);
        assertEquals(30, fruitDao.get("apple").getQuantity());
    }

    @Test
    void execute_supplyOperation_negativeQuantity_throwsException() {
        assertThrows(RuntimeException.class, () -> supplyOperation.execute("apple", -10));
    }

    @Test
    void execute_returnOperation_success() {
        fruitDao.add(new Fruit("apple", 20));
        returnOperation.execute("apple", 10);
        assertEquals(30, fruitDao.get("apple").getQuantity());
    }
}
