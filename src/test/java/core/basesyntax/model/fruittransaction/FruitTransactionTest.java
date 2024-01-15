package core.basesyntax.model.fruittransaction;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FruitTransactionTest {
    private static final String FRUIT = "apple";
    private static final FruitTransaction.Operation OPERATION
            = FruitTransaction.Operation.BALANCE;
    private static final int AMOUNT = 35;
    private static final int INCORRECT_AMOUNT = -1;
    private FruitTransaction fruitTransaction;

    @BeforeEach
    void setUp() {
        this.fruitTransaction = new FruitTransaction();
        this.fruitTransaction.setName(FRUIT);
        this.fruitTransaction.setType(OPERATION);
        this.fruitTransaction.setAmount(AMOUNT);
    }

    @Test
    void fruitShop_nullObject_ok() {
        assertNotNull(fruitTransaction, "The object is not null.");
    }

    @Test
    void fruitShop_nullObject_notOk() {
        this.fruitTransaction = null;
        Assertions.assertNull(null, "The object is null.");
    }

    @Test
    void fruitShop_getName_ok() {
        assertNotNull(fruitTransaction.getName(), "The fruit name is not null.");
    }

    @Test
    void fruitShop_getName_notOk() {
        this.fruitTransaction.setName(null);
        Assertions.assertNull(null, "the name of fruit can't be null!");
    }

    @Test
    void fruitShop_amount_ok() {
        Assertions.assertTrue(fruitTransaction.getAmount() > INCORRECT_AMOUNT);
    }
}
