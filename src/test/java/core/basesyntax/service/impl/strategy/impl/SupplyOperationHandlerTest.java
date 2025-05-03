package core.basesyntax.service.impl.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.dto.FruitTransactionDto;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.operations.impl.SupplyOperationHandler;
import core.basesyntax.service.strategy.Operation;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SupplyOperationHandlerTest {
    private static final String FRUIT_NAME = "Apple";
    private static final int INITIAL_QUANTITY = 5;
    private static final int SUPPLIED_QUANTITY = 10;

    private SupplyOperationHandler supplyOperationHandler;
    private Storage storage;

    @BeforeEach
    public void setUp() {
        storage = new Storage();
        supplyOperationHandler = new SupplyOperationHandler(storage);
    }

    @Test
    public void apply_SupplyFruit_ok() {
        FruitTransactionDto transaction = new FruitTransactionDto(Operation.SUPPLY, FRUIT_NAME,
                SUPPLIED_QUANTITY);
        supplyOperationHandler.apply(transaction);
        Map<Fruit, Integer> fruits = storage.getFruits();
        Integer actualQuantity = fruits.get(new Fruit(FRUIT_NAME));
        assertEquals(SUPPLIED_QUANTITY, actualQuantity);
    }

    @Test
    public void apply_SupplyExistingFruit_ok() {
        storage.addFruit(new Fruit(FRUIT_NAME), INITIAL_QUANTITY);
        FruitTransactionDto transaction = new FruitTransactionDto(Operation.SUPPLY, FRUIT_NAME,
                SUPPLIED_QUANTITY);
        supplyOperationHandler.apply(transaction);
        Map<Fruit, Integer> fruits = storage.getFruits();
        Integer actualQuantity = fruits.get(new Fruit(FRUIT_NAME));
        assertEquals(INITIAL_QUANTITY + SUPPLIED_QUANTITY, actualQuantity);
    }

    @AfterEach
    public void tearDown() {
        storage.getFruits().clear();
    }
}
