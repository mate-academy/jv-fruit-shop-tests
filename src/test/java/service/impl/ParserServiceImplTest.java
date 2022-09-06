package service.impl;

import java.util.ArrayList;
import java.util.List;
import model.Fruit;
import model.Transaction;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import service.ParserService;

public class ParserServiceImplTest {
    private ParserService parserService;
    private Fruit fruit;

    @Before
    public void setUp() {
        parserService = new ParserServiceImpl();
        fruit = new Fruit("banana");
    }

    @Test
    public void parse_valid_ok() {
        List<Transaction> expected = new ArrayList<>();
        expected.add(new Transaction(Transaction.Operation.BALANCE, fruit, 16));
        expected.add(new Transaction(Transaction.Operation.SUPPLY, fruit, 15));
        List<String> inputList = new ArrayList<>();
        inputList.add("type,fruit,quantity");
        inputList.add("b,banana,16");
        inputList.add("s,banana,15");
        Assert.assertEquals(expected, parserService.parse(inputList));
    }
}
