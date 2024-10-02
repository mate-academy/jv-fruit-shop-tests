package core.basesyntax.handler.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BalanceOperationTest {
    private static final String FRUIT = "banana";
    private static final int QUANTITY = 100;
    private BalanceOperation balanceOperation;

    @BeforeEach
    public void setUp() {
        balanceOperation = new BalanceOperation;
    }

    @Test
    public void apply_validTransaction_ok() {
        FruitTransaction transaction = new FruitTransaction();
        transaction.setFruit(FRUIT);
        transaction.setQuantity(QUANTITY);
        balanceOperation.apply(transaction);
    }
}
