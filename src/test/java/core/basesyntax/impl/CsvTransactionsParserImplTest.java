package core.basesyntax.impl;

import static org.junit.Assert.assertArrayEquals;
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
            {TITLE_IN_CSV, "b,banana,20", "b,apple,100"};
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

    @BeforeClass
    public static void beforeClass() {
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
        csvTransactionsParser = new CsvTransactionsParserImpl();
        invalidFruitTransactions = new ArrayList<>();
        invalidFruitTransactions.add(TITLE_IN_CSV);
    }

    @After
    public void tearDown() {
        invalidFruitTransactions.clear();
    }

    @Test
    public void parseTransactions_returnValidFruitTransactionList_Ok() {
        //arrange
        List<FruitTransaction> expected = validFruitTransactions;

        //act
        List<FruitTransaction> actual = csvTransactionsParser.parseTransactions(transactions);

        //assert
        assertEquals("Array size after parseTransactions:",
                expected.size(), actual.size());
        assertArrayEquals("Incorrect data after parseTransactions:",
                expected.toArray(), actual.toArray());
    }

    @Test(expected = ParseException.class)
    public void parseTransactions_emptyOperationCell_NotOk() {
        assertFailParseTransactions("Empty operation is not allowed: %s",
                INVALID_EMPTY_OPERATION_TRANSACTION);
    }

    @Test(expected = ParseException.class)
    public void parseTransactions_emptyFruitCell_NotOk() {
        assertFailParseTransactions("Empty fruit is not allowed: %s",
                INVALID_EMPTY_FRUIT_TRANSACTION);
    }

    @Test(expected = ParseException.class)
    public void parseTransactions_emptyQuantityCell_NotOk() {
        assertFailParseTransactions("Empty quantity is not allowed: %s",
                INVALID_EMPTY_QUANTITY_TRANSACTION);
    }

    @Test(expected = IllegalArgumentException.class)
    public void parseTransactions_zeroQuantity_NotOk() {
        assertFailParseTransactions("Zero quantity is not allowed: %s",
                INVALID_ZERO_QUANTITY_TRANSACTION);
    }

    @Test(expected = IllegalArgumentException.class)
    public void parseTransactions_negativeQuantity_NotOk() {
        assertFailParseTransactions("Negative quantity is not allowed: %s",
                INVALID_NEGATIVE_QUANTITY_TRANSACTION);
    }

    @Test(expected = IllegalArgumentException.class)
    public void parseTransactions_notIntegerQuantity_NotOk() {
        assertFailParseTransactions("Quantity must be an integer type: %s",
                INVALID_TYPE_OF_QUANTITY_TRANSACTION);
    }

    private void assertFailParseTransactions(String errorPattern, String invalidData) {
        //arrange
        invalidFruitTransactions.add(invalidData);
        String errorMessage = String.format(errorPattern, invalidFruitTransactions);

        //act
        csvTransactionsParser.parseTransactions(invalidFruitTransactions);

        //assert
        fail(errorMessage);
    }
}
