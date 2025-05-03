package core.basesyntax.serviceparser;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitTransaction;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FruitTransactionParserImplTest {

    private static final String FRUIT_APPLE = "apple";
    private static final String FRUIT_BANANA = "banana";
    private static final int QUANTITY_APPLE = 10;
    private static final int QUANTITY_BANANA = 5;
    private static final int QUANTITY_PURCHASE = 3;
    private static final String OPERATION_BALANCE = "BALANCE";
    private static final String OPERATION_SUPPLY = "SUPPLY";
    private static final String OPERATION_PURCHASE = "PURCHASE";
    private static final String OPERATION_INVALID = "INVALID_OP";
    private static final String QUANTITY_INVALID = "abc";
    private static final String HEADER = "type,fruit,quantity";
    private static final int ZERO = 0;
    private static final int ONE = 1;
    private static final int TWO = 2;
    private static final int THREE = 3;
    private static final String EMPTY_STRING = "";

    private static final String COMMA = ",";

    private FruitTransactionParserImpl parser;

    @BeforeEach
    public void setUp() {
        parser = new FruitTransactionParserImpl();
    }

    @Test
    public void testParse_ValidLines_ok() {
        List<String> lines = Arrays.asList(
                OPERATION_BALANCE + COMMA + FRUIT_APPLE + COMMA + QUANTITY_APPLE,
                OPERATION_SUPPLY + COMMA + FRUIT_BANANA + COMMA + QUANTITY_BANANA,
                OPERATION_PURCHASE + COMMA + FRUIT_APPLE + COMMA + QUANTITY_PURCHASE
        );

        List<FruitTransaction> transactions = parser.parse(lines);

        assertEquals(THREE, transactions.size());
        assertEquals(FruitTransaction.Operation.BALANCE, transactions.get(ZERO).getOperation());
        assertEquals(FRUIT_APPLE, transactions.get(ZERO).getFruit());
        assertEquals(QUANTITY_APPLE, transactions.get(ZERO).getQuantity());

        assertEquals(FruitTransaction.Operation.SUPPLY, transactions.get(ONE).getOperation());
        assertEquals(FRUIT_BANANA, transactions.get(ONE).getFruit());
        assertEquals(QUANTITY_BANANA, transactions.get(ONE).getQuantity());

        assertEquals(FruitTransaction.Operation.PURCHASE, transactions.get(TWO).getOperation());
        assertEquals(FRUIT_APPLE, transactions.get(TWO).getFruit());
        assertEquals(QUANTITY_PURCHASE, transactions.get(TWO).getQuantity());
    }

    @Test
    public void testParse_InvalidOperation_ok() {
        List<String> lines = Arrays.asList(
                OPERATION_BALANCE + COMMA + FRUIT_APPLE + COMMA + QUANTITY_APPLE,
                OPERATION_INVALID + COMMA + FRUIT_BANANA + COMMA + QUANTITY_BANANA
        );

        List<FruitTransaction> transactions = parser.parse(lines);

        assertEquals(ONE, transactions.size());
        assertEquals(FruitTransaction.Operation.BALANCE, transactions.get(ZERO).getOperation());
        assertEquals(FRUIT_APPLE, transactions.get(ZERO).getFruit());
        assertEquals(QUANTITY_APPLE, transactions.get(ZERO).getQuantity());
    }

    @Test
    public void testParse_InvalidQuantity_ok() {
        List<String> lines = Arrays.asList(
                OPERATION_BALANCE + COMMA + FRUIT_APPLE + COMMA + QUANTITY_APPLE,
                OPERATION_SUPPLY + COMMA + FRUIT_BANANA + COMMA + QUANTITY_INVALID
        );

        List<FruitTransaction> transactions = parser.parse(lines);

        assertEquals(ONE, transactions.size());
        assertEquals(FruitTransaction.Operation.BALANCE, transactions.get(ZERO).getOperation());
        assertEquals(FRUIT_APPLE, transactions.get(ZERO).getFruit());
        assertEquals(QUANTITY_APPLE, transactions.get(ZERO).getQuantity());
    }

    @Test
    public void testParse_EmptyLine_ok() {
        List<String> lines = Arrays.asList(
                OPERATION_BALANCE + COMMA + FRUIT_APPLE + COMMA + QUANTITY_APPLE,
                EMPTY_STRING,
                OPERATION_SUPPLY + COMMA + FRUIT_BANANA + COMMA + QUANTITY_BANANA
        );

        List<FruitTransaction> transactions = parser.parse(lines);

        assertEquals(TWO, transactions.size());
        assertEquals(FruitTransaction.Operation.BALANCE, transactions.get(ZERO).getOperation());
        assertEquals(FRUIT_APPLE, transactions.get(ZERO).getFruit());
        assertEquals(QUANTITY_APPLE, transactions.get(ZERO).getQuantity());

        assertEquals(FruitTransaction.Operation.SUPPLY, transactions.get(ONE).getOperation());
        assertEquals(FRUIT_BANANA, transactions.get(ONE).getFruit());
        assertEquals(QUANTITY_BANANA, transactions.get(ONE).getQuantity());
    }

    @Test
    public void testParse_HeaderLine_ok() {
        List<String> lines = Arrays.asList(
                HEADER,
                OPERATION_BALANCE + COMMA + FRUIT_APPLE + COMMA + QUANTITY_APPLE,
                OPERATION_SUPPLY + COMMA + FRUIT_BANANA + COMMA + QUANTITY_BANANA
        );

        List<FruitTransaction> transactions = parser.parse(lines);

        assertEquals(TWO, transactions.size());
        assertEquals(FruitTransaction.Operation.BALANCE, transactions.get(ZERO).getOperation());
        assertEquals(FRUIT_APPLE, transactions.get(ZERO).getFruit());
        assertEquals(QUANTITY_APPLE, transactions.get(ZERO).getQuantity());

        assertEquals(FruitTransaction.Operation.SUPPLY, transactions.get(ONE).getOperation());
        assertEquals(FRUIT_BANANA, transactions.get(ONE).getFruit());
        assertEquals(QUANTITY_BANANA, transactions.get(ONE).getQuantity());
    }
}
