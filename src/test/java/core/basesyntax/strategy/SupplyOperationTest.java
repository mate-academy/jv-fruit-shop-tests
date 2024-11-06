package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.OperationType;
import core.basesyntax.model.ShopTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SupplyOperationTest {
    private static final String APPLE_KEY = "apple";
    private static final int SUPPLY_WEIGHT = 12;
    private static OperationHandler chooseOperation;
    private ShopTransaction validTransaction;
    private ShopTransaction notValidTransaction;

    @BeforeAll
    static void beforeAll() {
        chooseOperation = new SupplyOperation();
    }

    @BeforeEach
    void setUp() {
        validTransaction = new ShopTransaction(OperationType.SUPPLY, APPLE_KEY, 10);
        notValidTransaction = new ShopTransaction(OperationType.SUPPLY, "pineapple", -12);
    }

    @AfterEach
    void tearDown() {
        Storage.fruitShopStorage.clear();
    }

    @Test
    void operationWithValidData_Ok() {
        Storage.fruitShopStorage.put(APPLE_KEY, 14);
        chooseOperation.handle(validTransaction);
        assertEquals(24, Storage.fruitShopStorage.get(APPLE_KEY));
    }

    @Test
    void operationWithNotValidData_NotOk() {
        Integer expected = Storage.fruitShopStorage
                .put(notValidTransaction.getFruitName(),
                notValidTransaction.getWeight());
        Integer actual = Storage.fruitShopStorage
                .put(validTransaction.getFruitName(),
                validTransaction.getWeight());
        assertEquals(expected, actual);
    }

    @Test
    void updateDataAfterSupply_Ok() {
        Storage.fruitShopStorage.put(APPLE_KEY, SUPPLY_WEIGHT);
        Integer actual = Storage.fruitShopStorage.get(APPLE_KEY);
        assertEquals(12, actual);
    }
}
