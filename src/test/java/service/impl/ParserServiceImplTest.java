package service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;
import model.FruitTransaction;
import org.junit.Before;
import org.junit.Test;
import service.ParserService;

public class ParserServiceImplTest {
    private static final String TITLE = "type,fruit,quantity";
    private static final String SECOND_LINE = "b,banana,20";
    private static final String FRUIT_FOR_TEST = "banana";
    private static final Integer QUANTITY_OF_FRUITS = 20;
    private static final String INVALID_LINE = "z,zefir,-333";
    private static ParserService parserService;
    private static List<String> forTest;
    private static List<FruitTransaction> expected;

    @Before
    public void setUp() {
        parserService = new ParserServiceImpl();

        forTest = new ArrayList<>();
        forTest.add(TITLE);
        forTest.add(SECOND_LINE);

        FruitTransaction balance = new FruitTransaction();
        balance.setOperation(FruitTransaction.Operation.BALANCE);
        balance.setFruit(FRUIT_FOR_TEST);
        balance.setQuantity(QUANTITY_OF_FRUITS);

        expected = new ArrayList<>();
        expected.add(balance);
    }

    @Test
    public void parse_Successful_Ok() {
        List<FruitTransaction> actual = parserService.parse(forTest);
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void parse_NotSuccessful_NotOk() {
        forTest.add(INVALID_LINE);
        List<FruitTransaction> actual = parserService.parse(forTest);
        fail(RuntimeException.class.getName());
    }
}
