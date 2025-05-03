package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitTransactionParseService;
import core.basesyntax.service.impl.FruitTransactionParseServiceImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class FruitTransactionParseServiceTest {
    private static FruitTransactionParseService fruitTransactionParseService;
    private static List<String> records;

    @BeforeAll
    static void beforeAll() {
        fruitTransactionParseService = new FruitTransactionParseServiceImpl();
        records = new ArrayList<>();
        records.add("type,fruit,quantity");
        records.add("b,banana,20");
        records.add("b,apple,10");
    }

    @Test
    void parseFruitTransaction_checkSize_Ok() {
        List<FruitTransaction> transactions =
                fruitTransactionParseService.parseFruitTransaction(records);
        assertEquals(2, transactions.size());
    }

    @Test
    void parseFruitTransaction_checkValue_Ok() {
        List<FruitTransaction> transactions =
                fruitTransactionParseService.parseFruitTransaction(records);
        assertEquals(FruitTransaction.Operation.BALANCE, transactions.get(0).getOperation());
        assertEquals("banana", transactions.get(0).getFruit());
        assertEquals(20, transactions.get(0).getQuantity());
    }
}
