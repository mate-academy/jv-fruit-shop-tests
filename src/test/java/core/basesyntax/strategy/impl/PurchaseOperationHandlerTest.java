package core.basesyntax.strategy.impl;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseOperationHandlerTest {
    private PurchaseOperationHandler purchaseOperationHandler;
    private FruitDao fruitDao;

    @BeforeEach
    public void setUp() {
        fruitDao = new FruitDaoImpl();
        purchaseOperationHandler = new PurchaseOperationHandler();
    }

    @Test
    public void get_validPurchase_Ok() {
        FruitTransaction transaction = new FruitTransaction();
        transaction.setOperation(FruitTransaction.Operation.PURCHASE);
        transaction.setFruit("apple");
        transaction.setQuantity(10);
        fruitDao.putToDb("apple", 15);
        purchaseOperationHandler.getTransaction(transaction);
        int newQuantity = fruitDao.getQuantity("apple");
        Assertions.assertEquals(newQuantity, 5);
    }

    @Test
    public void get_invalidPurchase_NotOk() {
        FruitTransaction transaction = new FruitTransaction();
        transaction.setOperation(FruitTransaction.Operation.PURCHASE);
        transaction.setFruit("banana");
        transaction.setQuantity(10);
        fruitDao.putToDb("banana", 5);
        Assertions.assertThrows(IllegalStateException.class,
                () -> purchaseOperationHandler.getTransaction(transaction));
    }
}
