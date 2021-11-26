package core.basesyntax.service.impl;

import core.basesyntax.model.TransactionDto;
import core.basesyntax.service.Parser;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

public class ParserImplTest {
    private static final Parser parser = new ParserImpl(new ValidatorImpl());
    private static List<String> correctList
            = new ArrayList<>(List.of("type,fruit,quantity", "s,apple,100"));

    @After
    public void afterEachTest() {
        correctList.clear();
    }

    @Test
    public void parse_parseLines_Ok() {
        List<String> correctList1
                = new ArrayList<>(List.of("type,fruit,quantity", "s,apple,100", "p,apple,150"));
        TransactionDto transactionDto1
                = new TransactionDto("s", "apple", 100);
        TransactionDto transactionDto2
                = new TransactionDto("p", "apple", 150);
        List<TransactionDto> expected = List.of(transactionDto1, transactionDto2);
        List<TransactionDto> actual = parser.parseLines(correctList1);
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = AssertionError.class)
    public void parse_parseLine_notOk() {
        TransactionDto transactionDto
                = new TransactionDto("incorrect", "input", 100);
        List<TransactionDto> expected = List.of(transactionDto);
        List<TransactionDto> actual = parser.parseLines(correctList);
        Assert.assertEquals(expected, actual);
    }
}
