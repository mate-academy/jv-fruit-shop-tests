package core.service.impl;

import static org.junit.Assert.assertEquals;

import core.model.FruitTransaction;
import core.service.ParserService;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class ParserServiceImplTest {
    private static final List<String> DEFAULT_LIST_WITH_DATA = List.of("b,banana,20",
            "b,apple,100",
            "s,banana,100",
            "p,banana,13",
            "r,apple,10",
            "p,apple,20",
            "p,banana,5",
            "s,banana,50");
    private static final List<FruitTransaction> DEFAULT_RESULT = List.of(
            new FruitTransaction(FruitTransaction.Activity.BALANCE, "banana", 20),
            new FruitTransaction(FruitTransaction.Activity.BALANCE, "apple", 100),
            new FruitTransaction(FruitTransaction.Activity.SUPPLY, "banana", 100),
            new FruitTransaction(FruitTransaction.Activity.PURCHASE, "banana", 13),
            new FruitTransaction(FruitTransaction.Activity.RETURN, "apple", 10),
            new FruitTransaction(FruitTransaction.Activity.PURCHASE, "apple", 20),
            new FruitTransaction(FruitTransaction.Activity.PURCHASE, "banana", 5),
            new FruitTransaction(FruitTransaction.Activity.SUPPLY, "banana", 50));
    private ParserService parserService;

    @Before
    public void setUp() throws Exception {
        parserService = new ParserServiceImpl();
    }

    @Test
    public void parseService_parseDataFromDefaultList_Ok() {
        List<FruitTransaction> actual = parserService.parse(DEFAULT_LIST_WITH_DATA);
        assertEquals(actual, DEFAULT_RESULT);
    }

    @Test
    public void parseService_parseDataWithNullData_Ok() {
        List<FruitTransaction> parse = parserService.parse(null);
        assertEquals(new ArrayList<>(), parse);
    }
}
