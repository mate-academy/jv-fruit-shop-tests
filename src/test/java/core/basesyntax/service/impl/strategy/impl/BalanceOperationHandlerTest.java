package core.basesyntax.service.impl.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.dto.FruitTransactionDto;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.operations.impl.BalanceOperationHandler;
import core.basesyntax.service.strategy.Operation;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BalanceOperationHandlerTest {
    private static final String FRUIT_NAME = "Apple";
    private static final int BALANCE_QUANTITY = 10;
    private static final int INITIAL_QUANTITY = 0;
    private BalanceOperationHandler balanceOperationHandler;
    private Storage storage;

    @BeforeEach
    public void setUp() {
        storage = new Storage();
        balanceOperationHandler = new BalanceOperationHandler(storage);
    }

    @Test
    public void apply_AddToFruitStorage_ok() {
        FruitTransactionDto transaction = new FruitTransactionDto(Operation.BALANCE, FRUIT_NAME,
                BALANCE_QUANTITY);
        balanceOperationHandler.apply(transaction);
        Map<Fruit, Integer> fruits = storage.getFruits();
        Integer actualQuantity = fruits.get(new Fruit(FRUIT_NAME));
        assertEquals(BALANCE_QUANTITY, actualQuantity);
    }

    @Test
    public void apply_AddToExistingFruit_ok() {
        storage.addFruit(new Fruit(FRUIT_NAME), INITIAL_QUANTITY);
        FruitTransactionDto transaction = new FruitTransactionDto(Operation.BALANCE, FRUIT_NAME,
                BALANCE_QUANTITY);
        balanceOperationHandler.apply(transaction);
        Map<Fruit, Integer> fruits = storage.getFruits();
        Integer actualQuantity = fruits.get(new Fruit(FRUIT_NAME));
        assertEquals(INITIAL_QUANTITY + BALANCE_QUANTITY, actualQuantity);
    }

    @AfterEach
    public void tearDown() {
        storage.getFruits().clear();
    }
}
