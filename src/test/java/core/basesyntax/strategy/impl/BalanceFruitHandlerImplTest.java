package core.basesyntax.strategy.impl;

import core.basesyntax.enumeration.Operation;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.FruitHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BalanceFruitHandlerImplTest {
    private static final int QUANTITY_OF_FRUIT = 50;
    private static final String NAME_OF_FRUIT = "apple";
    private FruitTransaction fruitTransaction;
    private FruitHandler fruitHandler;

    @BeforeEach
    void setUp() {
        fruitTransaction = new FruitTransaction(Operation.BALANCE, NAME_OF_FRUIT,QUANTITY_OF_FRUIT);
    }

    @Test
    void doAction() {
    }
}