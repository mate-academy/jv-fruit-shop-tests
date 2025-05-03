package core.basesyntax.service.impl;

import core.basesyntax.model.DailyActivity;
import core.basesyntax.service.Parser;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

public class ParserImplTest {
    private static final Parser parser = new ParserImpl();
    private static DailyActivity dailyActivity;

    @Test
    public void parse_Ok() {
        List<String> list = new ArrayList<>(List.of("type,fruit,amount","r,banana,20"));
        List<DailyActivity> expected = List.of(new DailyActivity("r","banana", 20));
        List<DailyActivity> actual = parser.parse(list);
        Assert.assertEquals(expected, actual);
    }

    @Test (expected = RuntimeException.class)
    public void parseEmptyList_NotOk() {
        List<String> list = new ArrayList<>(List.of(""));
        parser.parse(list);
    }

    @Test (expected = RuntimeException.class)
    public void parseListWithNull_NotOk() {
        parser.parse(null);
    }

    @Test (expected = RuntimeException.class)
    public void parseWrongList_NotOk() {
        List<String> list = new ArrayList<>(List.of("apple. 40, 57"));
        parser.parse(list);
    }
}
