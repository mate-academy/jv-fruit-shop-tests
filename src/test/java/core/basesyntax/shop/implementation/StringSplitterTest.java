package core.basesyntax.shop.implementation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.shop.Fruit;
import org.junit.BeforeClass;
import org.junit.Test;

public class StringSplitterTest {
    private static StringSplitter splitter;
    private static StringSplitter incorrectSplitter;
    private static final String SPLIT_LINE = "s,banana,100";

    @BeforeClass
    public static void beforeClass() {
        splitter = new StringSplitter(SPLIT_LINE);
        incorrectSplitter = new StringSplitter("z,banana,i10");
        Storage.fruits.clear();
    }

    @Test
    public void getCount_Ok() {
        int expected = 100;
        int actual = splitter.getCount();
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void getCount_NotOk() {
        incorrectSplitter.getCount();
    }

    @Test
    public void getFruit_Ok() {
        assertEquals(new Fruit("banana"), splitter.getFruit());
    }

    @Test
    public void getFruit_NotOk() {
        assertNotEquals(new Fruit("cucumber"), splitter.getFruit());
    }

    @Test
    public void getTypeOfOperation_Ok() {
        String expected = "S";
        String actual = splitter.getTypeOfOperation();
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void getTypeOfOperation_NotOk() {
        incorrectSplitter.getTypeOfOperation();
    }
}
