package core.basesyntax.strategy.operation;

import core.basesyntax.dao.FruitsDao;
import core.basesyntax.dao.FruitsDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PurchaseOperationTest {
    private FruitsDao fruitsDao;
    private OperationHandler purchaseOperation;

    @BeforeEach
    public void setUp() throws Exception {
        fruitsDao = new FruitsDaoImpl();
        fruitsDao.add(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 400));
        fruitsDao.add(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 100));
        purchaseOperation = new PurchaseOperation(fruitsDao);
    }

    @Test
    public void purchaseTransaction_correctValue_ok() {
        purchaseOperation.transaction(
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 20));
        FruitTransaction fruitTransaction = fruitsDao.get(new FruitTransaction("banana"));
        Integer expected = 380;
        Assertions.assertEquals(expected, fruitTransaction.getQuantity());
    }

    @Test
    public void purchaseTransaction_incorrectValue_notOk() {
        Assertions.assertThrows(RuntimeException.class,
                () -> purchaseOperation.transaction(
                        new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 500)),
                "Quantity negative value");
    }

    @AfterEach
    public void afterClass() {
        Storage.fruitsStorage.clear();
    }
}
