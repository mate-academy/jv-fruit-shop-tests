package core.basesyntax;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ShopServiceImplTest {

    private ShopServiceImpl shopService;

    @BeforeEach
    void setUp() {
        shopService = new ShopServiceImpl();
    }

    @Test
    void testAddFruit() {
        shopService.addFruit("apple", 10);
        assertEquals(10, shopService.getFruitQuantity("apple"));
    }
}

class ShopServiceImpl {

    private final Map<String, Integer> fruits;

    public ShopServiceImpl() {
        this.fruits = new HashMap<>();
    }

    public void addFruit(String name, int quantity) {
        fruits.put(name, fruits.getOrDefault(name, 0) + quantity);
    }

    public int getFruitQuantity(String name) {
        return fruits.getOrDefault(name, 0);
    }
}
