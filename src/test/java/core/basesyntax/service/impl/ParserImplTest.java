package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.OperationFruitDto;
import core.basesyntax.service.Parser;
import org.junit.BeforeClass;
import org.junit.Test;

public class ParserImplTest {
    private static Parser parser;
    private OperationFruitDto actual;
    private String string;

    @BeforeClass
    public static void beforeClass() {
        parser = new ParserImpl();
    }

    @Test
    public void parse_validData_ok() {
        OperationFruitDto expectedBalanceAppleDto
                = new OperationFruitDto("b", "apple", 100);
        string = "b,apple,100";
        actual = parser.parse(string);
        assertEquals(expectedBalanceAppleDto, actual);
        OperationFruitDto expectedReturnBananaDto
                = new OperationFruitDto("r", "banana", 50);
        string = "r,banana,50";
        actual = parser.parse(string);
        assertEquals(expectedReturnBananaDto, actual);
        OperationFruitDto expectedSupplyCherryDto
                = new OperationFruitDto("s", "cherry", 75);
        string = "s,cherry,75";
        actual = parser.parse(string);
        assertEquals(expectedSupplyCherryDto, actual);
        OperationFruitDto expectedPurchasePineappleDto
                = new OperationFruitDto("p", "pineapple", 20);
        string = "p,pineapple,20";
        actual = parser.parse(string);
        assertEquals(expectedPurchasePineappleDto, actual);
    }
}
