package core.basesyntax.data.impl;

import core.basesyntax.data.DataParser;
import core.basesyntax.dto.TransactionDto;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.Operation;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitShopDataParserTest {
    private static final List<String> validRawData = new ArrayList<>();
    private static final List<String> invalidRawData = new ArrayList<>();
    private static final List<TransactionDto> expectedValidDto = new ArrayList<>();
    private static final List<TransactionDto> expectedInvalidDto = new ArrayList<>();
    private static DataParser fruitShopDataParser;

    @BeforeClass
    public static void beforeClass() {
        fruitShopDataParser = new FruitShopDataParser();

        validRawData.add("type,fruit,quantity");
        validRawData.add("b,apple,100");
        validRawData.add("b,banana,20");
        validRawData.add("s,apple,20");
        validRawData.add("s,banana,100");

        expectedValidDto.add(new TransactionDto(Operation.BALANCE, new Fruit("apple"), 100));
        expectedValidDto.add(new TransactionDto(Operation.BALANCE, new Fruit("banana"), 20));
        expectedValidDto.add(new TransactionDto(Operation.SUPPLY, new Fruit("apple"), 20));
        expectedValidDto.add(new TransactionDto(Operation.SUPPLY, new Fruit("banana"), 100));

        invalidRawData.add("         ");
        invalidRawData.add("type,fruit,quantity");
        invalidRawData.add("bp,applebanana,-200");
        invalidRawData.add("appleapplebanana");
    }

    @Test
    public void convert_TestWithValidData() {
        List<TransactionDto> actual = fruitShopDataParser.convert(validRawData);
        Assert.assertEquals(expectedValidDto, actual);
    }

    @Test
    public void convert_TestWithInvalidData() {
        List<TransactionDto> actual = fruitShopDataParser.convert(invalidRawData);
        Assert.assertEquals(expectedInvalidDto, actual);
    }
}
