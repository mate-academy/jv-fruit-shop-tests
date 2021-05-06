package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.exception.InvalidInputException;
import core.basesyntax.model.FruitRecordDto;
import core.basesyntax.service.impl.FruitRecordParserServiceImpl;
import core.basesyntax.service.validator.DataValidator;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitRecordParserServiceTest {
    private static final FruitRecordDto R_BANANA_20_DTO =
            new FruitRecordDto("r", "banana", 20);
    private static final String R_BANANA_20_STRING = "r,banana,20";
    private static final String NEGATIVE_AMOUNT_INPUT = "r,banana,-20";
    private static final String INVALID_OPERATION_INPUT = "A,banana,20";
    private static final String UNPARSEABLE_AMOUNT_INPUT = "r,banana,amount";
    private static final String TITLE = "fruit,quantity";

    private static FruitRecordParserService parser;
    private static DataValidator dataValidator;
    private static List<FruitRecordDto> expected;

    @BeforeClass
    public static void beforeClass() throws Exception {
        dataValidator = new DataValidator();
        parser = new FruitRecordParserServiceImpl(dataValidator);
    }

    @Test
    public void getRecord_parseCorrectData_Ok() {
        expected = List.of(R_BANANA_20_DTO);
        List<FruitRecordDto> actual = parser.getRecord(List.of(TITLE,
                R_BANANA_20_STRING));
        assertEquals(expected, actual);
    }

    @Test
    public void getRecord_parseWithoutTitle_Ok() {
        expected = List.of(R_BANANA_20_DTO);
        List<FruitRecordDto> actual = parser.getRecord(List.of(R_BANANA_20_STRING));
        assertEquals(expected, actual);
    }

    @Test(expected = InvalidInputException.class)
    public void getRecord_parseNegativeAmount_NotOk() {
        List<FruitRecordDto> actual = parser.getRecord(List.of(TITLE,
                NEGATIVE_AMOUNT_INPUT));
    }

    @Test(expected = InvalidInputException.class)
    public void getRecord_parseUnparseableAmount_NotOk() {
        List<FruitRecordDto> actual = parser.getRecord(List.of(TITLE,
                UNPARSEABLE_AMOUNT_INPUT));
    }

    @Test(expected = InvalidInputException.class)
    public void getRecord_parseIncorrectOperation_NotOk() {
        List<FruitRecordDto> actual = parser.getRecord(List.of(TITLE,
                INVALID_OPERATION_INPUT));
    }
}
