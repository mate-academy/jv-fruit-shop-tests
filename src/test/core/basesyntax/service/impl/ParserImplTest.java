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
    private static Parser parser;
    private static Validator validator;
    private static TransactionDto transactionDto;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @BeforeClass
    public static void initializeFields() {
        validator = new ValidatorImpl();
        parser = new ParserImpl(validator);
    }

    @Test
    public void parse_ValidatorIsNull_throwException() {
        expectedException.expect(RuntimeException.class);
        Parser parserWithoutValidator = new ParserImpl(null);
        parserWithoutValidator.parse("p,banana,5");
    }

    @Test
    public void parse_LineWithoutFormatStyle_throwException() {
        expectedException.expect(RuntimeException.class);
        parser.parse("b,banana,twelve");
    }

    @Test
    public void parse_FormattedLine_ok() {
        String expectedOperation = "b";
        String expectedFruit = "banana";
        int expectedAmount = 20;
        TransactionDto transactionDto = parser.parse("b,banana,20");
        Assert.assertEquals(expectedOperation, transactionDto.getOperation());
        Assert.assertEquals(expectedFruit,transactionDto.getFruit());
        Assert.assertEquals(expectedAmount,transactionDto.getAmount());
    }
}
