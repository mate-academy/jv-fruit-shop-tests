package core.basesyntax.service.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.model.TransactionDto;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ParserImplTest {
    private static ParserImpl parser;

    @BeforeClass
    public static void beforeClass() {
        parser = new ParserImpl(new ValidatorImpl());
    }

    @Test
    public void parser_correctInput_ok() {
        String inputLine = "b,banana,1";
        TransactionDto expected = new TransactionDto("b", "banana", 1);
        TransactionDto actual = parser.parseLine(inputLine);
        Assert.assertEquals(actual, expected);
    }

    @Test (expected = RuntimeException.class)
    public void parser_incorrectOperation_notOk() {
        parser.parseLine("balance,banana,1");
    }

    @Test (expected = RuntimeException.class)
    public void parser_incorrectFruitName_notOk() {
        parser.parseLine("r, ,1");
    }

    @Test (expected = RuntimeException.class)
    public void parser_incorrectQuantity_notOk() {
        parser.parseLine("s,banana,+1");
    }

    @Test (expected = RuntimeException.class)
    public void parser_emptyInput_notOk() {
        parser.parseLine("");
    }

    @After
    public void tearDown() {
        Storage.storage.clear();
    }
}
