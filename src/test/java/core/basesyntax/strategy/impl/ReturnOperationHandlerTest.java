package core.basesyntax.strategy.impl;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReturnOperationHandlerTest {
    private ReturnOperationHandler returnOperationHandler;
    private FruitDao fruitDao;

    @BeforeEach
    public void setUp() {
        returnOperationHandler = new ReturnOperationHandler();
        fruitDao = new FruitDaoImpl();
    }

    @Test
    public void get_returnTransaction_Ok() {
        FruitTransaction transaction = new FruitTransaction();
        transaction.setOperation(FruitTransaction.Operation.RETURN);
        transaction.setFruit("banana");
        transaction.setQuantity(10);
        Storage.fruits.put("banana", 5);
        returnOperationHandler.getTransaction(transaction);
        int quantity = Storage.fruits.get("banana");
        Assertions.assertEquals(15, quantity);
    }
}
