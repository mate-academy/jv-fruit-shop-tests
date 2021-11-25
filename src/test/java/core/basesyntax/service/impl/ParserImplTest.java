package core.basesyntax.service.impl;

import core.basesyntax.model.TransactionDto;
import core.basesyntax.service.Parser;
import core.basesyntax.service.Validator;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ParserImplTest {
    public static Parser parser;
    public static Validator validator;
    public static TransactionDto transactionDto;

    @BeforeClass
    public static void initializeFields() {
        validator = new ValidatorImpl();
        parser = new ParserImpl(validator);
    }

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void parse_ValidatorIsNull_throwException() {
        expectedException.expect(RuntimeException.class);
        Parser parserWithoutValidator = new ParserImpl(null);
        parserWithoutValidator.parse("p,banana,5");
    }

    @Test
    public void parse_LineWithoutFormat_throwException() {
        expectedException.expect(RuntimeException.class);
        parser.parse("b,banana,twelve");
    }

    @Test
    public void parse_formattedLine_Ok() {
        String expectedOperation = "b";
        String expectedFruit = "banana";
        int expectedAmount = 20;
        TransactionDto transactionDto = parser.parse("b,banana,20");
        Assert.assertEquals(expectedOperation, transactionDto.getOperation());
        Assert.assertEquals(expectedFruit,transactionDto.getFruit());
        Assert.assertEquals(expectedAmount,transactionDto.getAmount());
    }
}