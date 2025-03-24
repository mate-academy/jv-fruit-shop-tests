package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashMap;
import core.basesyntax.service.impl.ShopServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class ShopServiceImplTest {

    private ShopServiceImpl shopService;

    @BeforeEach
    void setUp() {
        shopService = new ShopServiceImpl(new HashMap<>());
    }

    @Test
    void shouldAddFruitCorrectly() {
        shopService.addFruit("apple", 10);
        assertEquals(10, shopService.getFruitQuantity("apple"));
    }

    @Test
    void shouldUpdateQuantityWhenFruitAlreadyExists() {
        shopService.addFruit("apple", 10);
        shopService.addFruit("apple", 5);
        assertEquals(15, shopService.getFruitQuantity("apple"));
    }

    @Test
    void shouldThrowExceptionWhenRemovingMoreThanAvailable() {
        shopService.addFruit("apple", 10);
        assertThrows(IllegalArgumentException.class, () -> shopService.removeFruit("apple", 15));
    }
}
