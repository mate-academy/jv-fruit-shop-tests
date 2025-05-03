package service;

import java.util.ArrayList;
import java.util.List;
import model.Transaction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class TransactionParserTest {
    private static final List<String> EMPTY_DATA = new ArrayList<>();
    private static final List<String> HEADER_ONLY = List.of("type,fruit,quantity");
    private static TransactionParser parser;

    @BeforeAll
    public static void setUp() {
        parser = new TransactionParserImpl();
    }

    @Test
    void parse_validData_ok() {
        List<String> data = List.of("type,fruit,quantity","b,apple,1");
        Transaction expected = new Transaction(Transaction.Operation.BALANCE,
                "apple", 1);
        List<Transaction> actual = parser.parse(data);
        Assertions.assertEquals(expected, actual.get(0), "Error parsing data " + data);

        data = List.of("type,fruit,quantity","r,apple,1");
        expected = new Transaction(Transaction.Operation.RETURN, "apple", 1);
        actual = parser.parse(data);
        Assertions.assertEquals(expected, actual.get(0), "Error parsing data " + data);

        data = List.of("type,fruit,quantity","s,apple,1");
        expected = new Transaction(Transaction.Operation.SUPPLY, "apple", 1);
        actual = parser.parse(data);
        Assertions.assertEquals(expected, actual.get(0), "Error parsing data " + data);

        data = List.of("type,fruit,quantity","p,apple,1");
        expected = new Transaction(Transaction.Operation.PURCHASE, "apple", 1);
        actual = parser.parse(data);
        Assertions.assertEquals(expected, actual.get(0), "Error parsing data " + data);
    }

    @Test
    void parse_emptyList_notOk() {
        Assertions.assertThrows(RuntimeException.class,
                () -> parser.parse(EMPTY_DATA),
                "RuntimeExceptions should be thrown when parsing empty list");
    }

    @Test
    void parse_fileWithHeaderOnly_notOk() {
        Assertions.assertThrows(RuntimeException.class,
                () -> parser.parse(HEADER_ONLY),
                "RuntimeExceptions should be thrown when parsing list with header only");
    }

    @Test
    void parse_illegalOperation_notOk() {
        List<String> data = List.of("type,fruit,quantity", "t,apple,1");
        Assertions.assertThrows(RuntimeException.class, () -> parser.parse(data),
                "RuntimeException should be thrown if operation is illegal");
    }
}
