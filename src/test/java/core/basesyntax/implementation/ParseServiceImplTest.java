package core.basesyntax.implementation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.ParseService;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ParseServiceImplTest {
    private static final String TITLE = "type,fruit,quantity";
    private static final String BALANCE_OPERATION = "b,banana,20";
    private static final String SUPPLY_OPERATION = "s,banana,100";
    private static final String RETURN_OPERATION = "r,banana,10";
    private static final String PURCHASE_OPERATION = "p,banana,5";
    private static ParseService parseService;

    @BeforeAll
    static void beforeAll() {
        parseService = new ParseServiceImpl();
    }

    @Test
    void parseStringToTransactionListIsNull_NotOk() {
        assertThrows(RuntimeException.class, () -> parseService.parseStringsToTransactions(null));
    }

    @Test
    void parseStringToTransactionListIsEmpty_NotOk() {
        List<String> transactions = new ArrayList<>();
        assertThrows(RuntimeException.class,
                () -> parseService.parseStringsToTransactions(transactions));
    }

    @Test
    void parseStringToTransactionListWithTitle_NotOk() {
        List<String> transactions = List.of(TITLE);
        assertThrows(RuntimeException.class,
                () -> parseService.parseStringsToTransactions(transactions));
    }

    @Test
    void parseStringToTransactionWrightListSize_Ok() {
        List<String> transactions = List.of(TITLE,
                BALANCE_OPERATION,
                SUPPLY_OPERATION,
                RETURN_OPERATION,
                PURCHASE_OPERATION);
        int expected = transactions.size() - 1;
        assertEquals(expected, parseService.parseStringsToTransactions(transactions).size());
    }
}
