package core.basesyntax.dao.impl;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.ParseFruitAction;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class ParseFruitActionImplTest {
    private static ParseFruitAction parseFruitAction;

    @BeforeClass
    public static void setUp() {
        parseFruitAction = new ParseFruitActionImpl();
    }

    @Test
    public void parseFruit_NullData_NotOk() {
        assertThrows(RuntimeException.class, () -> parseFruitAction.parseFruit(null));
    }

    @AfterClass
    public static void clear() {
        Storage.fruits.clear();
    }
}
