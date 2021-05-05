package service;

import static org.junit.Assert.assertEquals;

import java.util.List;
import model.FruitDataDto;
import org.junit.BeforeClass;
import org.junit.Test;
import service.util.DataValidator;
import service.util.DataValidatorImpl;

public class DataParserTest {
    public static final List<String> PARSABLE_DATA =
            List.of("activity,fruit,quantity", "b,parse,10", "s,this,20", "r,please,30");
    public static final List<FruitDataDto> EXPECTED_DTO_LIST =
            List.of(new FruitDataDto("b", "parse", 10),
            new FruitDataDto("s", "this", 20),
            new FruitDataDto("r", "please", 30));
    public static final List<String> DATA_WITH_WRONG_FIRST_LINE =
            List.of("not\"activity,fruit,quantity\"");
    public static final List<String> DATA_WITH_WRONG_ACTIVITY =
            List.of("activity,fruit,quantity", "a,parseThis,10");
    public static final List<String> DATA_WITH_NEGATIVE_QUANTITY =
            List.of("activity,fruit,quantity", "s,parse,-10");
    public static final List<String> DATA_WITHOUT_NUMBER =
            List.of("activity,fruit,quantity", "s,parse,ab");
    private static DataValidator dataValidator;
    private static DataParser dataParser;
    private static List<String> dataToParse;

    @BeforeClass
    public static void beforeClass() {
        dataValidator = new DataValidatorImpl();
        dataParser = new DataParserImpl(dataValidator);
    }

    @Test
    public void parseData_NormalData_OK() {
        dataToParse = PARSABLE_DATA;
        List<FruitDataDto> actual = dataParser.parseData(dataToParse);
        List<FruitDataDto> expected = EXPECTED_DTO_LIST;
        assertEquals(actual, expected);
    }

    @Test(expected = RuntimeException.class)
    public void parseData_WrongFirstLine_NotOk() {
        dataToParse = DATA_WITH_WRONG_FIRST_LINE;
        dataParser.parseData(dataToParse);
    }

    @Test(expected = RuntimeException.class)
    public void parseData_WrongInputActivity_NotOk() {
        dataToParse = DATA_WITH_WRONG_ACTIVITY;
        dataParser.parseData(dataToParse);
    }

    @Test(expected = RuntimeException.class)
    public void parseData_WrongInputQuantity_NotOk() {
        dataToParse = DATA_WITH_NEGATIVE_QUANTITY;
        dataParser.parseData(dataToParse);
    }

    @Test(expected = RuntimeException.class)
    public void parseData_WrongInputQuantityNotNumber_NotOk() {
        dataToParse = DATA_WITHOUT_NUMBER;
        dataParser.parseData(dataToParse);
    }
}
