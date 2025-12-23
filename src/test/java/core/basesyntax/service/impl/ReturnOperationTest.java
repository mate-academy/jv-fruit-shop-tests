package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import java.util.HashMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReturnOperationTest {
    private Storage storage;
    private OperationHandler returnOperation;

    @BeforeEach
    void setUp() {
        storage = new Storage(new HashMap<>());
        storage.addFruit("kiwi", 100);
        storage.addFruit("apple", 50);
        storage.addFruit("orange", 20);
        storage.addFruit("pear", 10);
        returnOperation = new ReturnOperation(storage);
    }

    @Test
    void handle_notFoundFruitInStorage_Ok() {
        assertEquals(0, storage.getFruitQuantity("banana"));
        FruitTransaction transaction = new FruitTransaction();
        transaction.setFruit("banana");
        transaction.setQuantity(1);
        returnOperation.handle(transaction);
        assertEquals(1, storage.getFruitQuantity("banana"));
    }

    @Test
    void handle_normalOperation_Ok() {
        FruitTransaction transaction = new FruitTransaction();
        transaction.setFruit("kiwi");
        transaction.setQuantity(100);
        returnOperation.handle(transaction);
        assertEquals(200, storage.getFruitQuantity("kiwi"));
    }
}
