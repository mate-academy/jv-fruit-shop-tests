package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FruitServiceImplTest {
    private static FruitServiceImpl fruitService;
    private static Map<String,Integer> expectedRemnants;

    @BeforeAll
    static void beforeAll() {
        Storage.remnantsOfGoods.clear(); ;
        fruitService = new FruitServiceImpl();
        expectedRemnants = new HashMap<>();
    }

    @Test
    void setFruitAndQuantity_Add_Ok() {
        fruitService.setFruit("apple",10);
        expectedRemnants.put("apple", 10);
        assertEquals(expectedRemnants, Storage.remnantsOfGoods);
    }

    @Test
    void addFruitAndQuantity_Add_Ok() {
        fruitService.addFruit("banana", 10);
        expectedRemnants.put("banana", 10);
        assertEquals(expectedRemnants, Storage.remnantsOfGoods);
    }

    @Test
    void addFruitAndQuantityUpdate_Ok() {
        Storage.remnantsOfGoods.put("pineapple", 5);
        fruitService.addFruit("pineapple", 10);
        expectedRemnants.put("pineapple",15);
        assertEquals(expectedRemnants, Storage.remnantsOfGoods);
    }

    @AfterAll
    static void afterAllTest() {
        Storage.remnantsOfGoods.clear();
    }
}
