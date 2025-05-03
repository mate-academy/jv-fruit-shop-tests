package core.basesyntax;

import static core.basesyntax.servise.impl.FruitTransaction.Operation.SUPPLY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.servise.impl.FruitTransaction;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FruitTransactionTest {
    private static final String FRUIT = "banana";
    private static final int QUANTITY = 100;
    private static final int NEGATIVE_NUMBER = -10;
    private static final String CODE = "s";
    private static List<String> invalidCode;
    private static List<String> invalidFruit;
    private FruitTransaction actual;

    @BeforeAll
    public static void setUp() {
        invalidCode = List.of("q", "ss", "s7", " s", "s ", " ", "PURCHASE", "7");
        invalidFruit = List.of(" ", "77", "!#", "банан", " banana", "banana ", "BANANA",
                "Banana", "banana10", "b_a_n_a_n_a", "b a n a n a");
    }

    @BeforeEach
    public void beforeTest() {
        actual = new FruitTransaction(CODE,FRUIT,QUANTITY);
    }

    @Test
    public void fruitTransaction_argumentIsNull_notOk() {
        assertThrows(IllegalArgumentException.class,
                () -> new FruitTransaction(null,FRUIT,QUANTITY));
        assertThrows(IllegalArgumentException.class,
                () -> new FruitTransaction(CODE,null,QUANTITY));
    }

    @Test
    public void fruitTransaction_invalidCode_notOk() {
        invalidCode.forEach(code -> assertThrows(IllegalArgumentException.class,
                () -> new FruitTransaction(code,FRUIT,QUANTITY)));
    }

    @Test
    public void fruitTransaction_negativeNumber_notOk() {
        assertThrows(IllegalArgumentException.class,
                () -> new FruitTransaction(CODE,FRUIT,NEGATIVE_NUMBER));
    }

    @Test
    public void fruitTransaction_invalidFruit_notOk() {
        invalidFruit.forEach(fruit -> assertThrows(IllegalArgumentException.class,
                () -> new FruitTransaction(CODE,fruit,QUANTITY)));
    }

    @Test
    public void fruitTransaction_getOperation_Ok() {
        assertEquals(SUPPLY, actual.getOperation());
    }

    @Test
    public void fruitTransaction_getFruit_Ok() {
        assertEquals(FRUIT, actual.getFruit());
    }

    @Test
    public void fruitTransaction_getQuantity_Ok() {
        assertEquals(QUANTITY, actual.getQuantity());
    }
}
