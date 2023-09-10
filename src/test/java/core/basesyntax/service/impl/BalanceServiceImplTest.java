package core.basesyntax.service.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BalanceServiceImplTest {
    private Storage storage;
    private BalanceServiceImpl balanceService;

    @BeforeEach
    public void init() {
        storage = new Storage();
        balanceService = new BalanceServiceImpl(storage);
    }

    @Test
    public void performOperation_addFruitTransactionToStorage_OK() {
        String operationCode = "b";
        String fruit = "Banana";
        int quantity = 76;
        FruitTransaction transaction =
                new FruitTransaction(operationCode, fruit, quantity);
        balanceService.performOperation(transaction);
        Assertions.assertTrue(storage.getData().containsKey(fruit));
        Assertions.assertEquals(quantity, storage.getData().get(fruit));

        fruit = "Pineapple";
        quantity = 123;
        transaction = new FruitTransaction(operationCode, fruit, quantity);
        balanceService.performOperation(transaction);
        Assertions.assertTrue(storage.getData().containsKey(fruit));
        Assertions.assertEquals(quantity, storage.getData().get(fruit));
    }
}
