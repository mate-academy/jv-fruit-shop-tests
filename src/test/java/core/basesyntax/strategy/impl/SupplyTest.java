package core.basesyntax.strategy.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import core.basesyntax.db.FruitsStorage;
import core.basesyntax.model.FruitTransaction;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class SupplyTest {
    private static final FruitTransaction CORRECT_TRANSACTION
            = new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 20);
    private static final FruitTransaction CORRECT_FRUIT_THAT_NOT_EXIST
            = new FruitTransaction(FruitTransaction.Operation.SUPPLY, "pineapple", 150);
    private static final FruitTransaction INCORRECT_ENOTHER_OPER
            = new FruitTransaction(FruitTransaction.Operation.RETURN, "banana", 20);
    private static final String NEW_FRUIT = "pineapple";
    private static Supply supply;

    @BeforeClass
    public static void initBalanceHandler() {
        supply = new Supply();
        FruitsStorage.fruitsStorage.clear();
    }

    @Before
    public void fillStorage() {
        FruitsStorage.fruitsStorage.put("banana", 100);
    }

    @Test
    public void makeOperation_validData_ok() {
        Integer quantityAfterTransaction = 120;
        assertTrue(supply.makeOperation(CORRECT_TRANSACTION));
        assertEquals(FruitsStorage.fruitsStorage.get("banana"), quantityAfterTransaction);
    }

    @Test
    public void makeOperation_newFruit_ok() {
        Integer quantityAfterTransaction = 150;
        assertTrue(supply.makeOperation(CORRECT_FRUIT_THAT_NOT_EXIST));
        assertEquals(FruitsStorage.fruitsStorage.get(NEW_FRUIT), quantityAfterTransaction);
    }

    @Test (expected = RuntimeException.class)
    public void makeOperation_incorrectTransaction_notOk() {
        supply.makeOperation(INCORRECT_ENOTHER_OPER);
    }

    @After
    public void clearStorage() {
        FruitsStorage.fruitsStorage.clear();
    }
}
