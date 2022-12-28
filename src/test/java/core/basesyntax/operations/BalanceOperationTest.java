package core.basesyntax.operations;

import static core.basesyntax.db.Storage.fruitStorage;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import core.basesyntax.exception.OperationException;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class BalanceOperationTest {
    private static final String APPLE_NAME = "apple";
    private static final String ORANGE_NAME = "orange";
    private static final String NULL_NAME = null;
    private static final int FRUIT_AMOUNT = 10;
    private static final int NEGATIVE_AMOUNT = -10;
    private static BalanceOperation balanceOperation;

    @BeforeClass
    public static void beforeClass() {
        balanceOperation = new BalanceOperation();
    }

    @Test
    public void setAppleBalance_Ok() {
        balanceOperation.action(APPLE_NAME, FRUIT_AMOUNT);
        int actual = fruitStorage.get(APPLE_NAME);
        assertFalse(fruitStorage.isEmpty());
        assertEquals(FRUIT_AMOUNT, actual);
    }

    @Test
    public void setSomeBalances_Ok() {
        balanceOperation.action(APPLE_NAME, FRUIT_AMOUNT);
        balanceOperation.action(ORANGE_NAME, FRUIT_AMOUNT);
        int actual = fruitStorage.get(APPLE_NAME);
        assertEquals(FRUIT_AMOUNT, actual);
        actual = fruitStorage.get(ORANGE_NAME);
        assertEquals(FRUIT_AMOUNT, actual);
        assertFalse(fruitStorage.isEmpty());
    }

    @Test(expected = OperationException.class)
    public void setBalanceForNullFruit_NotOk() {
        balanceOperation.action(NULL_NAME, FRUIT_AMOUNT);
    }

    @Test(expected = OperationException.class)
    public void setNegativeBalance_NotOk() {
        balanceOperation.action(APPLE_NAME, NEGATIVE_AMOUNT);
    }

    @Test(expected = OperationException.class)
    public void bigLetterInFruitName_NotOk() {
        balanceOperation.action("Apple", FRUIT_AMOUNT);
    }

    @Test(expected = OperationException.class)
    public void incorrectSymbolsInFruitName_NotOk() {
        balanceOperation.action("$arvisberry", FRUIT_AMOUNT);
    }

    @After
    public void afterEach() {
        fruitStorage.clear();
    }
}
