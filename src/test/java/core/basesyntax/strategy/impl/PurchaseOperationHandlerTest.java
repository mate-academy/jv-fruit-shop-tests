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
    public void check_successPurchaseAndUpdate_Ok() {
        String fruitName = "apple";
        int initialQuantity = 15;
        fruitDao.putToDb(fruitName, initialQuantity);
        FruitTransaction transaction = new FruitTransaction();
        transaction.setOperation(FruitTransaction.Operation.PURCHASE);
        transaction.setFruit(fruitName);
        transaction.setQuantity(10);
        purchaseOperationHandler.getTransaction(transaction);
        int newQuantity = fruitDao.getQuantity(fruitName);
        Assertions.assertEquals(newQuantity,
                initialQuantity - transaction.getQuantity());
    }

    @Test
    public void get_purchaseWithInsufficientQuantity_NotOk() {
        String fruitName = "banana";
        int initialQuantity = 5;
        fruitDao.putToDb(fruitName, initialQuantity);
        FruitTransaction transaction = new FruitTransaction();
        transaction.setOperation(FruitTransaction.Operation.PURCHASE);
        transaction.setFruit(fruitName);
        transaction.setQuantity(10);
        Assertions.assertThrows(IllegalStateException.class,
                () -> purchaseOperationHandler.getTransaction(transaction));
        int newQuantity = fruitDao.getQuantity(fruitName);
        Assertions.assertEquals(newQuantity, initialQuantity);
    }
}
