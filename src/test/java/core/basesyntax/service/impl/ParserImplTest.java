package core.basesyntax.service.impl;

import core.basesyntax.model.TransactionDto;
import core.basesyntax.service.Parser;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ParserImplTest {
    private static Parser<TransactionDto> parser;

    @BeforeClass
    public static void beforeClass() {
        parser = new ParserImpl(new ValidatorImpl());
    }

    @Test
    public void parseLine_correctInput_ok() {
        String inputLine = "b,banana,1";
        TransactionDto expected = new TransactionDto("b", "banana", 1);
        TransactionDto actual = parser.parseLine(inputLine);
        Assert.assertEquals(actual, expected);
    }

    @Test (expected = RuntimeException.class)
    public void parseLine_incorrectOperation_notOk() {
        parser.parseLine("balance,banana,1");
    }

    @Test (expected = RuntimeException.class)
    public void parseLine_incorrectFruitName_notOk() {
        parser.parseLine("r, ,1");
    }

    @Test (expected = RuntimeException.class)
    public void parseLine_incorrectQuantity_notOk() {
        parser.parseLine("s,banana,+1");
    }

    @Test (expected = RuntimeException.class)
    public void parseLine_emptyInput_notOk() {
        parser.parseLine("");
    }
}
