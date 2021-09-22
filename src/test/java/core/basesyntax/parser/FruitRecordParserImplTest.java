package core.basesyntax.parser;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.OperationType;
import core.basesyntax.model.TransactionDto;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitRecordParserImplTest {
    private static FruitRecordParserImpl fruitRecordParser;
    private static List<String> emptyList;
    private static List<String> fullList;
    private static List<TransactionDto> expectedList;
    private static List<String> nullList;
    private List<TransactionDto> expected;
    private List<TransactionDto> actual;

    @BeforeClass
    public static void setUp() {
        fruitRecordParser = new FruitRecordParserImpl();
        emptyList = new ArrayList<>();
        fullList = new ArrayList<>(List.of("b,banana,20",
                "b,apple,100", "s,banana,100", "p,banana,13"));
        emptyList = new ArrayList<>();
        expectedList = new ArrayList<>();
        expectedList.add(new TransactionDto(OperationType.BALANCE, new Fruit("banana"), 20));
        expectedList.add(new TransactionDto(OperationType.BALANCE, new Fruit("apple"), 100));
        expectedList.add(new TransactionDto(OperationType.SUPPLY, new Fruit("banana"), 100));
        expectedList.add(new TransactionDto(OperationType.PURCHASE, new Fruit("banana"), 13));
        nullList = null;
    }

    @Test
    public void parse_emptyList_Ok() {
        expected = new ArrayList<>();
        actual = fruitRecordParser.parse(emptyList);
        assertEquals("Test failed! You should returned empty list.", expected, actual);
    }

    @Test
    public void parse_fullList_Ok() {
        expected = expectedList;
        actual = fruitRecordParser.parse(fullList);
        Assert.assertEquals("Test failed! Lists are different.", expected, actual);
    }

    @Test(expected = NullPointerException.class)
    public void parse_nullList_NotOk() {
        fruitRecordParser.parse(nullList);
    }
}
