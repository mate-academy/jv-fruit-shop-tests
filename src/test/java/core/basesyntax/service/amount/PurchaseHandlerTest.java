package core.basesyntax.service.amount;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class PurchaseHandlerTest {
    private static FruitTransaction bananaTransactionPurchase;
    private static FruitTransaction bananaTransactionBalance;
    private static OperationHandler balanceHandler;
    private static OperationHandler purchaseHandler;
    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @BeforeClass
    public static void beforeClass() {
        bananaTransactionPurchase = new FruitTransaction.FruitTransactionBuilder()
                .setOperation(Operation.PURCHASE)
                .setFruitType("banana")
                .setAmount(3)
                .build();
        bananaTransactionBalance = new FruitTransaction.FruitTransactionBuilder()
                .setOperation(Operation.BALANCE)
                .setFruitType("banana")
                .setAmount(10)
                .build();
        balanceHandler = new BalanceHandler();
        purchaseHandler = new PurchaseHandler();
    }

    @Before
    public void setUp() {
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
    public void process_purchaseFromEmptyStorage_notOK() {
        expectedEx.expect(RuntimeException.class);
        expectedEx.expectMessage("You can`t cell more fruits than you have");
        purchaseHandler.process(bananaTransactionPurchase);
    }

    @Test
    public void process_purchaseMoreThanBalance_notOk() {
        balanceHandler.process(bananaTransactionBalance);
        FruitTransaction bananaInvalidPurchase = new FruitTransaction.FruitTransactionBuilder()
                .setOperation(Operation.PURCHASE)
                .setFruitType("banana").setAmount(15).build();
        expectedEx.expect(RuntimeException.class);
        expectedEx.expectMessage("You can`t cell more fruits than you have");
        purchaseHandler.process(bananaInvalidPurchase);

    }
}
