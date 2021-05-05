package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.dto.FruitDataDto;
import core.basesyntax.operations.Operations;
import core.basesyntax.service.DataParserService;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class DataParserServiceImplTest {
    private static DataParserService dataParserService;
    private List<FruitDataDto> actual;
    private List<FruitDataDto> expected;
    private List<String> inputDataList;

    @BeforeClass
    public static void beforeClass() {
        dataParserService = new DataParserServiceImpl();
    }

    @Before
    public void before() {
        inputDataList = new ArrayList<>();
        actual = new ArrayList<>();
        expected = new ArrayList<>();
    }

    @Test
    public void parseCorrectDataFormat_Ok() {
        inputDataList.add("type,fruit,quantity");
        inputDataList.add("b,banana,20");
        inputDataList.add("b,apple,100");
        inputDataList.add("s,banana,100");
        inputDataList.add("p,banana,13");
        inputDataList.add("r,apple,10");
        inputDataList.add("p,apple,20");
        inputDataList.add("p,banana,5");
        inputDataList.add("s,banana,50");

        expected.add(new FruitDataDto(Operations.BALANCE, new Fruit("banana"), 20));
        expected.add(new FruitDataDto(Operations.BALANCE, new Fruit("apple"), 100));
        expected.add(new FruitDataDto(Operations.SUPPLY, new Fruit("banana"), 100));
        expected.add(new FruitDataDto(Operations.PURCHASE, new Fruit("banana"), 13));
        expected.add(new FruitDataDto(Operations.RETURN, new Fruit("apple"), 10));
        expected.add(new FruitDataDto(Operations.PURCHASE, new Fruit("apple"), 20));
        expected.add(new FruitDataDto(Operations.PURCHASE, new Fruit("banana"), 5));
        expected.add(new FruitDataDto(Operations.SUPPLY, new Fruit("banana"), 50));

        actual = dataParserService.parseDataFromInputFile(inputDataList);

        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void parseIncorrectDataFormat_NotOk() {
        inputDataList.add("r, banana,25,sdv");
        inputDataList.add("s, mango,25 $");
        inputDataList.add("b, cherry, 25 coins");

        dataParserService.parseDataFromInputFile(inputDataList);
    }

    @Test(expected = RuntimeException.class)
    public void parseIncorrectOperationType_NotOk() {
        inputDataList.add("q,apple,20");
        inputDataList.add("a,banana,5");
        inputDataList.add("z,banana,50");

        dataParserService.parseDataFromInputFile(inputDataList);
    }

    @Test(expected = RuntimeException.class)
    public void parseNegativeFruitQuantity_NotOk() {
        inputDataList.add("p,apple,-20");
        inputDataList.add("b,banana,5");
        inputDataList.add("r,banana,-50");

        dataParserService.parseDataFromInputFile(inputDataList);
    }
}
