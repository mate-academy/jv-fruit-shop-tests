package core.basesyntax.strategy.operation;

import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.NullDataException;
import core.basesyntax.exceptions.TransactionQuantityException;
import core.basesyntax.model.FruitTransaction;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

public class BalanceOperationHandlerTest {
    private final OperationHandler balanceOperationHandler;
    private final FruitTransaction valid;
    private final FruitTransaction negative;

    public BalanceOperationHandlerTest() {
        balanceOperationHandler = new BalanceOperationHandler();
        valid = new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 10);
        negative = new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", -1);
    }

    @After
    public void after() {
        Storage.storage.clear();
    }

    @Test
    public void accept_validBalanceTransaction_Ok() {
        balanceOperationHandler.evaluateTransaction(valid);
        Assert.assertEquals(Integer.valueOf(10), Storage.storage.get("apple"));
    }

    @Test (expected = NullDataException.class)
    public void accept_null_NotOk() {
        balanceOperationHandler.evaluateTransaction(null);
    }

    @Test (expected = TransactionQuantityException.class)
    public void accept_negativeBalanceTransaction_NotOk() {
        balanceOperationHandler.evaluateTransaction(negative);
    }
}
