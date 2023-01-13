package core.basesyntax.service.amount;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import org.junit.Assert;
import org.junit.Test;

public class SupplyHandlerTest {

    private static final FruitTransaction bananaBalance;
    private static final FruitTransaction bananaSupply;
    private final OperationHandler balance = new BalanceHandler();
    private final OperationHandler supply = new SupplyHandler();

    static {
        bananaBalance = new FruitTransaction.FruitTransactionBuilder()
                .setOperation(Operation.BALANCE)
                .setFruitType("banana")
                .setAmount(10)
                .build();
        bananaSupply = new FruitTransaction.FruitTransactionBuilder()
                .setOperation(Operation.PURCHASE)
                .setFruitType("banana")
                .setAmount(20)
                .build();
    }

    @Test
    public void returnHandlerTest_ok() {
        balance.process(bananaBalance);
        supply.process(bananaSupply);
        int actual = Storage.fruits.get(bananaBalance.getFruitType());
        int expected = 30;
        Assert.assertEquals(expected, actual);
    }
}
