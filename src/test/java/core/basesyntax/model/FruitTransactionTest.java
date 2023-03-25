package core.basesyntax.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class FruitTransactionTest {
    private FruitTransaction fruitTransaction;

    @Before
    public void setUp() throws Exception {
        fruitTransaction = new FruitTransaction(FruitTransaction.Operation.RETURN,
                "banana", 43);
    }

    @Test
    public void fruitTransaction_setOperationTest_Ok() {
        fruitTransaction.setOperation(FruitTransaction.Operation.SUPPLY);
        Assert.assertEquals(FruitTransaction.Operation.SUPPLY, fruitTransaction.getOperation());
    }

    @Test
    public void fruitTransaction_setFruitTest_Ok() {
        fruitTransaction.setFruit("apple");
        Assert.assertEquals("apple", fruitTransaction.getFruit());
    }

    @Test
    public void fruitTransaction_setQuantityTest_Ok() {
        fruitTransaction.setQuantity(33);
        Assert.assertEquals(33, fruitTransaction.getQuantity());
    }

    @Test
    public void fruitTransaction_equals_Ok() {
        FruitTransaction expected = new FruitTransaction(FruitTransaction.Operation.RETURN,
                "banana", 43);
        Assert.assertEquals(fruitTransaction, expected);
    }

    @Test
    public void fruitTransaction_hashCode_Ok() {
        FruitTransaction expected = new FruitTransaction(FruitTransaction.Operation.RETURN,
                "banana", 43);
        Assert.assertEquals(fruitTransaction.hashCode(), expected.hashCode());
    }

    @Test
    public void fruitTransaction_operation_notOk() {
        FruitTransaction.Operation type =
                FruitTransaction.Operation.getByCode("g");
        Assert.assertNull(type);
    }
}
