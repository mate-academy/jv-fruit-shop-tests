package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.TransactionsGetter;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class TransactionsGetterImplTest {
    private static final String INPUT_STRING = "b,banana,20;b,apple,100;";
    private static final String INVALID_INPUT_STRING = "just different string";
    private static final String FIRST_FRUIT = "banana";
    private static final String SECOND_FRUIT = "apple";
    private static final int FIRST_QUANTITY = 20;
    private static final int SECOND_QUANTITY = 100;
    private static final int FIRST_FRUIT_INDEX = 0;
    private static final int SECOND_FRUIT_INDEX = 1;
    private static TransactionsGetter transactionsGetter;

    @BeforeAll
    static void init() {
        transactionsGetter = new TransactionsGetterImpl();
    }

    @Test
    void getTransactions_validData_isOk() {
        List<FruitTransaction> list = transactionsGetter.getTransactions(INPUT_STRING);
        FruitTransaction firstTransaction = list.get(FIRST_FRUIT_INDEX);
        assertEquals(FIRST_FRUIT, firstTransaction.getFruit());
        assertEquals(FruitTransaction.Operation.BALANCE, firstTransaction.getOperation());
        assertEquals(FIRST_QUANTITY, firstTransaction.getQuantity());
        FruitTransaction secondTransaction = list.get(SECOND_FRUIT_INDEX);
        assertEquals(SECOND_FRUIT, secondTransaction.getFruit());
        assertEquals(FruitTransaction.Operation.BALANCE, secondTransaction.getOperation());
        assertEquals(SECOND_QUANTITY, secondTransaction.getQuantity());
    }

    @Test
    public void setTransactions_invalidData_isNotOk() {
        assertThrows(IllegalArgumentException.class,
                () -> transactionsGetter.getTransactions(INVALID_INPUT_STRING));
    }

    @Test
    public void setTransactions_emptyData_isNotOk() {
        assertThrows(IllegalArgumentException.class,
                () -> transactionsGetter.getTransactions(""));
    }
}
