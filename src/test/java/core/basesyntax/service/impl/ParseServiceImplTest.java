package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ParseService;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ParseServiceImplTest {
    private static ParseService parseService;
    private static List<String> stringsFromFile;
    private static Exception exception;

    @BeforeClass
    public static void beforeClass() {
        parseService = new ParseServiceImpl();
        stringsFromFile = new ArrayList<>();
    }

    @Before
    public void setUp() {
        stringsFromFile.add("operation,fruit,quantity");
        stringsFromFile.add("b,apple,10");
        stringsFromFile.add("s,banana,20");
        exception = new Exception();
    }

    @After
    public void tearDown() {
        stringsFromFile.clear();
    }

    @Test
    public void parse_nullList_notOK() {
        try {
            parseService.parse(null);
        } catch (Exception e) {
            exception = e;
        }
        Assert.assertSame(RuntimeException.class, exception.getClass());
    }

    @Test
    public void parse_goodList_OK() {
        List<FruitTransaction> fruitTransactionList = new ArrayList<>();
        try {
            fruitTransactionList = parseService.parse(stringsFromFile);
        } catch (Exception e) {
            exception = e;
        }
        Assert.assertSame(Exception.class, exception.getClass());
        Assert.assertEquals(2, fruitTransactionList.size());
        Assert.assertEquals("apple", fruitTransactionList.get(0).getFruit());
        Assert.assertEquals(10, fruitTransactionList.get(0).getQuantity());
        Assert.assertEquals(FruitTransaction.Operation.BALANCE,
                fruitTransactionList.get(0).getOperation());
        Assert.assertEquals("banana", fruitTransactionList.get(1).getFruit());
        Assert.assertEquals(20, fruitTransactionList.get(1).getQuantity());
        Assert.assertEquals(FruitTransaction.Operation.SUPPLY,
                fruitTransactionList.get(1).getOperation());
    }
}
