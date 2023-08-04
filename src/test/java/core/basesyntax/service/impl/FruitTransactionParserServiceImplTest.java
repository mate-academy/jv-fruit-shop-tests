package core.basesyntax.service.impl;

import core.basesyntax.exceptions.EmptyDataExceptions;
import core.basesyntax.exceptions.OperationNotFoundException;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.TransactionParserService;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

class FruitTransactionParserServiceImplTest {
    private static final String LINE_SEPARATOR = System.lineSeparator();
    private static final String INPUT_DATA =
            "type,fruit,quantity" + LINE_SEPARATOR
            + "b,banana,20" + LINE_SEPARATOR
            + "b,apple,100" + LINE_SEPARATOR
            + "s,banana,100" + LINE_SEPARATOR
            + "p,banana,40" + LINE_SEPARATOR
            + "r,banana,10";
    private static final String INPUT_DATA_WITH_NOT_SUPPORTED_OPERATION
            = "type,fruit,quantity" + LINE_SEPARATOR
            + "k,banana,20" + LINE_SEPARATOR
            + "b,apple,100" + LINE_SEPARATOR
            + "s,banana,100" + LINE_SEPARATOR
            + "p,banana,40" + LINE_SEPARATOR
            + "r,banana,10";

    private static final String INPUT_DATA_EMPTY = "";
    private final TransactionParserService transactionParser
            = new FruitTransactionParserServiceImpl();

    @Test
    void getListOfTransactions_ValidInputData_Ok() {
        List<FruitTransaction> expectedListOfTransactions = new ArrayList<>();
        expectedListOfTransactions.add(new FruitTransaction(Operation.BALANCE, "banana", 20));
        expectedListOfTransactions.add(new FruitTransaction(Operation.BALANCE, "apple", 100));
        expectedListOfTransactions.add(new FruitTransaction(Operation.SUPPLY, "banana", 100));
        expectedListOfTransactions.add(new FruitTransaction(Operation.PURCHASE, "banana", 40));
        expectedListOfTransactions.add(new FruitTransaction(Operation.RETURN, "banana", 10));
        List<FruitTransaction> actual = transactionParser.getListOfTransactions(INPUT_DATA);
        Assert.assertEquals(expectedListOfTransactions, actual);
    }

    @Test
    void getListOfTransactions_NotSupportedOperationType_NotOk() {
        OperationNotFoundException exception = null;
        try {
            transactionParser.getListOfTransactions(INPUT_DATA_WITH_NOT_SUPPORTED_OPERATION);
        } catch (OperationNotFoundException e) {
            exception = e;
        }
        String expected = "Not supported operation type: k";
        Assert.assertEquals(expected, exception.getMessage());
    }

    @Test
    void getListOfTransactions_EmptyData_NotOk() {
        EmptyDataExceptions exception = null;
        try {
            transactionParser.getListOfTransactions(INPUT_DATA_EMPTY);
        } catch (EmptyDataExceptions e) {
            exception = e;
        }
        String expected = "Transactions cannot be created from empty data";
        Assert.assertEquals(expected, exception.getMessage());
    }
}
