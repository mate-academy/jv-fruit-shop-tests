package core.basesyntax.strategy.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReturnHandlerTest {
    private static final int INITIAL_STORAGE_QUANTITY = 50;
    private static final int RETURN_QUANTITY = 40;
    private static final Integer EXPECTED_QUANTITY = 90;
    private static final String FRUIT = "apple";
    private static FruitTransaction fruitTransaction;
    private static OperationHandler handler;

    @BeforeClass
    public static void beforeClass() throws Exception {
        handler = new ReturnHandler();
        fruitTransaction = new FruitTransaction(FruitTransaction.Operation.RETURN,
                FRUIT, RETURN_QUANTITY);
    }

    @After
    public void tearDown() throws Exception {
        FruitStorage.fruitStorage.clear();
    }

    @Test
    public void apply_regularReturnCase_Ok() {
        FruitStorage.fruitStorage.put(FRUIT, INITIAL_STORAGE_QUANTITY);
        handler.apply(fruitTransaction);
        assertEquals(EXPECTED_QUANTITY, FruitStorage.fruitStorage.get(FRUIT));
    }
}
