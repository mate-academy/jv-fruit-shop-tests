package core.basesyntax.service.amount;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import org.junit.After;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class PurchaseHandlerTest {

    private static final FruitTransaction bananaBalance;
    private static final FruitTransaction bananaPurchase;
    private static final FruitTransaction bananaPurchaseInvalid;
    @Rule
    public ExpectedException expectedEx = ExpectedException.none();
    private final OperationHandler balance = new BalanceHandler();
    private final OperationHandler purchase = new PurchaseHandler();

    static {
        bananaBalance = new FruitTransaction.FruitTransactionBuilder()
                .setOperation(Operation.BALANCE)
                .setFruitType("banana")
                .setAmount(10)
                .build();
        bananaPurchase = new FruitTransaction.FruitTransactionBuilder()
                .setOperation(Operation.PURCHASE)
                .setFruitType("banana")
                .setAmount(3)
                .build();
        bananaPurchaseInvalid = new FruitTransaction.FruitTransactionBuilder()
                .setOperation(Operation.PURCHASE)
                .setFruitType("banana")
                .setAmount(15)
                .build();
    }

    @After
    public void tearDown() throws Exception {
        Storage.fruits.clear();
    }

    @Test
    public void purchaseHandlerTest_ok() {
        balance.process(bananaBalance);
        purchase.process(bananaPurchase);
        int actual = Storage.fruits.get(bananaBalance.getFruitType());
        int expected = 7;
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void purchaseHandler_notOK() {
        expectedEx.expect(RuntimeException.class);
        expectedEx.expectMessage("You can`t cell more fruits than you have");
        purchase.process(bananaPurchase);
    }

    @Test
    public void purchaseHandlerTest_purchaseMoreThanBalance_notOk() {
        balance.process(bananaBalance);
        expectedEx.expect(RuntimeException.class);
        expectedEx.expectMessage("You can`t cell more fruits than you have");
        purchase.process(bananaPurchaseInvalid);

    }
}
