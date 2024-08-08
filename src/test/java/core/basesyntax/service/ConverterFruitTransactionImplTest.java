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
    private static final int FIRST_FRUIT_TRANSACTION_INDEX = 0;
    private static final int SECOND_FRUIT_TRANSACTION_INDEX = 1;
    private static final String READ_BANANA_TRANSACTION = "b,banana,20";
    private static final String READ_APPLE_TRANSACTION = "b,apple,100";
    private static final String BANANA_FRUIT = "banana";
    private static final String APPLE_FRUIT = "apple";
    private static final int QUANTITY_BANANA = 20;
    private static final int QUANTITY_APPLE = 100;

    private ConverterFruitTransaction converterFruitTransaction;
    private List<String> readFruitTransaction;
    private List<FruitTransaction> fruitTransactions;

    @BeforeEach
    void setUp() {
        converterFruitTransaction = new ConverterFruitTransactionImpl();
        readFruitTransaction = new ArrayList<>();
        readFruitTransaction.add(READ_BANANA_TRANSACTION);
        readFruitTransaction.add(READ_APPLE_TRANSACTION);
        fruitTransactions = new ArrayList<>();
        FruitTransaction banana = new FruitTransaction();
        banana.setOperation(Operation.BALANCE);
        banana.setFruit(BANANA_FRUIT);
        banana.setQuantity(QUANTITY_BANANA);
        fruitTransactions.add(banana);
        FruitTransaction apple = new FruitTransaction();
        apple.setOperation(Operation.BALANCE);
        apple.setFruit(APPLE_FRUIT);
        apple.setQuantity(QUANTITY_APPLE);
        fruitTransactions.add(apple);
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
