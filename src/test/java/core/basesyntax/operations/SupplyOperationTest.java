package core.basesyntax.operations;

import static core.basesyntax.db.Storage.fruitStorage;
import static org.junit.Assert.assertEquals;

import core.basesyntax.exception.OperationException;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class SupplyOperationTest {
    private static SupplyOperation supplyOperation;

    @BeforeClass
    public static void beforeClass() throws Exception {
        supplyOperation = new SupplyOperation();
    }

    @Before
    public void setUp() throws Exception {
        fruitStorage.put("apple", 20);
    }

    @Test
    public void getSupply_Ok() {
        supplyOperation.action("apple", 10);
        int actual = fruitStorage.get("apple");
        int expected = 30;
        assertEquals(expected, actual);
    }

    @Test(expected = OperationException.class)
    public void getSypplyWithZeroAmount_NotOk() {
        supplyOperation.action("apple", 0);
    }

    @Test(expected = OperationException.class)
    public void getSupplyWithNegativeAmount() {
        supplyOperation.action("apple", -1);
    }

    @Test(expected = OperationException.class)
    public void getSupplyWithUnknownFruit() {
        supplyOperation.action("orange", 10);
    }

    @Test(expected = OperationException.class)
    public void getSupplyWithNullFruitName() {
        supplyOperation.action(null, 10);
    }

    @After
    public void tearDown() throws Exception {
        fruitStorage.clear();
    }
}
