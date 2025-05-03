package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.storage.Storage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BalanceOperationHandlerTest {
    private OperationHandler balanceOperationHandler;
    private String fruit;
    private int quantity;

    @BeforeEach
    void setUp() {
        balanceOperationHandler = new BalanceOperationHandler();
        fruit = "apple";
        quantity = 100;
    }

    @Test
    void applyHandler_ValidData_Ok() {
        balanceOperationHandler.apply(fruit, quantity);
        assertEquals(Storage.getFruits().get(fruit), quantity);
    }

    @Test
    void applyHandler_UpdateData_Ok() {
        Storage.getFruits().put(fruit, quantity);
        int newQuantity = 75;
        balanceOperationHandler.apply(fruit, newQuantity);
        assertEquals(newQuantity, Storage.getFruits().get(fruit));
    }

    @AfterEach
    void clearStorage() {
        Storage.getFruits().clear();
    }
}
