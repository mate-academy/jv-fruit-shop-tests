package core.basesyntax.service;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.exceptions.IncorrectOperationException;
import core.basesyntax.exceptions.WrongNameException;
import core.basesyntax.exceptions.WrongQuantityException;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.OperationType;
import core.basesyntax.service.impl.ParserServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ParserServiceTest {
    private static ParserService parserService;
    private String transactionLine;

    @BeforeClass
    public static void beforeClass() {
        parserService = new ParserServiceImpl();
    }

    @Before
    public void setUp() {
        FruitStorage.fruits.clear();
    }

    @Test(expected = NullPointerException.class)
    public void parse_NullLine_NotOk() {
        parserService.parseToTransaction(null);
    }

    @Test(expected = IncorrectOperationException.class)
    public void parse_EmptyLine_NotOk() {
        parserService.parseToTransaction("");
    }

    @Test(expected = IncorrectOperationException.class)
    public void parse_WithIncorrectOperationType_NotOk() {
        transactionLine = "z,apple,14";
        parserService.parseToTransaction(transactionLine);
    }

    @Test(expected = NumberFormatException.class)
    public void parse_LineWithWordInsteadOfQuantity_NotOk() {
        transactionLine = "b,apple,pear";
        parserService.parseToTransaction(transactionLine);
    }

    @Test(expected = WrongQuantityException.class)
    public void parse_LineWithZeroQuantity_NotOk() {
        transactionLine = "b,apple,0";
        parserService.parseToTransaction(transactionLine);
    }

    @Test(expected = WrongQuantityException.class)
    public void parse_LineWithNegativeQuantity_NotOk() {
        transactionLine = "b,apple,-4";
        parserService.parseToTransaction(transactionLine);
    }

    @Test(expected = WrongNameException.class)
    public void parse_LineWithUnderscoredName_NotOk() {
        transactionLine = "b,apple_pie,14";
        parserService.parseToTransaction(transactionLine);
    }

    @Test(expected = WrongNameException.class)
    public void parse_LineWithNameContainsNumber_NotOk() {
        transactionLine = "b,apple0,14";
        parserService.parseToTransaction(transactionLine);
    }

    @Test(expected = WrongNameException.class)
    public void parse_LineWithFirstWrongSymbolInFruitName_NotOk() {
        transactionLine = "b,-apple,-14";
        parserService.parseToTransaction(transactionLine);
    }

    @Test
    public void parse_BasicLine_Ok() {
        transactionLine = "b,apple,14";
        FruitTransaction actualTransaction = parserService.parseToTransaction(transactionLine);
        FruitTransaction expectedTransaction =
                new FruitTransaction(OperationType.BALANCE, new Fruit("apple", 14));
        Assert.assertEquals(expectedTransaction, actualTransaction);
    }
}
