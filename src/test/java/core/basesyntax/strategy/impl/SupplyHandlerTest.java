package core.basesyntax.strategy.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class SupplyHandlerTest {
    private static final int INITIAL_STORAGE_QUANTITY = 50;
    private static final int SUPPLY_QUANTITY = 40;
    private static final Integer EXPECTED_QUANTITY = 90;
    private static final String FRUIT = "apple";
    private static FruitTransaction fruitTransaction;
    private static OperationHandler handler;

    @BeforeClass
    public static void beforeClass() throws Exception {
        fruitTransaction = new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                FRUIT, SUPPLY_QUANTITY);
        handler = new SupplyHandler();
    }

    @After
    public void tearDown() throws Exception {
        FruitStorage.fruitStorage.clear();
    }

    @Test
    public void apply_regularSupplyCase_Ok() {
        FruitStorage.fruitStorage.put(FRUIT, INITIAL_STORAGE_QUANTITY);
        handler.apply(fruitTransaction);
        assertEquals(EXPECTED_QUANTITY, FruitStorage.fruitStorage.get(FRUIT));
    }
}
