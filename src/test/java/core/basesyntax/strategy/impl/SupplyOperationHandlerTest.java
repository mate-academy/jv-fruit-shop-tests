package core.basesyntax.strategy.impl;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SupplyOperationHandlerTest {
    private SupplyOperationHandler supplyOperationHandler;

    @BeforeEach
    public void setUp() {
        supplyOperationHandler = new SupplyOperationHandler();
        FruitDao fruitDao = new FruitDaoImpl();
    }

    @Test
    public void get_supplyTransaction_Ok() {
        FruitTransaction transaction = new FruitTransaction();
        transaction.setOperation(FruitTransaction.Operation.SUPPLY);
        transaction.setFruit("apple");
        transaction.setQuantity(20);
        Storage.fruits.put("apple", 10);
        supplyOperationHandler.getTransaction(transaction);
        int quantity = Storage.fruits.get("apple");
        Assertions.assertEquals(30, quantity);
    }
}
