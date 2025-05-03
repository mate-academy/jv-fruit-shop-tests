package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.OperationFruitDto;
import core.basesyntax.service.Parser;
import org.junit.BeforeClass;
import org.junit.Test;

public class ParserImplTest {
    private static Parser parser;
    private OperationFruitDto actual;
    private OperationFruitDto expected;
    private String string;

    @BeforeClass
    public static void beforeClass() {
        parser = new ParserImpl();
    }

    @Test(expected = RuntimeException.class)
    public void parse_invalidOperation_notOk() {
        expected = new OperationFruitDto("q", "apple", 100);
        string = "q,apple,100";
        actual = parser.parse(string);
    }

    @Test
    public void parse_balanceValidData_ok() {
        expected = new OperationFruitDto("b", "apple", 100);
        string = "b,apple,100";
        actual = parser.parse(string);
        assertEquals(expected, actual);
    }

    @Test
    public void parse_returnValidData_ok() {
        expected = new OperationFruitDto("r", "banana", 50);
        string = "r,banana,50";
        actual = parser.parse(string);
        assertEquals(expected, actual);
    }

    @Test
    public void parse_supplyValidData_ok() {
        expected = new OperationFruitDto("s", "cherry", 75);
        string = "s,cherry,75";
        actual = parser.parse(string);
        assertEquals(expected, actual);
    }

    @Test
    public void parse_purchaseValidData_ok() {
        expected = new OperationFruitDto("p", "pineapple", 20);
        string = "p,pineapple,20";
        actual = parser.parse(string);
        assertEquals(expected, actual);
    }
}
