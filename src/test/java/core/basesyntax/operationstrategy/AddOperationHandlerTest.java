package core.basesyntax.operationstrategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.OperationFruitDto;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class AddOperationHandlerTest {
    private static OperationFruitDto balanceOperation;
    private static OperationFruitDto returnOperation;
    private static OperationFruitDto supplyOperation;
    private static OperationHandler handler;

    @BeforeClass
    public static void beforeClass() {
        balanceOperation = new OperationFruitDto("b", "banana", 57);
        returnOperation = new OperationFruitDto("r", "pineapple", 33);
        supplyOperation = new OperationFruitDto("s", "apple", 123);
        handler = new AddOperationHandler();
    }

    @Before
    public void setUp() {
        Storage.fruits.clear();
    }

    @After
    public void tearDown() {
        Storage.fruits.clear();
    }

    @Test
    public void apply_addTwoFruitToStorage_ok() {
        Fruit banana = new Fruit("banana", 57);
        Fruit pineapple = new Fruit("pineapple", 33);
        List<Fruit> expected = List.of(banana, pineapple);
        handler.apply(balanceOperation);
        handler.apply(returnOperation);
        List<Fruit> actual = Storage.fruits;
        assertEquals(expected, actual);
    }

    @Test
    public void apply_addThreeFruitToStorage_ok() {
        Fruit banana = new Fruit("banana", 57);
        Fruit pineapple = new Fruit("pineapple", 33);
        Fruit apple = new Fruit("apple", 123);
        handler.apply(balanceOperation);
        handler.apply(returnOperation);
        handler.apply(supplyOperation);
        List<Fruit> expected = List.of(banana, pineapple, apple);
        List<Fruit> actual = Storage.fruits;
        assertEquals(expected, actual);
    }

    @Test
    public void apply_addThreeFruitTwiceToStorage_ok() {
        Fruit banana = new Fruit("banana", 114);
        Fruit pineapple = new Fruit("pineapple", 66);
        Fruit apple = new Fruit("apple", 246);
        handler.apply(balanceOperation);
        handler.apply(returnOperation);
        handler.apply(supplyOperation);
        handler.apply(balanceOperation);
        handler.apply(returnOperation);
        handler.apply(supplyOperation);
        List<Fruit> expected = List.of(banana, pineapple, apple);
        List<Fruit> actual = Storage.fruits;
        assertEquals(expected, actual);
    }
}
