package core.basesyntax.service.amount;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import org.junit.Assert;
import org.junit.Test;

public class ReturnHandlerTest {

    private static final FruitTransaction bananaBalance;
    private static final FruitTransaction bananaReturn;
    private final OperationHandler balance = new BalanceHandler();
    private final OperationHandler returnHandler = new ReturnHandler();

    static {
        bananaBalance = new FruitTransaction.FruitTransactionBuilder()
                .setOperation(Operation.BALANCE)
                .setFruitType("banana")
                .setAmount(10)
                .build();
        bananaReturn = new FruitTransaction.FruitTransactionBuilder()
                .setOperation(Operation.PURCHASE)
                .setFruitType("banana")
                .setAmount(8)
                .build();
    }

    @Test
    public void returnHandlerTest_ok() {
        balance.process(bananaBalance);
        returnHandler.process(bananaReturn);
        int actual = Storage.fruits.get(bananaBalance.getFruitType());
        int expected = 18;
        Assert.assertEquals(expected, actual);
    }
}
