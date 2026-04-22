package strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import dao.impl.FruitDaoImpl;
import db.FruitStorage;
import model.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

public class ReturnOperationTest {
    private final FruitDaoImpl fruitDao = new FruitDaoImpl();
    private final ReturnOperation returnOperation = new ReturnOperation(fruitDao);

    @AfterEach
    void tearDown() {
        FruitStorage.fruits.clear();
    }

    @Test
    void handle_returnFruit_ok() {
        FruitStorage.fruits.put("pear", 5);
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.RETURN, "pear", 10);

        returnOperation.handle(transaction);

        assertEquals(15, FruitStorage.fruits.get("pear"));
    }
}
