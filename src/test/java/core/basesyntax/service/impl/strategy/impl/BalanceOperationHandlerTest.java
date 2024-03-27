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
    private BalanceOperationHandler balanceOperationHandler;
    private Storage storage;

    @BeforeEach
    public void setUp() {
        storage = new Storage();
        balanceOperationHandler = new BalanceOperationHandler(storage);
    }

    @Test
    public void apply_AddToFruitStorage_ok() {
        FruitTransactionDto dto = new FruitTransactionDto(Operation.BALANCE, FRUIT_NAME,
                BALANCE_QUANTITY);
        balanceOperationHandler.apply(dto);
        Map<Fruit, Integer> fruits = storage.getFruits();
        assertEquals(BALANCE_QUANTITY, fruits.getOrDefault(new Fruit(FRUIT_NAME), 0));
    }

    @Test
    public void apply_AddToExistingFruit_ok() {
        int initialQuantity = 5;
        storage.addFruit(new Fruit(FRUIT_NAME), initialQuantity);
        FruitTransactionDto dto = new FruitTransactionDto(Operation.BALANCE, FRUIT_NAME,
                BALANCE_QUANTITY);
        balanceOperationHandler.apply(dto);
        Map<Fruit, Integer> fruits = storage.getFruits();
        assertEquals(initialQuantity + BALANCE_QUANTITY,
                fruits.getOrDefault(new Fruit(FRUIT_NAME), 0));
    }

    @AfterEach
    public void tearDown() {
        storage.getFruits().clear();
    }
}
