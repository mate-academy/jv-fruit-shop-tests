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

public class ReturnHandlerTest {
    private static FruitTransaction bananaTransactionBalance;
    private static FruitTransaction bananaTransactionReturn;
    @Rule
    public ExpectedException expectedEx = ExpectedException.none();
    private final OperationHandler balanceHandler = new BalanceHandler();
    private final OperationHandler returnHandler = new ReturnHandler();

    @BeforeClass
    public static void beforeClass() {
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
    }

    @After
    public void tearDown() {
        Storage.fruits.clear();
    }

    @Test
    public void process_ok() {
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
