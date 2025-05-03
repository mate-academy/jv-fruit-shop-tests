package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.TransactionService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class TransactionServiceImplTest {
    private static TransactionService parserService;

    @BeforeAll
    static void beforeAll() {
        parserService = new TransactionServiceImpl();
    }

    @Test
    void parseData_ValidData_Ok() {
        List<String> testData = new ArrayList<>(Arrays.asList(
                "type,fruit,quantity",
                "b,banana,20",
                "s,banana,100",
                "p,banana,13",
                "r,apple,10"));
        List<FruitTransaction> result = parserService.parseTransactions(testData);

        FruitTransaction transaction1 = result.get(0);
        assertEquals(FruitTransaction.Operation.BALANCE,transaction1.getOperation());
        assertEquals("banana",transaction1.getFruit());
        assertEquals(20, transaction1.getQuantity());

        FruitTransaction transaction2 = result.get(1);
        assertEquals(FruitTransaction.Operation.SUPPLY,transaction2.getOperation());
        assertEquals("banana",transaction2.getFruit());
        assertEquals(100, transaction2.getQuantity());

        FruitTransaction transaction3 = result.get(2);
        assertEquals(FruitTransaction.Operation.PURCHASE,transaction3.getOperation());
        assertEquals("banana",transaction3.getFruit());
        assertEquals(13, transaction3.getQuantity());

        FruitTransaction transaction4 = result.get(3);
        assertEquals(FruitTransaction.Operation.RETURN,transaction4.getOperation());
        assertEquals("apple",transaction4.getFruit());
        assertEquals(10, transaction4.getQuantity());
    }

    @Test
    void parseData_withNull_notOk() {
        assertThrows(NullPointerException.class, () -> parserService.parseTransactions(null));
    }
}
