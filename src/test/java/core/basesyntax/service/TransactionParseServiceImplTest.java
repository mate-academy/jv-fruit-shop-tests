package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import org.junit.Before;
import org.junit.Test;

public class TransactionParseServiceImplTest {
    private TransactionParseService parserService;
    private List<String> dailyTransactionList;

    @Before
    public void setUp() throws Exception {
        parserService = new TransactionParseServiceImpl();
        dailyTransactionList = new ArrayList<>();
    }

    @Test
    public void parseList_Ok() {
        dailyTransactionList.add("type,fruit,quantity");
        dailyTransactionList.add("b,banana,20");
        dailyTransactionList.add("b,apple,100");
        List<FruitTransaction> lists = parserService.parse(dailyTransactionList);
        assertEquals(2,lists.size());
        assertEquals("banana",lists.get(0).getFruit());
        assertEquals("apple",lists.get(1).getFruit());
    }

    @Test
    public void parseListNoFruits_Ok() {
        dailyTransactionList.add("type,fruit,quantity");
        List<FruitTransaction> lists = parserService.parse(dailyTransactionList);
        assertEquals(0,lists.size());
    }

    @Test
    public void parseListEmpty_Ok() {
        List<FruitTransaction> lists = parserService.parse(dailyTransactionList);
        assertEquals(0,lists.size());
    }

    @Test (expected = NoSuchElementException.class)
    public void parseListBadData_NotOk() {
        dailyTransactionList.add("type,fruit,quantity");
        dailyTransactionList.add("apple,100");
        List<FruitTransaction> lists = parserService.parse(dailyTransactionList);
    }
}
