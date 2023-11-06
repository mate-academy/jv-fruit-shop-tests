package core.basesyntax.service.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.validator.FruitQuantityValidatorImpl;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FruitTransactionDataParserImplTest {
    private static final int AMOUNT_OF_FRUIT_TRANSACTIONS = 4;
    private static List<String> data;
    private static List<FruitTransaction> transactions;
    private static DataParser parser;

    @BeforeAll
    static void beforeAll() {
        parser = new FruitTransactionDataParserImpl(new FruitQuantityValidatorImpl());
    }

    @Test
    void parseData_validData_ok() {
        data = List.of("b,Apple,10", "s,Banana,15",
                "p,Peach,20", "r,Lemon,25");
        transactions = parser.parseData(data);

        assertEquals(AMOUNT_OF_FRUIT_TRANSACTIONS, transactions.size());

        assertEquals(Operation.BALANCE, transactions.get(0).getOperation());
        assertEquals("Apple", transactions.get(0).getFruit());
        assertEquals(10, transactions.get(0).getQuantity());

        assertEquals(Operation.SUPPLY, transactions.get(1).getOperation());
        assertEquals("Banana", transactions.get(1).getFruit());
        assertEquals(15, transactions.get(1).getQuantity());

        assertEquals(Operation.PURCHASE, transactions.get(2).getOperation());
        assertEquals("Peach", transactions.get(2).getFruit());
        assertEquals(20, transactions.get(2).getQuantity());

        assertEquals(Operation.RETURN, transactions.get(3).getOperation());
        assertEquals("Lemon", transactions.get(3).getFruit());
        assertEquals(25, transactions.get(3).getQuantity());
    }

    @Test
    void parseData_emptyData_ok() {
        data = Collections.emptyList();
        transactions = parser.parseData(data);
        assertTrue(transactions.isEmpty());
    }
}
