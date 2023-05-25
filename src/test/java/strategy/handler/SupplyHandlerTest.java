package strategy.handler;

import static org.junit.Assert.assertEquals;

import db.Storage;
import model.FruitTransaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import strategy.TransactionHandler;

public class SupplyHandlerTest {
    private static final String FRUIT_FOR_TEST = "banana";
    private static final Integer QUANTITY_OF_FRUITS_IN_STORAGE = 10;
    private static final Integer QUANTITY_OF_FRUITS_TO_SUPPLY = 5;
    private static TransactionHandler supplyHandler;
    private static FruitTransaction supply;

    @Before
    public void setUp() {
        supplyHandler = new SupplyHandler();

        supply = new FruitTransaction();
        supply.setOperation(FruitTransaction.Operation.SUPPLY);
        supply.setFruit(FRUIT_FOR_TEST);
        supply.setQuantity(QUANTITY_OF_FRUITS_TO_SUPPLY);

        Storage.fruits.put(FRUIT_FOR_TEST, QUANTITY_OF_FRUITS_IN_STORAGE);
    }

    @Test
    public void handle_SupplyTransaction_Ok() {
        Integer expected = 15;
        supplyHandler.handle(supply);
        Integer actual = Storage.fruits.get(FRUIT_FOR_TEST);
        assertEquals(expected, actual);
    }

    @After
    public void tearDown() {
        Storage.fruits.clear();
    }
}
