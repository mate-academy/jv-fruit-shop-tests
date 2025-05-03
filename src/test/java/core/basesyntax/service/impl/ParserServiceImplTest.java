package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.ParserService;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ParserServiceImplTest {
    private static final int FIRST_TRANSACTION_INDEX = 0;
    private static final int SECOND_TRANSACTION_INDEX = 1;
    private static final int THIRD_TRANSACTION_INDEX = 2;
    private static ParserService parserService;
    private static final List<String> VALID_LIST_LINES = List.of(
            "b,banana,100", "b,apple,200", "s,apple,50");

    @BeforeAll
    public static void beforeAll() {
        parserService = new ParserServiceImpl();
    }

    @Test
    public void parseFruitTransactions_validLines_ok() {
        List<FruitTransaction> fruitTransactionsActual
                = parserService.parseFruitTransactions(VALID_LIST_LINES);

        assertEquals(Operation.BALANCE,
                fruitTransactionsActual.get(FIRST_TRANSACTION_INDEX).getOperation());
        assertEquals("banana",
                fruitTransactionsActual.get(FIRST_TRANSACTION_INDEX).getFruit());
        assertEquals(100,
                fruitTransactionsActual.get(FIRST_TRANSACTION_INDEX).getAmount());

        assertEquals(Operation.BALANCE,
                fruitTransactionsActual.get(SECOND_TRANSACTION_INDEX).getOperation());
        assertEquals("apple",
                fruitTransactionsActual.get(SECOND_TRANSACTION_INDEX).getFruit());
        assertEquals(200,
                fruitTransactionsActual.get(SECOND_TRANSACTION_INDEX).getAmount());

        assertEquals(Operation.SUPPLY,
                fruitTransactionsActual.get(THIRD_TRANSACTION_INDEX).getOperation());
        assertEquals("apple",
                fruitTransactionsActual.get(THIRD_TRANSACTION_INDEX).getFruit());
        assertEquals(50,
                fruitTransactionsActual.get(THIRD_TRANSACTION_INDEX).getAmount());
    }
}
