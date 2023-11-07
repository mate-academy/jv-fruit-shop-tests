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
    public static final String FOURTH_FRUIT_NAME = "Lemon";
    public static final int FIRST_FRUIT_QUANTITY = 10;
    public static final int SECOND_FRUIT_QUANTITY = 15;
    public static final int THIRD_FRUIT_QUANTITY = 20;
    public static final int FOURTH_FRUIT_QUANTITY = 25;
    private static final int AMOUNT_OF_FRUIT_TRANSACTIONS = 4;
    public static final String FIRST_FRUIT_NAME = "Apple";
    public static final String SECOND_FRUIT_NAME = "Banana";
    public static final String THIRD_FRUIT_NAME = "Peach";
    public static final int FIRST_FRUIT_INDEX = 0;
    public static final int SECOND_FRUIT_INDEX = 1;
    public static final int THIRD_FRUIT_INDEX = 2;
    public static final int FOURTH_FRUIT_INDEX = 3;
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

        assertEquals(Operation.BALANCE, transactions.get(FIRST_FRUIT_INDEX).getOperation());
        assertEquals(FIRST_FRUIT_NAME, transactions.get(FIRST_FRUIT_INDEX).getFruit());
        assertEquals(FIRST_FRUIT_QUANTITY, transactions.get(FIRST_FRUIT_INDEX).getQuantity());

        assertEquals(Operation.SUPPLY, transactions.get(SECOND_FRUIT_INDEX).getOperation());
        assertEquals(SECOND_FRUIT_NAME, transactions.get(SECOND_FRUIT_INDEX).getFruit());
        assertEquals(SECOND_FRUIT_QUANTITY, transactions.get(SECOND_FRUIT_INDEX).getQuantity());

        assertEquals(Operation.PURCHASE, transactions.get(THIRD_FRUIT_INDEX).getOperation());
        assertEquals(THIRD_FRUIT_NAME, transactions.get(THIRD_FRUIT_INDEX).getFruit());
        assertEquals(THIRD_FRUIT_QUANTITY, transactions.get(THIRD_FRUIT_INDEX).getQuantity());

        assertEquals(Operation.RETURN, transactions.get(FOURTH_FRUIT_INDEX).getOperation());
        assertEquals(FOURTH_FRUIT_NAME, transactions.get(FOURTH_FRUIT_INDEX).getFruit());
        assertEquals(FOURTH_FRUIT_QUANTITY, transactions.get(FOURTH_FRUIT_INDEX).getQuantity());
    }

    @Test
    void parseData_emptyData_ok() {
        data = Collections.emptyList();
        transactions = parser.parseData(data);
        assertTrue(transactions.isEmpty());
    }
}
