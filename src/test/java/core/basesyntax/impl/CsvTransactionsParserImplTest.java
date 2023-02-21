package core.basesyntax.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import core.basesyntax.exceptions.ParseException;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.CsvTransactionsParser;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class CsvTransactionsParserImplTest {
    private static CsvTransactionsParser csvTransactionsParser;
    private static List<String> transactions;
    private static List<FruitTransaction> validFruitTransactions;
    private static final String TITLE_IN_CSV = "type,fruit,quantity";
    private static final String[] VALID_STRING_TRANSACTIONS =
            {TITLE_IN_CSV,
                    "b,banana,20",
                    "b,apple,100"};
    private static List<String> invalidFruitTransactions;
    private static final String EMPTY_SCV_TABLE_CELL = "\"\"";
    private static final String INVALID_EMPTY_OPERATION_TRANSACTION =
            EMPTY_SCV_TABLE_CELL + ",banana,20";
    private static final String INVALID_EMPTY_FRUIT_TRANSACTION =
            "b," + EMPTY_SCV_TABLE_CELL + ",20";
    private static final String INVALID_EMPTY_QUANTITY_TRANSACTION =
            "b,banana," + EMPTY_SCV_TABLE_CELL;
    private static final String INVALID_ZERO_QUANTITY_TRANSACTION = "b,apple,0";
    private static final String INVALID_NEGATIVE_QUANTITY_TRANSACTION = "r,banana,-1";
    private static final String INVALID_TYPE_OF_QUANTITY_TRANSACTION = "r,banana,banana";
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
    }

    @Before
    public void setUp() {
        invalidFruitTransactions = new ArrayList<>();
        invalidFruitTransactions.add(TITLE_IN_CSV);
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
        invalidFruitTransactions.add(INVALID_EMPTY_OPERATION_TRANSACTION);

        csvTransactionsParser.parseTransactions(invalidFruitTransactions);
        fail("Test failed! The method must throw " + PARSE_EXCEPTION_MESSAGE
                + " if the operation is empty: " + invalidFruitTransactions);
    }

    @Test(expected = ParseException.class)
    public void parseTransactions_emptyFruitCell_NotOk() {
        invalidFruitTransactions.add(INVALID_EMPTY_FRUIT_TRANSACTION);

        csvTransactionsParser.parseTransactions(invalidFruitTransactions);
        fail("Test failed! The method must throw " + PARSE_EXCEPTION_MESSAGE
                + " if the fruit is empty: " + invalidFruitTransactions);
    }

    @Test(expected = ParseException.class)
    public void parseTransactions_emptyQuantityCell_NotOk() {
        invalidFruitTransactions.add(INVALID_EMPTY_QUANTITY_TRANSACTION);

        csvTransactionsParser.parseTransactions(invalidFruitTransactions);
        fail("Test failed! The method must throw " + PARSE_EXCEPTION_MESSAGE
                + " if the quantity is empty: " + invalidFruitTransactions);
    }

    @Test(expected = IllegalArgumentException.class)
    public void parseTransactions_zeroQuantity_NotOk() {
        invalidFruitTransactions.add(INVALID_ZERO_QUANTITY_TRANSACTION);

        csvTransactionsParser.parseTransactions(invalidFruitTransactions);
        fail("Test failed! The method must throw " + ILLEGAL_ARGUMENT_EXCEPTION_MESSAGE
                + " if the quantity is equal to 0: " + invalidFruitTransactions);
    }

    @Test(expected = IllegalArgumentException.class)
    public void parseTransactions_negativeQuantity_NotOk() {
        invalidFruitTransactions.add(INVALID_NEGATIVE_QUANTITY_TRANSACTION);

        csvTransactionsParser.parseTransactions(invalidFruitTransactions);
        fail("Test failed! The method must throw " + ILLEGAL_ARGUMENT_EXCEPTION_MESSAGE
                + " if the quantity is negative number: " + invalidFruitTransactions);
    }

    @Test(expected = IllegalArgumentException.class)
    public void parseTransactions_notIntegerQuantity_NotOk() {
        invalidFruitTransactions.add(INVALID_TYPE_OF_QUANTITY_TRANSACTION);

        csvTransactionsParser.parseTransactions(invalidFruitTransactions);
        fail("Test failed! The method must throw " + ILLEGAL_ARGUMENT_EXCEPTION_MESSAGE
                + " if the quantity is not in the type of an Integer: " + invalidFruitTransactions);
    }
}
