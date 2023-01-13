package core.basesyntax.service.amount;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import org.junit.Assert;
import org.junit.Test;

public class BalanceHandlerTest {
    private static final FruitTransaction bananaBalance;
    private final OperationHandler balance = new BalanceHandler();

    static {
        bananaBalance = new FruitTransaction.FruitTransactionBuilder()
                .setOperation(Operation.BALANCE)
                .setFruitType("banana")
                .setAmount(10)
                .build();
    }

    @Test
    public void balanceHandlerTest_ok() {
        balance.process(bananaBalance);
        int actual = Storage.fruits.get(bananaBalance.getFruitType());
        int expected = 10;
        Assert.assertEquals(expected, actual);
    }
}
