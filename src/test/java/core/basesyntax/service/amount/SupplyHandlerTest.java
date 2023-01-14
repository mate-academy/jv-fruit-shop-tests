package core.basesyntax.service.amount;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import org.junit.After;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class SupplyHandlerTest {

    private static final FruitTransaction bananaBalance;
    private static final FruitTransaction bananaSupply;
    @Rule
    public ExpectedException expectedEx = ExpectedException.none();
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

    @After
    public void tearDown() throws Exception {
        Storage.fruits.clear();
    }

    @Test
    public void returnHandlerTest_ok() {
        balance.process(bananaBalance);
        supply.process(bananaSupply);
        int actual = Storage.fruits.get(bananaBalance.getFruitType());
        int expected = 30;
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void supplyHandlerTest_nullValueIsStorage_Ok() {
        supply.process(bananaSupply);
        int actual = Storage.fruits.get(bananaSupply.getFruitType());
        int expected = 20;
        Assert.assertEquals(expected, actual);
    }
}
