package core.basesyntax.strategy.implementation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.ProductAmountException;
import core.basesyntax.strategy.interfaces.OperationHandler;
import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class BalanceOperationHandlerTest {
    private static final OperationHandler handler
            = new BalanceOperationHandler(new StorageDaoImpl());
    private static final String TEST_PRODUCT = "banana";

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void handle_correctAmount_ok() {
        int amountExpected = 100;
        handler.handle(TEST_PRODUCT, amountExpected);
        assertTrue(Storage.fruitsStorage.containsKey(TEST_PRODUCT));
        assertEquals(amountExpected, (int)Storage.fruitsStorage.get(TEST_PRODUCT));
    }

    @Test
    public void handle_incorrectAmount_notOk() {
        int amount = -100;
        exceptionRule.expect(ProductAmountException.class);
        exceptionRule.expectMessage(OperationHandler.AMOUNT_LESS_THEN_ZERO_EXCEPTION_MESSAGE
                + TEST_PRODUCT);
        handler.handle(TEST_PRODUCT, amount);
    }

    @Test
    public void handle_nullProductString_notOk() {
        exceptionRule.expect(NullPointerException.class);
        exceptionRule.expectMessage("Product can't be null");
        handler.handle(null, 100);
    }

    @After
    public void after() {
        Storage.fruitsStorage.clear();
    }
}
