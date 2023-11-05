package core.basesyntax.service;

import static core.basesyntax.db.Storage.storage;
import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitTransaction;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FoodStoreServiceImplTest {

    private static final FoodStoreService foodStoreService = new FoodStoreServiceImpl();
    private static final List<FruitTransaction> fruitTransanctionList = new ArrayList<>();
    private static final Integer EXPECTED_VALUE = 90;
    private static final Integer VALID_VALUE_1 = 100;
    private static final Integer VALID_VALUE_2 = 10;
    private static final String VALID_NAME = "apple";

    @BeforeAll
    static void beforeAll() {
        fruitTransanctionList.add(new FruitTransaction(FruitTransaction
                .Operation.BALANCE,VALID_NAME,VALID_VALUE_1));
        fruitTransanctionList.add(new FruitTransaction(FruitTransaction
                .Operation.PURCHASE,VALID_NAME, VALID_VALUE_2));
    }

    @BeforeEach
    void setUp() {
        storage.clear();
    }

    @Test
    void setFoodStoreServiceValidTransactions_Ok() {
        foodStoreService.processTransactions(fruitTransanctionList);
        assertEquals(EXPECTED_VALUE,storage.get(VALID_NAME));
    }
}
