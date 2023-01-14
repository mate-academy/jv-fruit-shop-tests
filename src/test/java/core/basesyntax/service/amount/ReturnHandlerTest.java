package core.basesyntax.service.amount;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import org.junit.After;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ReturnHandlerTest {

    private static final FruitTransaction bananaBalance;
    private static final FruitTransaction bananaReturn;
    @Rule
    public ExpectedException expectedEx = ExpectedException.none();
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

    @After
    public void tearDown() throws Exception {
        Storage.fruits.clear();
    }

    @Test
    public void returnHandlerTest_ok() {
        balance.process(bananaBalance);
        returnHandler.process(bananaReturn);
        int actual = Storage.fruits.get(bananaBalance.getFruitType());
        int expected = 18;
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void returnHandlerTest_nullValueIsStorage_Ok() {
        returnHandler.process(bananaReturn);
        int actual = Storage.fruits.get(bananaReturn.getFruitType());
        int expected = 8;
        Assert.assertEquals(expected, actual);
    }
}
