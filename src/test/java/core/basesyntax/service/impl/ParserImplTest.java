package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.TransactionLine;
import core.basesyntax.service.Parser;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class ParserImplTest {
    private static final Parser parserLine = new ParserImpl();
    private static TransactionLine transactionLine;

    @Test
    public void parser_Ok() {
        List<String> list = new ArrayList<>(List.of("type,fruit,quantity", "b,banana,20"));
        List<TransactionLine> expected = List.of(
                new TransactionLine("b", "banana", 20));
        List<TransactionLine> actual = parserLine.parser(list);
        assertEquals(expected, actual);
        list.clear();
    }

    @Test (expected = RuntimeException.class)
    public void parseNullList_NotOk() {
        parserLine.parser(null);
    }

    @Test (expected = RuntimeException.class)
    public void parseEmptyList_nOToK() {
        List<String> list = new ArrayList<>(List.of(""));
        parserLine.parser(list);
    }

    @Test (expected = RuntimeException.class)
    public void parseIncorrectList_NotOk() {
        List<String> list = new ArrayList<>(List.of("p,banana,20,20"));
        parserLine.parser(list);
        list.clear();
    }
}
