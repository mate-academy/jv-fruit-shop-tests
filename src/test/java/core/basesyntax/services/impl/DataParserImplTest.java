package core.basesyntax.services.impl;

import core.basesyntax.dto.TransactionDto;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.Operation;
import core.basesyntax.services.DataParser;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class DataParserImplTest {
    private static List<String> emptyList;
    private static List<String> validList;
    private static List<String> invalidList;

    private static DataParser dataParser;

    @BeforeClass
    public static void beforeClass() {
        emptyList = new ArrayList<>();
        dataParser = new DataParserImpl();
        validList = List.of("type,fruit,quantity",
                "b,banana,25",
                "s,banana,50",
                "b,apple,77",
                "r,apple,10",
                "p,banana,13");
        invalidList = List.of("type,fruit,quantity",
                "b,banana,20",
                "b,apple,100",
                "s,banana,100",
                "p,banana,13",
                "k,apple,10",
                "p,apple,**");
    }

    @Test
    public void parseLines_validList_ok() {
        List<TransactionDto> expected = new ArrayList<>();
        expected.add(new TransactionDto(Operation.BALANCE, new Fruit("banana"), 25));
        expected.add(new TransactionDto(Operation.SUPPLY, new Fruit("banana"), 50));
        expected.add(new TransactionDto(Operation.BALANCE, new Fruit("apple"), 77));
        expected.add(new TransactionDto(Operation.RETURN, new Fruit("apple"), 10));
        expected.add(new TransactionDto(Operation.PURCHASE, new Fruit("banana"), 13));
        List<TransactionDto> actual = dataParser.parse(validList);
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void parseLines_invalidList_notOk() {
        dataParser.parse(invalidList);
    }

    @Test(expected = RuntimeException.class)
    public void parseLines_emptyList_notOk() {
        dataParser.parse(emptyList);
    }
}
