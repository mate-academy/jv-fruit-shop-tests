package core.basesyntax.service.impl;

import core.basesyntax.model.TransactionDto;
import core.basesyntax.service.Parser;
import java.util.Collections;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ParserImplTest {
    private static Parser<TransactionDto> parser;

    @BeforeClass
    public static void setUp() {
        parser = new ParserImpl(new ValidatorImpl());
    }

    @Test
    public void parseLines_parseValidData_ok() {
        List<String> list = List.of("type,fruit,quantity", "b,apple,120");
        List<TransactionDto> expected = List.of(new TransactionDto("b",
                "apple",
                120));
        Assert.assertEquals(expected, parser.parseLines(list));
    }

    @Test (expected = RuntimeException.class)
    public void parseLines_parseDataFromEmptyList_notOk() {
        List<String> list = Collections.emptyList();
        parser.parseLines(list);
    }

    @Test (expected = RuntimeException.class)
    public void parseLines_parseValueIsNull_notOk() {
        List<String> list = List.of(null);
        parser.parseLines(list);
    }

    @Test
    public void parseLines_parseWithOnlyTitleLine_Ok() {
        List<String> list = List.of("type,fruit,quantity");
        List<TransactionDto> actual = parser.parseLines(list);
        List<TransactionDto> expected = Collections.emptyList();
        Assert.assertEquals(expected,actual);
    }
}
