package core.basesyntax;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import core.basesyntax.model.Fruit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FruitTest {
    private static final String FIRST_FRUIT_NAME = "banana";
    private static final String SECOND_FRUIT_NAME = "apple";
    private static final int FRUIT_QUANTITY = 10;
    private static final String BALANCE_CODE = "b";
    private static final String SUPPLY_CODE = "s";
    private static final String PURCHASE_CODE = "p";
    private static final String RETURN_CODE = "r";

    private Fruit fruit;

    @BeforeEach
    public void setUp() {
        fruit = new Fruit(Fruit.Operation.BALANCE, SECOND_FRUIT_NAME, FRUIT_QUANTITY);
    }

    @Test
    public void testSetOperation_Success() {
        fruit.setOperation(Fruit.Operation.PURCHASE);
        assertEquals(Fruit.Operation.PURCHASE, fruit.getOperation());
    }

    @Test
    public void testSetFruit_Success() {
        fruit.setFruit(FIRST_FRUIT_NAME);
        assertEquals(FIRST_FRUIT_NAME, fruit.getFruit());
    }

    @Test
    public void testSetQuantity_Success() {
        fruit.setQuantity(FRUIT_QUANTITY);
        assertEquals(FRUIT_QUANTITY, fruit.getQuantity());
    }

    @Test
    public void testGetByCode_Success() {
        assertEquals(Fruit.Operation.BALANCE, Fruit.Operation.getByCode(BALANCE_CODE));
        assertEquals(Fruit.Operation.SUPPLY, Fruit.Operation.getByCode(SUPPLY_CODE));
        assertEquals(Fruit.Operation.PURCHASE, Fruit.Operation.getByCode(PURCHASE_CODE));
        assertEquals(Fruit.Operation.RETURN, Fruit.Operation.getByCode(RETURN_CODE));
    }

    @Test
    public void testGetByCode_InvalidCode() {
        assertThrows(IllegalArgumentException.class,
                () -> Fruit.Operation.getByCode("Invalid operation code"));
    }
}
