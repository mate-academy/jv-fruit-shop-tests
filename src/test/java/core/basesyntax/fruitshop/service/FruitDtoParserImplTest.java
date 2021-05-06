package core.basesyntax.fruitshop.service;

import core.basesyntax.fruitshop.exception.IncorrectOperationException;
import core.basesyntax.fruitshop.model.dto.FruitOperationDto;
import core.basesyntax.fruitshop.service.shopoperation.OperationType;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitDtoParserImplTest {
    private static FruitDtoParser fruitDtoParser;
    private static List<String> fruitDataLines;
    private static List<FruitOperationDto> fruitOperationDtoList;

    @BeforeClass
    public static void beforeClass() {
        fruitDtoParser = new FruitDtoParserImpl();
        FruitOperationDto bananaBalanceDto = new FruitOperationDto(OperationType.BALANCE,
                "banana", new BigDecimal(20));
        FruitOperationDto bananaSupplyDto = new FruitOperationDto(OperationType.SUPPLY,
                "banana", new BigDecimal(10));
        FruitOperationDto appleBalanceDto = new FruitOperationDto(OperationType.BALANCE,
                "apple", new BigDecimal(15));
        FruitOperationDto appleReturnDto = new FruitOperationDto(OperationType.RETURN,
                "apple", new BigDecimal(25));
        fruitOperationDtoList = List.of(bananaBalanceDto, appleBalanceDto,
                bananaSupplyDto, appleReturnDto);
        fruitDataLines = new ArrayList<>();
    }

    @Before
    public void setUp() {
        fruitDataLines.add("type,fruit,quantity");
    }

    @Test
    public void parse_ListOfValidOperations_isOk() {
        fruitDataLines.add("b,banana,20");
        fruitDataLines.add("b,apple,15");
        fruitDataLines.add("s,banana,10");
        fruitDataLines.add("r,apple,25");
        List<FruitOperationDto> actual = fruitDtoParser.parse(fruitDataLines);
        Assert.assertEquals(fruitOperationDtoList, actual);
    }

    @Test
    public void parse_ListWithNoOperations_isOk() {
        List<FruitOperationDto> actual = fruitDtoParser.parse(fruitDataLines);
        Assert.assertTrue(actual.isEmpty());
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void parse_EmptyListException_isThrown() {
        fruitDataLines.clear();
        fruitDtoParser.parse(fruitDataLines);
    }

    @Test(expected = IncorrectOperationException.class)
    public void parse_ListWithIncorrectOperationException_isThrown() {
        fruitDataLines.add("c,banana,20");
        fruitDtoParser.parse(fruitDataLines);
    }

    @Test(expected = IncorrectOperationException.class)
    public void parse_ListWithIncorrectRecordException_isThrown() {
        fruitDataLines.add("b,banana,ASD20");
        fruitDtoParser.parse(fruitDataLines);
    }

    @Test(expected = IncorrectOperationException.class)
    public void parse_ListWithEmptyLineException_isThrown() {
        fruitDataLines.add(",,");
        fruitDtoParser.parse(fruitDataLines);
    }

    @After
    public void tearDown() {
        fruitDataLines.clear();
    }
}
