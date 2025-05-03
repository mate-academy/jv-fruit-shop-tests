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

public class SupplyHandlerTest {
    private static FruitTransaction bananaTransactionBalance;
    private static FruitTransaction bananaTransactionSupply;
    private static OperationHandler balanceHandler;
    private static OperationHandler supplyHandler;
    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @BeforeClass
    public static void init() {
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
        supplyHandler = new SupplyHandler();
        balanceHandler = new BalanceHandler();
    }

    @Before
    public void setUp() {
        Storage.fruits.clear();
    }

    @Test
    public void process_supply_ok() {
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
