package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ParseService;
import java.util.ArrayList;
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
    void parse_emptyList_notOk() {
        assertThrows(RuntimeException.class,
                () -> parseService.parse(new ArrayList<>()));
    }

    @Test
    void parse_nullList_notOk() {
        assertThrows(RuntimeException.class,
                () -> parseService.parse(null));
    }

    @Test
    void parse_validData_Ok() {
        List<String> data = new ArrayList<>();
        data.add("type,fruit,quantity");
        data.add("b,banana,50");
        data.add("s,apple,60");
        data.add("p,banana,10");
        List<FruitTransaction> expected = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 50),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "apple", 60),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 10));
        List<FruitTransaction> actual = parseService.parse(data);
        assertEquals(expected, actual);
    }
}

