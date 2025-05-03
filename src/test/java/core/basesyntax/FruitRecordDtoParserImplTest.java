package core.basesyntax;

import static core.basesyntax.service.impl.Operation.BALANCE;
import static core.basesyntax.service.impl.Operation.PURCHASE;
import static core.basesyntax.service.impl.Operation.RETURN;
import static core.basesyntax.service.impl.Operation.SUPPLY;

import core.basesyntax.model.dto.FruitRecordDto;
import core.basesyntax.service.FruitRecordDtoParser;
import core.basesyntax.service.impl.FruitRecordDtoParserImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitRecordDtoParserImplTest {
    private static final List<String> EMPTY_LIST = new ArrayList<>();
    private static final List<String> INVALID_LIST = List.of("type,fruit,quantity",
            "b,banana,20",
            "b,apple,100",
            "s,banana,100",
            "p,banana,13",
            "k,apple,10",
            "p,apple,**");
    private static final List<String> VALID_LIST = List.of("type,fruit,quantity",
            "b,banana,20",
            "b,apple,100",
            "s,banana,100",
            "p,banana,13",
            "r,apple,10");

    private static FruitRecordDtoParser fruitRecordDtoParser;

    @BeforeClass
    public static void initialization() {
        fruitRecordDtoParser = new FruitRecordDtoParserImpl();
    }

    @Test
    public void parseLines_validList_ok() {
        List<FruitRecordDto> expected = new ArrayList<>();
        expected.add(new FruitRecordDto(BALANCE, "banana", 20));
        expected.add(new FruitRecordDto(BALANCE, "apple", 100));
        expected.add(new FruitRecordDto(SUPPLY, "banana", 100));
        expected.add(new FruitRecordDto(PURCHASE, "banana", 13));
        expected.add(new FruitRecordDto(RETURN, "apple", 10));
        List<FruitRecordDto> actual = fruitRecordDtoParser.parseLines(VALID_LIST);
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void parseLines_invalidList_notOk() {
        fruitRecordDtoParser.parseLines(INVALID_LIST);
    }

    @Test(expected = RuntimeException.class)
    public void parseLines_emptyList_notOk() {
        fruitRecordDtoParser.parseLines(EMPTY_LIST);
    }
}
