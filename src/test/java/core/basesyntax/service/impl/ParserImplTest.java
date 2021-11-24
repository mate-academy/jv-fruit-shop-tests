package core.basesyntax.service.impl;

import core.basesyntax.model.Operation;
import core.basesyntax.model.TransactionDto;
import core.basesyntax.service.Parser;
import core.basesyntax.validation.OperationValidator;
import core.basesyntax.validation.OperationValidatorImpl;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ParserImplTest {
    private static Parser parser;
    private static OperationValidator operationValidator;

    @BeforeClass
    public static void beforeAll() {
        operationValidator = new OperationValidatorImpl();
        parser = new ParserImpl(operationValidator);
    }

    @Test
    public void parseLine_ok() {
        String inputLine = "s,banana,100";
        TransactionDto expected = new TransactionDto(Operation.SUPPLY, "banana", 100);
        TransactionDto actual = parser.parseLine(inputLine);
        Assert.assertEquals(expected, actual);
    }
}
