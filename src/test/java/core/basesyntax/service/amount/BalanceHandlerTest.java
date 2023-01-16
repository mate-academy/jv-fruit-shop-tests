package core.basesyntax.service.amount;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class BalanceHandlerTest {
    private static OperationHandler balanceHandler;

    @BeforeClass
    public static void beforeClass() {
        balanceHandler = new BalanceHandler();
    }

    @Test
    public void process_balance_ok() {
        FruitTransaction bananaTransactionBalance = new FruitTransaction.FruitTransactionBuilder()
                .setOperation(Operation.BALANCE)
                .setFruitType("banana")
                .setAmount(10)
                .build();
        balanceHandler.process(bananaTransactionBalance);
        int actual = Storage.fruits.get(bananaTransactionBalance.getFruitType());
        int expected = bananaTransactionBalance.getAmount();
        Assert.assertEquals(expected, actual);
    }
}
