package core.basesyntax.service.transaction.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitShopOperation;
import core.basesyntax.model.FruitTransaction;
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

    }

    @Test
    void executeReturnTransaction_OK() {
        Storage.addPair("apple", 60);
        Storage.addPair("banana", 20);
        FruitTransaction transaction = new FruitTransaction(FruitShopOperation.RETURN, "apple", 60);
        Map<String, Integer> expected = new HashMap<>(
                Map.of("apple", 120,"banana", 20));
        returnTransactionHandler.executeTransaction(transaction);
        Assertions.assertEquals(expected, Storage.getAll(),
                "Return operation doesn't work correctly: ");
        Storage.clear();
    }
}
