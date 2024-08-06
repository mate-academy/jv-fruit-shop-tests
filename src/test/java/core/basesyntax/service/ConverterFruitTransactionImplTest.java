package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.impl.ConverterFruitTransactionImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ConverterFruitTransactionImplTest {
    private static final List<String> readFruitTransaction;

    static {
        readFruitTransaction = new ArrayList<>();
        readFruitTransaction.add("b,banana,20");
        readFruitTransaction.add("b,apple,100");
    }

    private static final List<FruitTransaction> fruitTransactions;

    static {
        fruitTransactions = new ArrayList<>();
        FruitTransaction banana = new FruitTransaction();
        banana.setOperation(Operation.BALANCE);
        banana.setFruit("banana");
        banana.setQuantity(20);
        fruitTransactions.add(banana);
        FruitTransaction apple = new FruitTransaction();
        apple.setOperation(Operation.BALANCE);
        apple.setFruit("apple");
        apple.setQuantity(100);
        fruitTransactions.add(apple);
    }

    private static final int FIRST_FRUIT_TRANSACTION_INDEX = 0;
    private static final int SECOND_FRUIT_TRANSACTION_INDEX = 1;

    private ConverterFruitTransaction converterFruitTransaction;

    @BeforeEach
    void setUp() {
        converterFruitTransaction = new ConverterFruitTransactionImpl();
    }

    @Test
    public void convertToFruitTransaction_readFruitTransactions_ok() {
        List<FruitTransaction> actual = converterFruitTransaction
                .convertToFruitTransaction(readFruitTransaction);
        assertEquals(actual.get(FIRST_FRUIT_TRANSACTION_INDEX).getFruit(),
                fruitTransactions.get(FIRST_FRUIT_TRANSACTION_INDEX).getFruit());
        assertEquals(actual.get(SECOND_FRUIT_TRANSACTION_INDEX).getFruit(),
                fruitTransactions.get(SECOND_FRUIT_TRANSACTION_INDEX).getFruit());
    }

    @Test
    public void convertToFruitTransaction_nullReadFruitTransactions_notOk() {
        assertThrows(NullPointerException.class, () -> converterFruitTransaction
                .convertToFruitTransaction(null));
    }
}
