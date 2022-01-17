package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.TransactionLine;
import core.basesyntax.service.Parser;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class ParserImplTest {
    private static final Parser PARSE_LINE = new ParserImpl();
    private static TransactionLine transactionLine;
    private static List<String> list = new ArrayList<>();

    @Test
    public void parser_Ok() {
        list = new ArrayList<>(List.of("type,fruit,quantity", "b,banana,20"));
        List<TransactionLine> expected = List.of(
                new TransactionLine("b", "banana", 20));
        List<TransactionLine> actual = PARSE_LINE.parser(list);
        assertEquals(expected, actual);
        list.clear();
    }

    @Test (expected = RuntimeException.class)
    public void parseNullList_NotOk() {
        PARSE_LINE.parser(null);
    }

    @Test (expected = RuntimeException.class)
    public void parseEmptyList_nOToK() {
        list.add("");
        PARSE_LINE.parser(list);
    }

    @Test (expected = RuntimeException.class)
    public void parseIncorrectList_NotOk() {
        list = List.of("p,banana,20,20");
        PARSE_LINE.parser(list);
        list.clear();
    }
}
