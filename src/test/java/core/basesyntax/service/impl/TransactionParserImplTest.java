package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.TransactionParser;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class TransactionParserImplTest {
    private static final Map<String, FruitTransaction> VALID_TRANSACTIONS = Map.of(
            "b,wine,0",
            new FruitTransaction(FruitTransaction.Operation.BALANCE, "wine", 0),
            "b,wine,01",
            new FruitTransaction(FruitTransaction.Operation.BALANCE, "wine", 1),
            "b,apple,10",
            new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 10),
            "s,banana,10",
            new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 10),
            "p,apple,10",
            new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", 10),
            "r,banana,10",
            new FruitTransaction(FruitTransaction.Operation.RETURN, "banana", 10),
            "b,Banana,10",
            new FruitTransaction(FruitTransaction.Operation.BALANCE, "Banana", 10),
            "b,custard apple,10",
            new FruitTransaction(FruitTransaction.Operation.BALANCE, "custard apple", 10),
            "b,bullock's-heart,10",
            new FruitTransaction(FruitTransaction.Operation.BALANCE, "bullock's-heart", 10));
    private static final String HEADER = "operation,fruit,quantity";
    private static final String OPERATION_CODE = "b";
    private static final String PRODUCT_NAME = "apple";
    private static final String QUANTITY = "10";
    private static final String COLUMN_SEPARATOR = ",";
    private static final String[] INVALID_CSV_FORMATS = {
            "b,apple.10", "b ,apple,10", "b, apple,10", "b,apple, 10", "b.apple.10", "b-apple-10"};
    private static final String[] INVALID_OPERATION_CODES = {
            "c", "z", "t", "bb", "ss", "rr", "B", "S", "P", "R"};
    private static final String[] INVALID_PRODUCT_NAMES = {
            "%banana", "apple!", "custard@apple", "banana&"};
    private static final String[] INVALID_QUANTITY_CHARACTERS = {
            "1o", "1 0"};
    private static final String NEGATIVE_QUANTITY = "b,apple,-1";
    private static List<String> transactionList;
    private static TransactionParser parser;
    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @BeforeClass
    public static void init() {
        transactionList = new ArrayList<>();
    }

    @Before
    public void parserInitialization() {
        parser = new TransactionParserImpl();
    }

    @After
    public void tearDown() {
        transactionList.clear();
    }

    @Test
    public void parseTransactions_validDataWithHeader_ok() {
        transactionList.add(HEADER);
        transactionList.addAll(VALID_TRANSACTIONS.keySet());
        List<FruitTransaction> parsedTransactions = parser.parseTransactions(transactionList);
        Iterator<FruitTransaction> iterator = parsedTransactions.iterator();
        for (Map.Entry<String, FruitTransaction> entry : VALID_TRANSACTIONS.entrySet()) {
            assertEquals("Test failed! String \"" + entry.getKey() + "\" is correct",
                    entry.getValue(), iterator.next());
        }
    }

    @Test
    public void parseTransactions_validDataWithoutHeader_ok() {
        transactionList.addAll(VALID_TRANSACTIONS.keySet());
        List<FruitTransaction> parsedTransactions = parser.parseTransactions(transactionList);
        Iterator<FruitTransaction> iterator = parsedTransactions.iterator();
        for (Map.Entry<String, FruitTransaction> entry : VALID_TRANSACTIONS.entrySet()) {
            assertEquals("Test failed! String \"" + entry.getKey() + "\" is correct",
                    entry.getValue(), iterator.next());
        }
    }

    @Test
    public void parseTransactions_listContainsEmptyString_notOk() {
        exception.expect(RuntimeException.class);
        exception.reportMissingExceptionWithMessage("Test failed! List contains empty strings");
        transactionList.add(HEADER);
        transactionList.add(OPERATION_CODE + COLUMN_SEPARATOR + PRODUCT_NAME
                + COLUMN_SEPARATOR + QUANTITY);
        transactionList.add("      ");
        parser.parseTransactions(transactionList);
    }

    @Test
    public void parseTransactions_unsupportedCsvFormat_notOk() {
        exception.expect(RuntimeException.class);
        for (String invalidCsvString : INVALID_CSV_FORMATS) {
            exception.reportMissingExceptionWithMessage(
                    "Test failed! String '" + invalidCsvString
                            + "' has unsupported CSV format. Expected RuntimeException");
            parser.parseTransactions(List.of(invalidCsvString));
        }
    }

    @Test
    public void parseTransactions_invalidOperationCode_notOk() {
        exception.expect(RuntimeException.class);
        for (String invalidCode : INVALID_OPERATION_CODES) {
            exception.reportMissingExceptionWithMessage(
                    "Test failed! Operation code '" + invalidCode + "' is invalid");
            parser.parseTransactions(List.of(
                    invalidCode + COLUMN_SEPARATOR + PRODUCT_NAME + COLUMN_SEPARATOR + QUANTITY));
        }
    }

    @Test
    public void parseTransactions_invalidQuantityValue_notOk() {
        exception.expect(RuntimeException.class);
        for (String invalidQuantity : INVALID_QUANTITY_CHARACTERS) {
            exception.reportMissingExceptionWithMessage(
                    "Test failed! String with product quantity '" + invalidQuantity
                            + "' contains invalid character");
            parser.parseTransactions(List.of(OPERATION_CODE + COLUMN_SEPARATOR
                    + PRODUCT_NAME + COLUMN_SEPARATOR + invalidQuantity));
        }
    }

    @Test
    public void parseTransactions_negativeQuantity_notOk() {
        exception.expect(RuntimeException.class);
        exception.reportMissingExceptionWithMessage("Test failed! Transaction '"
                + NEGATIVE_QUANTITY + "' has negative quantity value. Expected RuntimeException");
        parser.parseTransactions(List.of(NEGATIVE_QUANTITY));
    }

    @Test
    public void parseTransactions_invalidCharacterInProductName_notOk() {
        exception.expect(RuntimeException.class);
        for (String invalidName : INVALID_PRODUCT_NAMES) {
            exception.reportMissingExceptionWithMessage("Test failed! Product name '"
                    + invalidName + "' contains invalid characters");
            parser.parseTransactions(List.of(OPERATION_CODE + COLUMN_SEPARATOR
                    + invalidName + COLUMN_SEPARATOR + QUANTITY));
        }
    }
}
