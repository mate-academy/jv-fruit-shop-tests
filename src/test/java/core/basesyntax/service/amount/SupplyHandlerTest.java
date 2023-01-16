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

public class SupplyHandlerTest {
    private static FruitTransaction bananaTransactionBalance;
    private static FruitTransaction bananaTransactionSupply;
    @Rule
    public ExpectedException expectedEx = ExpectedException.none();
    private final OperationHandler balanceHandler = new BalanceHandler();
    private final OperationHandler supplyHandler = new SupplyHandler();

    @BeforeClass
    public static void beforeClass() {
        bananaTransactionBalance = new FruitTransaction.FruitTransactionBuilder()
                .setOperation(Operation.BALANCE)
                .setFruitType("banana")
                .setAmount(10)
                .build();
        bananaTransactionSupply = new FruitTransaction.FruitTransactionBuilder()
                .setOperation(Operation.PURCHASE)
                .setFruitType("banana")
                .setAmount(20)
                .build();
    }

    @After
    public void tearDown() {
        Storage.fruits.clear();
    }

    @Test
    public void process_ok() {
        balanceHandler.process(bananaTransactionBalance);
        supplyHandler.process(bananaTransactionSupply);
        int actual = Storage.fruits.get(bananaTransactionBalance.getFruitType());
        int expected = bananaTransactionSupply.getAmount() + bananaTransactionBalance.getAmount();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void process_nullValueIsStorage_Ok() {
        supplyHandler.process(bananaTransactionSupply);
        int actual = Storage.fruits.get(bananaTransactionSupply.getFruitType());
        int expected = bananaTransactionSupply.getAmount();
        Assert.assertEquals(expected, actual);
    }
}
