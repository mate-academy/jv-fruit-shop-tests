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

public class ReturnHandlerTest {
    private static FruitTransaction bananaTransactionBalance;
    private static FruitTransaction bananaTransactionReturn;
    private static OperationHandler balanceHandler;
    private static OperationHandler returnHandler;
    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @BeforeClass
    public static void init() {
        bananaTransactionBalance = new FruitTransaction.FruitTransactionBuilder()
                .setOperation(Operation.BALANCE)
                .setFruitType("banana")
                .setAmount(10)
                .build();
        bananaTransactionReturn = new FruitTransaction.FruitTransactionBuilder()
                .setOperation(Operation.PURCHASE)
                .setFruitType("banana")
                .setAmount(8)
                .build();
        returnHandler = new ReturnHandler();
        balanceHandler = new BalanceHandler();
    }

    @Before
    public void setUp() {
        Storage.fruits.clear();
    }

    @Test
    public void process_return_ok() {
        balanceHandler.process(bananaTransactionBalance);
        returnHandler.process(bananaTransactionReturn);
        int actual = Storage.fruits.get(bananaTransactionBalance.getFruitType());
        int expected = bananaTransactionReturn.getAmount() + bananaTransactionBalance.getAmount();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void process_nullValueIsStorage_Ok() {
        returnHandler.process(bananaTransactionReturn);
        int actual = Storage.fruits.get(bananaTransactionReturn.getFruitType());
        int expected = bananaTransactionReturn.getAmount();
        Assert.assertEquals(expected, actual);
    }
}
