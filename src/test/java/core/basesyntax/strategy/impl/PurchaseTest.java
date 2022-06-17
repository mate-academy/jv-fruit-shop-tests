package core.basesyntax.strategy.impl;

import static core.basesyntax.model.FruitTransaction.Operation;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import core.basesyntax.db.FruitsStorage;
import core.basesyntax.model.FruitTransaction;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class PurchaseTest {
    private static final FruitTransaction CORRECT_TRANSACTION
            = new FruitTransaction(Operation.PURCHASE, "banana", 20);
    private static final FruitTransaction INCORRECT_MORE_THAN_PRESENT
            = new FruitTransaction(Operation.PURCHASE, "banana", 150);
    private static final FruitTransaction INCORRECT_ENOTHER_OPER
            = new FruitTransaction(Operation.RETURN, "banana", 20);
    private static Purchase purchase;

    @BeforeClass
    public static void initBalanceHandler() {
        purchase = new Purchase();
        FruitsStorage.fruitsStorage.clear();
    }

    @Before
    public void fillStorage() {
        FruitsStorage.fruitsStorage.put("banana", 100);
    }

    @Test
    public void makeOperation_validData_ok() {
        Integer quantityAfterTransaction = 80;
        assertTrue(purchase.makeOperation(CORRECT_TRANSACTION));
        assertEquals(FruitsStorage.fruitsStorage.get("banana"), quantityAfterTransaction);
    }

    @Test (expected = RuntimeException.class)
    public void makeOperation_purchaseMoreThanInStorage_notOk() {
        purchase.makeOperation(INCORRECT_MORE_THAN_PRESENT);
    }

    @Test (expected = RuntimeException.class)
    public void makeOperation_incorrectTransaction_notOk() {
        purchase.makeOperation(INCORRECT_ENOTHER_OPER);
    }

    @After
    public void clearStorage() {
        FruitsStorage.fruitsStorage.clear();
    }
}
