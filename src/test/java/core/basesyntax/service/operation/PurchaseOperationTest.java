package core.basesyntax.service.operation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.dao.FruitTransactionDao;
import core.basesyntax.dao.impl.FruitTransactionDaoIml;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseOperationTest {
    private FruitTransactionDao fruitTransactionDao;
    private PurchaseOperation purchaseOperation;

    @BeforeEach
    void setUp() {
        fruitTransactionDao = new FruitTransactionDaoIml();
        purchaseOperation = new PurchaseOperation(fruitTransactionDao);
    }

    @Test
    void operation_ok() {
        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setFruit("apple");
        fruitTransaction.setQuantity(100);
        fruitTransactionDao.add(fruitTransaction);
        FruitTransaction purchaseTransaction = new FruitTransaction();
        purchaseTransaction.setFruit("apple");
        purchaseTransaction.setQuantity(50);
        purchaseOperation.operation(purchaseTransaction);
        int actualQuality = 50;
        int extendQuality = fruitTransactionDao.get("apple").getQuantity();
        assertEquals(actualQuality, extendQuality);
    }
}
