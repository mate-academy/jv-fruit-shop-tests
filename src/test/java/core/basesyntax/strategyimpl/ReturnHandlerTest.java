package core.basesyntax.strategyimpl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.database.FruitStock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReturnHandlerTest {
    private ReturnHandler returnHandler;
    private FruitStock fruitStock;

    @BeforeEach
    void setUp() {
        fruitStock = new FruitStock();
        returnHandler = new ReturnHandler(fruitStock);
    }

    @Test
    void handle_addFruit_correctlyAdds() {
        returnHandler.handle("apple", 10);
        assertEquals(10, fruitStock.getQuantity("apple"));
    }

    @Test
    void handle_addFruit_whenFruitAlreadyExists_correctlyAdds() {
        fruitStock.add("apple", 5);
        returnHandler.handle("apple", 10);
        assertEquals(15, fruitStock.getQuantity("apple"));
    }

    @Test
    void handle_addFruit_whenQuantityIsZeroOrNegative_shouldNotChangeStock() {
        fruitStock.add("apple", 5);
        returnHandler.handle("apple", -5);
        assertEquals(5, fruitStock.getQuantity("apple"));
    }

}
