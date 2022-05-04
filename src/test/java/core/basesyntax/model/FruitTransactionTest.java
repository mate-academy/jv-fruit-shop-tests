package core.basesyntax.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class FruitTransactionTest {
    private FruitTransaction fruitTransaction;
    private Fruit fruit;

    @Before
    public void setUp() {
        fruit = new Fruit("grapes", 333);
        fruitTransaction = new FruitTransaction(FruitTransaction.Operation.BALANCE, fruit);
    }

    @Test
    public void getOperation_Ok() {
        assertEquals("BALANCE", fruitTransaction.getOperation().name());
    }

    @Test
    public void setOperation_Ok() {
        fruitTransaction.setOperation(FruitTransaction.Operation.SUPPLY);
        assertEquals("SUPPLY", fruitTransaction.getOperation().name());
    }

    @Test
    public void getFruit_Ok() {
        assertEquals(fruit, fruitTransaction.getFruit());
    }

    @Test
    public void setFruit_Ok() {
        fruit.setName("Kivi");
        fruit.setQuantity(22);
        fruitTransaction.setFruit(fruit);
        assertEquals("Kivi", fruitTransaction.getFruit().getName());
    }

    @Test
    public void getOperation_notOk() {
        boolean thrown = false;
        try {
            FruitTransaction.Operation.BALANCE.getOperationFromString("d");
        } catch (RuntimeException e) {
            thrown = true;
        }
        assertTrue(thrown);
    }
}
