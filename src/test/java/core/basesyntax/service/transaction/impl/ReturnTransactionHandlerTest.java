package core.basesyntax.service.transaction.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.transaction.TransactionHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReturnTransactionHandlerTest {
    private TransactionHandler returnTransactionHandler;

    @BeforeEach
    void setUp() {
        returnTransactionHandler = new ReturnTransactionHandler();
        Storage.addPair(Fruit.PINEAPPLE, 60);
        Storage.addPair(Fruit.STRAWBERRY, 20);
    }

    @Test
    void executeReturnTransaction_OK() {
        FruitTransaction transaction = new FruitTransaction(Operation.RETURN, Fruit.PINEAPPLE, 60);
        Map<Fruit, Integer> expected = new HashMap<>(
                Map.of(Fruit.PINEAPPLE, 120,Fruit.STRAWBERRY, 20));
        returnTransactionHandler.executeTransaction(transaction);
        Assertions.assertEquals(expected, Storage.getAll(),
                "Return operation doesn't work correctly: ");
        Storage.clear();
    }
}
