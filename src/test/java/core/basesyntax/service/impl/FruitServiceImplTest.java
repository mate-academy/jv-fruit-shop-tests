package core.basesyntax.service.impl;

import static core.basesyntax.model.FruitTransaction.Operation;
import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitService;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.impl.OperationStrategyImpl;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class FruitServiceImplTest {
    private static final OperationStrategy operationStrategy = new OperationStrategyImpl();
    private static final FruitService fruitService = new FruitServiceImpl(operationStrategy);
    private static List<FruitTransaction> inputFruitTransactions;
    private static final List<Fruit> expectedFruits = List.of(
            new Fruit("apple", 90), new Fruit("banana", 152),
            new Fruit("melon", 20), new Fruit("orange", 100));
    @Rule
    public final ExpectedException exceptionRule = ExpectedException.none();

    @Before
    public void setUp() {
        inputFruitTransactions = new ArrayList<>(List.of(
                new FruitTransaction(Operation.BALANCE, new Fruit("banana", 20)),
                new FruitTransaction(Operation.BALANCE, new Fruit("apple", 100)),
                new FruitTransaction(Operation.SUPPLY, new Fruit("banana", 100)),
                new FruitTransaction(Operation.PURCHASE, new Fruit("banana", 13)),
                new FruitTransaction(Operation.RETURN, new Fruit("apple", 10)),
                new FruitTransaction(Operation.PURCHASE, new Fruit("apple", 20)),
                new FruitTransaction(Operation.PURCHASE, new Fruit("banana", 5)),
                new FruitTransaction(Operation.SUPPLY, new Fruit("banana", 50)),
                new FruitTransaction(Operation.SUPPLY, new Fruit("orange", 100)),
                new FruitTransaction(Operation.RETURN, new Fruit("melon", 20))));
    }

    @After
    public void tearDown() {
        inputFruitTransactions.clear();
    }

    @Test
    public void getAll_nullFruitTransactions_NotOk() {
        exceptionRule.expect(NullPointerException.class);
        exceptionRule.expectMessage("FruitTransactions cannot be null");
        fruitService.getAll(null);
    }

    @Test
    public void getAll_nullFruitTransaction_NotOk() {
        inputFruitTransactions.add(null);
        exceptionRule.expect(NullPointerException.class);
        exceptionRule.expectMessage("FruitTransaction cannot be null");
        fruitService.getAll(inputFruitTransactions);
    }

    @Test
    public void getAll_nullFruit_NotOk() {
        inputFruitTransactions.add(new FruitTransaction(Operation.BALANCE, null));
        exceptionRule.expect(NullPointerException.class);
        exceptionRule.expectMessage("Fruit cannot be null");
        fruitService.getAll(inputFruitTransactions);
    }

    @Test
    public void getAll_nullFruitName_NotOk() {
        inputFruitTransactions.add(new FruitTransaction(Operation.BALANCE, new Fruit(null, 10)));
        exceptionRule.expect(NullPointerException.class);
        exceptionRule.expectMessage("Fruit name cannot be null");
        fruitService.getAll(inputFruitTransactions);
    }

    @Test
    public void getAll_validFruitTransactions_Ok() {
        List<Fruit> actualFruits = fruitService.getAll(inputFruitTransactions);
        assertEquals(expectedFruits, actualFruits);
    }

    @Test
    public void getAll_emptyFruitTransaction_Ok() {
        inputFruitTransactions.clear();
        List<Fruit> actualFruits = fruitService.getAll(inputFruitTransactions);
        assertEquals(Collections.emptyList(), actualFruits);
    }

    @Test
    public void getFruitsReport_validFruits_Ok() {
        Storage.fruits.addAll(expectedFruits);
        String expectedReport = "fruit,quantity" + System.lineSeparator()
                + "apple,90" + System.lineSeparator()
                + "banana,152" + System.lineSeparator()
                + "melon,20" + System.lineSeparator() + "orange,100";
        String actualReport = fruitService.getFruitsReport();
        assertEquals(expectedReport, actualReport);
    }

    @Test
    public void getFruitsReport_EmptyFruits_Ok() {
        Storage.fruits.clear();
        String expectedReport = "fruit,quantity";
        String actualReport = fruitService.getFruitsReport();
        assertEquals(expectedReport, actualReport);
    }
}
