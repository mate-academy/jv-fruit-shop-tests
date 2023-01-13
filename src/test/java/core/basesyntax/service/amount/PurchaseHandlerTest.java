package core.basesyntax.service.amount;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import org.junit.Assert;
import org.junit.Test;

public class PurchaseHandlerTest {

    private static final FruitTransaction bananaBalance;
    private static final FruitTransaction bananaPurchase;
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
    }

    @Test
    public void purchaseHandlerTest_ok() {
        balance.process(bananaBalance);
        purchase.process(bananaPurchase);
        int actual = Storage.fruits.get(bananaBalance.getFruitType());
        int expected = 7;
        Assert.assertEquals(expected, actual);
    }
}
