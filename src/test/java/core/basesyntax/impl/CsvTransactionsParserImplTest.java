package core.basesyntax.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import core.basesyntax.exceptions.ParseException;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.CsvTransactionsParser;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class CsvTransactionsParserImplTest {
    private static CsvTransactionsParser csvTransactionsParser;
    private static List<String> transactions;
    private static List<FruitTransaction> validFruitTransactions;
    private static final String[] VALID_STRING_TRANSACTIONS =
            {"type,fruit,quantity",
                    "b,banana,20",
                    "b,apple,100"};
    private static List<String> invalidFruitTransactions;
    private static final String EMPTY_SCV_TABLE_CELL = "\"\"";
    private static final String[] FIRST_INVALID_OPERATION_TRANSACTIONS =
            {EMPTY_SCV_TABLE_CELL + ",banana,20", EMPTY_SCV_TABLE_CELL + ",apple,80"};
    private static final String[] INVALID_FRUIT_TRANSACTIONS =
            {"b," + EMPTY_SCV_TABLE_CELL + ",20", "b," + EMPTY_SCV_TABLE_CELL + ",100"};
    private static final String[] FIRST_INVALID_QUANTITY_TRANSACTIONS =
            {"b,banana," + EMPTY_SCV_TABLE_CELL, "p,apple," + EMPTY_SCV_TABLE_CELL};
    private static final String[] SECOND_INVALID_QUANTITY_TRANSACTIONS =
            {"b,apple,0", "r,banana,0"};
    private static final String[] THIRD_INVALID_QUANTITY_TRANSACTIONS =
            {"r,banana,-1", "b,apple,-5"};
    private static final String PARSE_EXCEPTION_MESSAGE = ParseException.class.toString();
    private static final String ILLEGAL_ARGUMENT_EXCEPTION_MESSAGE =
            IllegalArgumentException.class.toString();

    @BeforeClass
    public static void beforeClass() {
        csvTransactionsParser = new CsvTransactionsParserImpl();

        transactions = new ArrayList<>();
        transactions.addAll(List.of(VALID_STRING_TRANSACTIONS));

        validFruitTransactions = new ArrayList<>();
        validFruitTransactions.add(new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "banana", 20));
        validFruitTransactions.add(new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "apple", 100));

        invalidFruitTransactions = new ArrayList<>();
    }

    @After
    public void tearDown() {
        invalidFruitTransactions.clear();
    }

    @Test
    public void parseTransactions_returnValidFruitTransactionList_Ok() {
        List<FruitTransaction> expected = validFruitTransactions;
        List<FruitTransaction> actual = csvTransactionsParser.parseTransactions(transactions);

        assertEquals("Test failed! Incorrect array size after method operation. Expected: "
                        + expected.size() + ", but was: " + actual.size(),
                expected.size(), actual.size());
        assertEquals("Test failed! Incorrect data in FruitTransaction list",
                expected.toString(), actual.toString());
    }

    @Test(expected = ParseException.class)
    public void parseTransactions_emptyOperationCell_NotOk() {
        invalidFruitTransactions.addAll(List.of(FIRST_INVALID_OPERATION_TRANSACTIONS));

        csvTransactionsParser.parseTransactions(invalidFruitTransactions);
        fail("You should to throw an exception " + PARSE_EXCEPTION_MESSAGE
                + " if the parser receives an empty operation: " + invalidFruitTransactions);
    }

    @Test(expected = ParseException.class)
    public void parseTransactions_emptyFruitCell_NotOk() {
        invalidFruitTransactions.addAll(List.of(INVALID_FRUIT_TRANSACTIONS));

        csvTransactionsParser.parseTransactions(invalidFruitTransactions);
        fail("You should to throw an exception " + PARSE_EXCEPTION_MESSAGE
                + " if the parser receives an empty fruit: " + invalidFruitTransactions);
    }

    @Test(expected = ParseException.class)
    public void parseTransactions_emptyQuantityCell_NotOk() {
        invalidFruitTransactions.addAll(List.of(FIRST_INVALID_QUANTITY_TRANSACTIONS));

        csvTransactionsParser.parseTransactions(invalidFruitTransactions);
        fail("You should to throw an exception " + PARSE_EXCEPTION_MESSAGE
                + " if the parser receives an empty quantity: " + invalidFruitTransactions);
    }

    @Test(expected = IllegalArgumentException.class)
    public void parseTransactions_zeroQuantity_NotOk() {
        invalidFruitTransactions.addAll(List.of(SECOND_INVALID_QUANTITY_TRANSACTIONS));

        csvTransactionsParser.parseTransactions(invalidFruitTransactions);
        fail("You should to throw an exception " + ILLEGAL_ARGUMENT_EXCEPTION_MESSAGE
                + " if the parser receives an 0 quantity: " + invalidFruitTransactions);
    }

    @Test(expected = IllegalArgumentException.class)
    public void parseTransactions_negativeQuantity_NotOk() {
        invalidFruitTransactions.addAll(List.of(THIRD_INVALID_QUANTITY_TRANSACTIONS));

        csvTransactionsParser.parseTransactions(invalidFruitTransactions);
        fail("You should to throw an exception " + ILLEGAL_ARGUMENT_EXCEPTION_MESSAGE
                + " if the parser receives an negative quantity: " + invalidFruitTransactions);
    }
}
