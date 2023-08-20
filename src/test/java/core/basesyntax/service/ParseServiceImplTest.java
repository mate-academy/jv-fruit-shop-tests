package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.ParseServiceImpl;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ParseServiceImplTest {
    private static ParseService parseService;

    @BeforeAll
    static void beforeAll() {
        parseService = new ParseServiceImpl();
    }

    @Test
    void parseTransactions_NullData_NotOk() {
        assertThrows(RuntimeException.class, () ->
                parseService.parseTransactions(Collections.emptyList()));
    }

    @Test
    void parseTransactions_EmptyData_NotOk() {
        assertThrows(RuntimeException.class, () ->
                parseService.parseTransactions(null));
    }

    @Test
    void parseTransactions_ValidData_Ok() {
        List<String> data = new ArrayList<>();
        data.add("type,fruit,quantity");
        data.add("b,banana,20");
        data.add("b,apple,100");
        data.add("s,banana,100");
        data.add("p,banana,13");
        data.add("r,apple,10");
        List<FruitTransaction> expected = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE,
                        "banana", new BigDecimal(20)),
                new FruitTransaction(FruitTransaction.Operation.BALANCE,
                        "apple", new BigDecimal(100)),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                        "banana", new BigDecimal(100)),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                        "banana", new BigDecimal(13)),
                new FruitTransaction(FruitTransaction.Operation.RETURN,
                        "apple", new BigDecimal(10)));
        List<FruitTransaction> actual = parseService.parseTransactions(data);
        assertEquals(expected, actual);
    }

}
