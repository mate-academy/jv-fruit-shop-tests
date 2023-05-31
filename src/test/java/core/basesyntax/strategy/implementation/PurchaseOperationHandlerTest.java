package core.basesyntax.strategy.implementation;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.NoSuchProductException;
import core.basesyntax.exceptions.ProductAmountException;
import core.basesyntax.strategy.interfaces.OperationHandler;
import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class PurchaseOperationHandlerTest {
    private static final OperationHandler handler
            = new PurchaseOperationHandler(new StorageDaoImpl());
    private static final String TEST_PRODUCT = "banana";

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void handle_correctAmount_ok() {
        Storage.fruitsStorage.put(TEST_PRODUCT, 100);
        handler.handle(TEST_PRODUCT, 10);
        int expected = 90;
        int actual = Storage.fruitsStorage.get(TEST_PRODUCT);
        assertEquals(expected, actual);
    }

    @Test
    public void handle_incorrectAmount_notOk() {
        Storage.fruitsStorage.put(TEST_PRODUCT, 100);
        exceptionRule.expect(ProductAmountException.class);
        exceptionRule.expectMessage(OperationHandler
                .AMOUNT_LESS_THEN_ZERO_EXCEPTION_MESSAGE + TEST_PRODUCT);
        handler.handle(TEST_PRODUCT, -10);
    }

    @Test
    public void handle_storageNotContainProduct_notOk() {
        exceptionRule.expect(NoSuchProductException.class);
        exceptionRule.expectMessage(TEST_PRODUCT + OperationHandler.NOT_EXISTS_EXCEPTION_MESSAGE);
        handler.handle(TEST_PRODUCT, 10);
    }

    @Test
    public void handle_amountLessThenRequired_notOk() {
        int amount = 10;
        Storage.fruitsStorage.put(TEST_PRODUCT, amount);
        exceptionRule.expect(ProductAmountException.class);
        exceptionRule.expectMessage(OperationHandler
                .AMOUNT_TO_MINUS_INCORRECT_EXCEPTION_MESSAGE + amount);
        handler.handle(TEST_PRODUCT, 100);
    }

    @After
    public void after() {
        Storage.fruitsStorage.clear();
    }
}
