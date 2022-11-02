package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ParserService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ParserServiceImplTest {
    private static ParserService parserService;

    @Before
    public void setUp() {
        parserService = new ParserServiceImpl();
    }

    @Test
    public void parse_ValidData_Ok() {
        List<String> testList = new ArrayList<>();
        testList.add("type,fruit,quantity");
        testList.add("b,apple,120");
        testList.add("r,apple,10");
        List<FruitTransaction> expected = new ArrayList<>();
        expected.add(new FruitTransaction(FruitTransaction.Operation.BALANCE,"apple",120));
        expected.add(new FruitTransaction(FruitTransaction.Operation.RETURN,"apple",10));
        List<FruitTransaction> actual = parserService.parseData(testList);
        Assert.assertEquals(expected,actual);
    }

    @Test(expected = RuntimeException.class)
    public void parse_InvalidData_notOk() {
        List<String> testList = new ArrayList<>();
        testList.add("b,banana,someNumber");
        testList.add("s,apple,seven");
        parserService.parseData(testList);
    }

    @Test(expected = RuntimeException.class)
    public void parse_EmptyList_notOk() {
        parserService.parseData(Collections.emptyList());
    }

}
