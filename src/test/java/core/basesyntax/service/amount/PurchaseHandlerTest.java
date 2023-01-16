package core.basesyntax.service.amount;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class PurchaseHandlerTest {
    private static FruitTransaction bananaTransactionBalance;
    private static FruitTransaction bananaTransactionPurchase;
    private static FruitTransaction bananaInvalidPurchase;
    @Rule
    public ExpectedException expectedEx = ExpectedException.none();
    private final OperationHandler balanceHandler = new BalanceHandler();
    private final OperationHandler purchaseHandler = new PurchaseHandler();

    @BeforeClass
    public static void beforeClass() {
        bananaTransactionBalance = new FruitTransaction.FruitTransactionBuilder()
                .setOperation(Operation.BALANCE)
                .setFruitType("banana")
                .setAmount(10)
                .build();
        bananaTransactionPurchase = new FruitTransaction.FruitTransactionBuilder()
                .setOperation(Operation.PURCHASE)
                .setFruitType("banana")
                .setAmount(3)
                .build();
        bananaInvalidPurchase = new FruitTransaction.FruitTransactionBuilder()
                .setOperation(Operation.PURCHASE)
                .setFruitType("banana")
                .setAmount(15)
                .build();
    }

    @After
    public void tearDown() {
        Storage.fruits.clear();
    }

    @Test
    public void process_ok() {
        balanceHandler.process(bananaTransactionBalance);
        purchaseHandler.process(bananaTransactionPurchase);
        int actual = Storage.fruits.get(bananaTransactionBalance.getFruitType());
        int expected = bananaTransactionBalance.getAmount() - bananaTransactionPurchase.getAmount();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void process_notOK() {
        expectedEx.expect(RuntimeException.class);
        expectedEx.expectMessage("You can`t cell more fruits than you have");
        purchaseHandler.process(bananaTransactionPurchase);
    }

    @Test
    public void process_purchaseMoreThanBalance_notOk() {
        balanceHandler.process(bananaTransactionBalance);
        expectedEx.expect(RuntimeException.class);
        expectedEx.expectMessage("You can`t cell more fruits than you have");
        purchaseHandler.process(bananaInvalidPurchase);

    }
}
