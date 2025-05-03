package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.Operation;
import core.basesyntax.model.Transaction;
import core.basesyntax.service.ParseService;
import core.basesyntax.util.CommonUtils;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ParseServiceImplTest {
    private static final List<Transaction> TRANSACTIONS = new ArrayList<>();
    private static final String WRONG_OPERATION_MESSAGE = "Wrong operation for this store l";
    private static final String NULL_DATA_MESSAGE = "Data can't be null";
    private static final String EMPTY_DATA = "";
    private static final String INVALID_ROW = "type,fruit,quantity" + System.lineSeparator()
            + "l,banana,23";

    private ParseService parseService = new ParseServiceImpl();

    @BeforeAll
    static void setUp() {
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(new Transaction(Operation.BALANCE, "banana", 20));
        transactions.add(new Transaction(Operation.BALANCE, "apple", 100));
        transactions.add(new Transaction(Operation.SUPPLY, "banana", 100));
        transactions.add(new Transaction(Operation.PURCHASE, "banana", 13));
        transactions.add(new Transaction(Operation.RETURN, "apple", 10));
        transactions.add(new Transaction(Operation.PURCHASE, "apple", 20));
        transactions.add(new Transaction(Operation.PURCHASE, "banana", 5));
        transactions.add(new Transaction(Operation.SUPPLY, "banana", 50));
        TRANSACTIONS.addAll(transactions);
    }

    @Test
    void parse_validData_ok() {
        List<Transaction> actual = parseService.parse(CommonUtils.getRawData());
        assertEquals(TRANSACTIONS, actual);
    }

    @Test
    void parse_emptyData_returnEmptyList() {
        List<Transaction> invalidData = parseService.parse(EMPTY_DATA);
        assertEquals(List.of(), invalidData);
    }

    @Test
    void parse_wrongOperation_notOk() {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            parseService.parse(INVALID_ROW);
        });
        assertEquals(WRONG_OPERATION_MESSAGE, exception.getMessage());
    }

    @Test
    void parse_nullData_notOk() {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            parseService.parse(null);
        });
        assertEquals(NULL_DATA_MESSAGE, exception.getMessage());
    }
}
