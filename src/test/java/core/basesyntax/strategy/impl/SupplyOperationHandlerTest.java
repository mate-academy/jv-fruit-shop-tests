package core.basesyntax.strategy.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SupplyOperationHandlerTest {
    private static final int EXISTING_FRUIT_AMOUNT = 30;
    private static final int NEW_TRANSACTION_AMOUNT = 25;
    private static final Integer EXPECTED_AMOUNT = 55;
    private static final String FRUIT_NAME = "banana";
    private Storage storage;
    private SupplyOperationHandler supplyOperationHandler;
    private FruitTransaction fruitTransaction;

    @BeforeEach
    public void setUp() {
        this.supplyOperationHandler = new SupplyOperationHandler();
        this.fruitTransaction = new FruitTransaction();
        this.storage = new Storage();
    }

    @Test
    void operateFruits_CorrectSupplyValue_Ok() {
        storage.getFruitsStorage().put(FRUIT_NAME, EXISTING_FRUIT_AMOUNT);
        fruitTransaction.setOperation(FruitTransaction.Operation.SUPPLY);
        fruitTransaction.setFruit(FRUIT_NAME);
        fruitTransaction.setQuantity(NEW_TRANSACTION_AMOUNT);
        supplyOperationHandler.operateFruits(fruitTransaction);
        Integer actualValue = storage.getFruitsStorage().get(FRUIT_NAME);
        Assert.assertEquals(EXPECTED_AMOUNT, actualValue);
    }
}
