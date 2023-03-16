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
    private static ParserService parserService;
    private static List<String> forTest;
    private static List<FruitTransaction> expected;

    @Before
    public void setUp() {
        parserService = new ParserServiceImpl();

        forTest = new ArrayList<>();
        forTest.add("type,fruit,quantity");
        forTest.add("b,banana,20");

        FruitTransaction balance = new FruitTransaction();
        balance.setOperation(FruitTransaction.Operation.BALANCE);
        balance.setFruit("banana");
        balance.setQuantity(20);

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
        forTest.add("z,zefir,-333");
        List<FruitTransaction> actual = parserService.parse(forTest);
        fail(RuntimeException.class.getName());
    }
}
