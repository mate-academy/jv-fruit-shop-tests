package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.TransactionsGetter;
import java.util.List;
import org.junit.jupiter.api.Test;

class TransactionsGetterImplTest {
    private static final String INPUT_STRING = "b,banana,20;b,apple,100;";
    private static final String INVALID_INPUT_STRING = "just different string";
    private final TransactionsGetter transactionsGetter = new TransactionsGetterImpl();

    @Test
    void getTransactions_validData_isOk() {
        List<FruitTransaction> list = transactionsGetter.getTransactions(INPUT_STRING);
        assertEquals("banana", list.get(0).getFruit());
        assertEquals(FruitTransaction.Operation.BALANCE, list.get(0).getOperation());
        assertEquals(20, list.get(0).getQuantity());
        assertEquals("apple", list.get(1).getFruit());
        assertEquals(FruitTransaction.Operation.BALANCE, list.get(1).getOperation());
        assertEquals(100, list.get(1).getQuantity());
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
