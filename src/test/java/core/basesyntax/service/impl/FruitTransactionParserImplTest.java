package core.basesyntax.service.impl;

import core.basesyntax.exception.FruitTransactionException;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitTransactionParser;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitTransactionParserImplTest {
    private static FruitTransactionParser transactionParser;

    @BeforeClass
    public static void setUp() {
        transactionParser = new FruitTransactionParserImpl();
    }

    @Test
    public void parse_ValidInput_ok() {
        List<FruitTransaction> expected = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 20));
        String input = "type,fruit,quantity" + System.lineSeparator()
                + "b,banana,20";
        List<FruitTransaction> actual = transactionParser.parse(input);
        Assert.assertEquals("Method should return List<FruitTransaction>: ",
                expected, actual);
    }

    @Test(expected = FruitTransactionException.class)
    public void parse_inputOneLine_notOk() {
        transactionParser.parse("invalidInput");
    }

    @Test(expected = NumberFormatException.class)
    public void parseData_thirdPartIsNotInteger_notOk() {
        String input = "type,fruit,quantity" + System.lineSeparator()
                + "b,banana,one" + System.lineSeparator();
        transactionParser.parse(input);
    }

    @Test(expected = FruitTransactionException.class)
    public void parseData_invalidOperationLetter_notOk() {
        String input = "type,fruit,quantity" + System.lineSeparator()
                + "x,banana,10" + System.lineSeparator();
        transactionParser.parse(input);
    }

    @Test(expected = NullPointerException.class)
    public void parseData_nullInput_notOk() {
        transactionParser.parse(null);
    }

    @Test(expected = FruitTransactionException.class)
    public void parseData_emptyInput_notOk() {
        transactionParser.parse("");
    }

    @Test(expected = FruitTransactionException.class)
    public void parseData_notValidDataInput_notOk() {
        transactionParser.parse("s,smthng,notNumber");
    }
}
