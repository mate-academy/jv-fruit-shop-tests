package core.basesyntax.strategy.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BalanceOperationHandlerTest {
    private static final int EXISTING_FRUIT_AMOUNT = 30;
    private static final int NEW_TRANSACTION_AMOUNT = 25;
    private static final Integer EXPECTED_AMOUNT = 25;
    private static final String FRUIT_NAME = "banana";
    private Storage storage;
    private BalanceOperationHandler balanceOperationHandler;
    private FruitTransaction fruitTransaction;

    @BeforeEach
    public void setUp() {
        this.balanceOperationHandler = new BalanceOperationHandler();
        this.fruitTransaction = new FruitTransaction();
        this.storage = new Storage();
    }

    @Test
    void operateFruits_CorrectBalanceValue_Ok() {
        storage.getFruitsStorage().put(FRUIT_NAME, EXISTING_FRUIT_AMOUNT);
        fruitTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        fruitTransaction.setFruit(FRUIT_NAME);
        fruitTransaction.setQuantity(NEW_TRANSACTION_AMOUNT);
        balanceOperationHandler.operateFruits(fruitTransaction);
        Integer actualValue = storage.getFruitsStorage().get(FRUIT_NAME);
        Assert.assertEquals(EXPECTED_AMOUNT, actualValue);
    }
}
