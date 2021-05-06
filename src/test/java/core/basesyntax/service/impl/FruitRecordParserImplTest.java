package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.dto.FruitRecordDto;
import core.basesyntax.service.FruitRecordParser;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitRecordParserImplTest {
    private static FruitRecordDto fruitRecordDtoBalance;
    private static FruitRecordDto fruitRecordDtoSupply;
    private static FruitRecordDto fruitRecordDtoReturn;
    private static FruitRecordDto fruitRecordDtoPurchase;
    private static FruitRecordParser fruitRecordParser;
    private final List<String> strings = new ArrayList<>();
    private final List<FruitRecordDto> expected = new ArrayList<>();

    @BeforeClass
    public static void beforeClass() {
        fruitRecordDtoBalance = new FruitRecordDto(OperationType.BALANCE, "banana", 100);
        fruitRecordDtoSupply = new FruitRecordDto(OperationType.SUPPLY, "apple", 125);
        fruitRecordDtoReturn = new FruitRecordDto(OperationType.RETURN, "banana", 150);
        fruitRecordDtoPurchase = new FruitRecordDto(OperationType.PURCHASE, "apple", 75);
        fruitRecordParser = new FruitRecordParserImpl();
    }

    @Before
    public void setUp() {
        expected.add(fruitRecordDtoBalance);
        expected.add(fruitRecordDtoSupply);
        expected.add(fruitRecordDtoReturn);
        expected.add(fruitRecordDtoPurchase);
    }

    @Test
    public void parseValidData_Ok() {
        strings.add("b,banana,100");
        strings.add("s,apple,125");
        strings.add("r,banana,150");
        strings.add("p,apple,75");
        List<FruitRecordDto> actual = fruitRecordParser.parse(strings);
        assertEquals(expected, actual);
    }

    @Test
    public void parseValidDataWithTitle_Ok() {
        strings.add("type,fruit ,quantity");
        strings.add("b,banana,100");
        strings.add("s,apple,125");
        strings.add("r,banana,150");
        strings.add("p,apple,75");
        List<FruitRecordDto> actual = fruitRecordParser.parse(strings);
        assertEquals(expected, actual);
    }

    @Test
    public void parseDataWithSpaces_Ok() {
        strings.add(" b ,   banana ,  100 ");
        strings.add("s ,apple,125");
        strings.add("r   ,banana,150 ");
        strings.add("  p,apple , 75 ");
        List<FruitRecordDto> actual = fruitRecordParser.parse(strings);
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void parseDataWithInvalidOperationType_NotOk() {
        strings.add("z,banana,100");
        strings.add("w,apple,125");
        strings.add("y,banana,150");
        strings.add("o,apple,75");
        fruitRecordParser.parse(strings);
    }

    @Test(expected = RuntimeException.class)
    public void parseDataWithInvalidQuantity_NotOk() {
        strings.add("b,banana,-100");
        strings.add("s,apple,-125");
        strings.add("r,banana,-150");
        strings.add("p,apple,-75");
        fruitRecordParser.parse(strings);
    }

    @Test(expected = RuntimeException.class)
    public void parseDataWithInvalidTitle_NotOk() {
        strings.add("type,fruit ,quantity, cost");
        strings.add("b,banana,100, 25");
        strings.add("s,apple,125, 50");
        strings.add("r,banana,150, 75");
        strings.add("p,apple,75, 15");
        fruitRecordParser.parse(strings);
    }
}
