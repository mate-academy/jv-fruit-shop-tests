package core.basesyntax.service.impl.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.dto.FruitTransactionDto;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.operations.impl.ReturnOperationHandler;
import core.basesyntax.service.strategy.Operation;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReturnOperationHandlerTest {
    private static final String FRUIT_NAME = "Apple";
    private static final int INITIAL_QUANTITY = 5;
    private static final int RETURNED_QUANTITY = 10;
    private ReturnOperationHandler returnOperationHandler;
    private Storage storage;

    @BeforeEach
    public void setUp() {
        storage = new Storage();
        returnOperationHandler = new ReturnOperationHandler(storage);
    }

    @Test
    public void apply_SupplyFruit_ok() {
        FruitTransactionDto dto = new FruitTransactionDto(Operation.RETURN, FRUIT_NAME,
                RETURNED_QUANTITY);
        returnOperationHandler.apply(dto);
        Map<Fruit, Integer> fruits = storage.getFruits();
        assertEquals(RETURNED_QUANTITY, fruits.getOrDefault(new Fruit(FRUIT_NAME), 0));
    }

    @Test
    public void apply_SupplyExistingFruit_ok() {
        storage.addFruit(new Fruit(FRUIT_NAME), INITIAL_QUANTITY);
        FruitTransactionDto dto = new FruitTransactionDto(Operation.RETURN, FRUIT_NAME,
                RETURNED_QUANTITY);
        returnOperationHandler.apply(dto);
        Map<Fruit, Integer> fruits = storage.getFruits();
        assertEquals(INITIAL_QUANTITY + RETURNED_QUANTITY,
                fruits.getOrDefault(new Fruit(FRUIT_NAME), 0));
    }

    @AfterEach
    public void tearDown() {
        storage.getFruits().clear();
    }
}
