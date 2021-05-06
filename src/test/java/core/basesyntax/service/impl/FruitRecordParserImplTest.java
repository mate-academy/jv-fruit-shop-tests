package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.dto.FruitRecordDto;
import core.basesyntax.service.FruitRecordParser;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitRecordParserImplTest {
    private static FruitRecordDto fruitRecordDtoBalance;
    private static FruitRecordDto fruitRecordDtoSupply;
    private static FruitRecordDto fruitRecordDtoReturn;
    private static FruitRecordDto fruitRecordDtoPurchase;
    private static FruitRecordParser fruitRecordParser;
    private final List<String> rawRecords = new ArrayList<>();
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

    @After
    public void tearDown() {
        expected.clear();
        rawRecords.clear();
    }

    @Test
    public void parseValidData_Ok() {
        rawRecords.add("b,banana,100");
        rawRecords.add("s,apple,125");
        rawRecords.add("r,banana,150");
        rawRecords.add("p,apple,75");
        List<FruitRecordDto> actual = fruitRecordParser.parse(rawRecords);
        assertEquals(expected, actual);
    }

    @Test
    public void parseValidDataWithTitle_Ok() {
        rawRecords.add("type,fruit ,quantity");
        rawRecords.add("b,banana,100");
        rawRecords.add("s,apple,125");
        rawRecords.add("r,banana,150");
        rawRecords.add("p,apple,75");
        List<FruitRecordDto> actual = fruitRecordParser.parse(rawRecords);
        assertEquals(expected, actual);
    }

    @Test
    public void parseDataWithSpaces_Ok() {
        rawRecords.add(" b ,   banana ,  100 ");
        rawRecords.add("s ,apple,125");
        rawRecords.add("r   ,banana,150 ");
        rawRecords.add("  p,apple , 75 ");
        List<FruitRecordDto> actual = fruitRecordParser.parse(rawRecords);
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void parseDataWithInvalidOperationType_NotOk() {
        rawRecords.add("z,banana,100");
        rawRecords.add("w,apple,125");
        rawRecords.add("y,banana,150");
        rawRecords.add("o,apple,75");
        fruitRecordParser.parse(rawRecords);
    }

    @Test(expected = RuntimeException.class)
    public void parseDataWithInvalidQuantity_NotOk() {
        rawRecords.add("b,banana,-100");
        rawRecords.add("s,apple,-125");
        rawRecords.add("r,banana,-150");
        rawRecords.add("p,apple,-75");
        fruitRecordParser.parse(rawRecords);
    }

    @Test(expected = RuntimeException.class)
    public void parseDataWithInvalidTitle_NotOk() {
        rawRecords.add("type,fruit ,quantity, cost");
        rawRecords.add("b,banana,100, 25");
        rawRecords.add("s,apple,125, 50");
        rawRecords.add("r,banana,150, 75");
        rawRecords.add("p,apple,75, 15");
        fruitRecordParser.parse(rawRecords);
    }
}
