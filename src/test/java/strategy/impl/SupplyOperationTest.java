package strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import dao.impl.FruitDaoImpl;
import db.FruitStorage;
import model.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

public class SupplyOperationTest {
    private final FruitDaoImpl fruitDao = new FruitDaoImpl();
    private final SupplyOperation supplyOperation = new SupplyOperation(fruitDao);

    @AfterEach
    void tearDown() {
        FruitStorage.fruits.clear();
    }

    @Test
    void handle_increaseQuantity_ok() {
        FruitStorage.fruits.put("orange", 10);
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, "orange", 20);

        supplyOperation.handle(transaction);

        assertEquals(30, FruitStorage.fruits.get("orange"));
    }
}
