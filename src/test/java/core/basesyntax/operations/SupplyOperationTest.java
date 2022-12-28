package core.basesyntax.operations;

import static core.basesyntax.db.Storage.fruitStorage;
import static org.junit.Assert.assertEquals;

import core.basesyntax.exception.OperationException;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class SupplyOperationTest {
    private static final String APPLE_NAME = "apple";
    private static final String ORANGE_NAME = "orange";
    private static final String NULL_NAME = null;
    private static final int BALANCE_AMOUNT = 20;
    private static final int ZERO_AMOUNT = 0;
    private static final int NEGATIVE_AMOUNT = -1;
    private static final int SUPPLY_AMOUNT = 10;
    private static SupplyOperation supplyOperation;

    @BeforeClass
    public static void beforeClass() throws Exception {
        supplyOperation = new SupplyOperation();
    }

    @Before
    public void setUp() throws Exception {
        fruitStorage.put(APPLE_NAME, BALANCE_AMOUNT);
    }

    @Test
    public void getSupply_Ok() {
        supplyOperation.action(APPLE_NAME, SUPPLY_AMOUNT);
        int actual = fruitStorage.get(APPLE_NAME);
        int expected = 30;
        assertEquals(expected, actual);
    }

    @Test(expected = OperationException.class)
    public void getSypplyWithZeroAmount_NotOk() {
        supplyOperation.action(APPLE_NAME, ZERO_AMOUNT);
    }

    @Test(expected = OperationException.class)
    public void getSupplyWithNegativeAmount() {
        supplyOperation.action(APPLE_NAME, NEGATIVE_AMOUNT);
    }

    @Test(expected = OperationException.class)
    public void getSupplyWithUnknownFruit() {
        supplyOperation.action(ORANGE_NAME, SUPPLY_AMOUNT);
    }

    @Test(expected = OperationException.class)
    public void getSupplyWithNullFruitName() {
        supplyOperation.action(NULL_NAME, SUPPLY_AMOUNT);
    }

    @After
    public void tearDown() throws Exception {
        fruitStorage.clear();
    }
}
