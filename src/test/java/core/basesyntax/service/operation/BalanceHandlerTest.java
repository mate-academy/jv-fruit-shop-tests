package core.basesyntax.service.operation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.NullFruitNameException;
import core.basesyntax.models.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BalanceHandlerTest {
    private static final FruitTransaction.Operation DEFAULT_OPERATION
                = FruitTransaction.Operation.BALANCE;
    private static final String DEFAULT_FRUIT_NAME = "banana";
    private static final int DEFAULT_FRUIT_QUANTITY = 10;
    private static BalanceHandler balanceHandler;
    private FruitTransaction fruitTransaction;

    @BeforeAll
    static void beforeAll() {
        balanceHandler = new BalanceHandler();
    }

    @BeforeEach
    public void setUp() {
        fruitTransaction = new FruitTransaction(DEFAULT_OPERATION,
                DEFAULT_FRUIT_NAME, DEFAULT_FRUIT_QUANTITY);
    }

    @Test
    public void handle_fruitTransactionWithNullName_notOk() {
        fruitTransaction.setFruit(null);
        assertThrows(NullFruitNameException.class, () -> balanceHandler.handle(fruitTransaction));
    }

    @Test
    public void handle_fruitTransactionWithoutName_notOk() {
        fruitTransaction.setFruit("");
        assertThrows(NullFruitNameException.class, () -> balanceHandler.handle(fruitTransaction));
    }

    @Test
    public void handle_validFruitTransaction_ok() {
        balanceHandler.handle(fruitTransaction);
        int actual = Storage.fruitMap.get(fruitTransaction.getFruit());
        assertEquals(actual, DEFAULT_FRUIT_QUANTITY);
    }

    @AfterEach
    public void tearDown() {
        Storage.fruitMap.clear();
    }
}
