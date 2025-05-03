package core.basesyntax.operations;

import static core.basesyntax.db.Storage.fruitStorage;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import core.basesyntax.exception.OperationException;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class BalanceOperationTest {
    private static BalanceOperation balanceOperation;

    @BeforeClass
    public static void beforeClass() {
        balanceOperation = new BalanceOperation();
    }

    @Test
    public void setAppleBalance_Ok() {
        balanceOperation.action("apple", 10);
        int actual = fruitStorage.get("apple");
        assertFalse(fruitStorage.isEmpty());
        assertEquals(10, actual);
    }

    @Test(expected = OperationException.class)
    public void setBalanceForNullFruit_NotOk() {
        balanceOperation.action(null, 10);
    }

    @Test(expected = OperationException.class)
    public void setNegativeBalance_NotOk() {
        balanceOperation.action("apple", -10);
    }

    @Test(expected = OperationException.class)
    public void bigLetterInFruitName_NotOk() {
        balanceOperation.action("Apple", 10);
    }

    @Test(expected = OperationException.class)
    public void incorrectSymbolsInFruitName_NotOk() {
        balanceOperation.action("$arvisberry", 10);
    }

    @After
    public void afterEach() {
        fruitStorage.clear();
    }
}
