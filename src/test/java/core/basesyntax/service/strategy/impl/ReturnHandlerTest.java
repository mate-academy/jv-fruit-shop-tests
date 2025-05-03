package core.basesyntax.service.strategy.impl;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.impl.StorageDaoImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.strategy.OperationHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReturnHandlerTest {

    private static OperationHandler handler;
    private static StorageDao storageDao;

    @BeforeAll
    static void beforeAll() {
        storageDao = new StorageDaoImpl();
        handler = new PurchaseHandler(storageDao);
    }

    @BeforeEach
    void setUp() {
    }

    @Test
    void processTransaction() {
        storageDao.putToMap("apple", 50);
        FruitTransaction fruit = new FruitTransaction(Operation.BALANCE, "apple", 50);
        int quantityBefore = storageDao.getAmount("apple");
        int quantityActual = handler.processTransaction(fruit);
        Assertions.assertNotEquals(quantityActual, quantityBefore);
    }
}
