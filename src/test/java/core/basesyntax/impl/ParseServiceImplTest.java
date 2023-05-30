package core.basesyntax.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.FruitTransaction.Operation;
import core.basesyntax.service.ParseService;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ParseServiceImplTest {
    private static final List<String> data = List.of("b,banana,10");
    private static final String FRUIT = "banana";
    private static final int QUANTITY = 10;
    private ParseService parseService;

    @BeforeEach
    void setUp() {
        parseService = new ParseServiceImpl();
    }

    @Test
    void transaction_null_not_ok() {
        assertThrows(RuntimeException.class,
                () -> parseService.parse(null),
                "Transaction can't be null");
    }

    @Test
    void parse_balanceOperation_success() {
        List<FruitTransaction> actual = parseService.parse(data);
        FruitTransaction transaction = actual.get(0);
        assertEquals(Operation.BALANCE, transaction.getOperation());
        assertEquals(FRUIT, transaction.getFruit());
        assertEquals(QUANTITY, transaction.getQuantity());
    }

    @Test
    void parse_emptyTransactionList_returnsEmptyList() {
        List<String> emptyTransactionList = new ArrayList<>();
        List<FruitTransaction> parsedTransactions = parseService.parse(emptyTransactionList);
        assertEquals(0, parsedTransactions.size());
    }

    @AfterEach
    public void afterEachTest() {
        Storage.dataBase.clear();
    }

}
