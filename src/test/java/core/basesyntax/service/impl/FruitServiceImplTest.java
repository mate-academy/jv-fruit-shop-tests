package core.basesyntax.service.impl;

import static core.basesyntax.model.FruitTransaction.Operation;
import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitService;
import core.basesyntax.strategy.QuantityStrategy;
import core.basesyntax.strategy.impl.QuantityStrategyImpl;
import java.util.Collections;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class FruitServiceImplTest {
    private static FruitService fruitService;
    private static final List<FruitTransaction> fruitTransactions = List.of(
            new FruitTransaction(Operation.BALANCE, new Fruit("banana", 20)),
            new FruitTransaction(Operation.BALANCE, new Fruit("apple", 100)),
            new FruitTransaction(Operation.SUPPLY, new Fruit("banana", 100)),
            new FruitTransaction(Operation.PURCHASE, new Fruit("banana", 13)),
            new FruitTransaction(Operation.RETURN, new Fruit("apple", 10)),
            new FruitTransaction(Operation.PURCHASE, new Fruit("apple", 20)),
            new FruitTransaction(Operation.PURCHASE, new Fruit("banana", 5)),
            new FruitTransaction(Operation.SUPPLY, new Fruit("banana", 50)),
            new FruitTransaction(Operation.SUPPLY, new Fruit("orange", 100)),
            new FruitTransaction(Operation.RETURN, new Fruit("melon", 20)));

    @Rule
    public final ExpectedException exceptionRule = ExpectedException.none();

    @BeforeClass
    public static void beforeClass() {
        QuantityStrategy quantityStrategy = new QuantityStrategyImpl();
        fruitService = new FruitServiceImpl(quantityStrategy);
    }

    @Before
    public void setUp() {
        Storage.fruitTransactions.addAll(fruitTransactions);
    }

    @After
    public void tearDown() {
        Storage.fruitTransactions.clear();
    }

    @Test
    public void getFruitBalance_nullFruitTransactionInStorage_NotOk() {
        Storage.fruitTransactions.add(null);
        exceptionRule.expect(RuntimeException.class);
        exceptionRule.expectMessage("FruitTransaction cannot be null");
        fruitService.getFruitBalance();
    }

    @Test
    public void getFruitBalance_nullFruitInStorage_NotOk() {
        Storage.fruitTransactions.add(new FruitTransaction(Operation.BALANCE, null));
        exceptionRule.expect(RuntimeException.class);
        exceptionRule.expectMessage("Fruit cannot be null");
        fruitService.getFruitBalance();
    }

    @Test
    public void getFruitBalance_nullFruitNameInStorage_NotOk() {
        Storage.fruitTransactions.add(new FruitTransaction(Operation.BALANCE, new Fruit(null, 10)));
        exceptionRule.expect(RuntimeException.class);
        exceptionRule.expectMessage("Fruit name cannot be null");
        fruitService.getFruitBalance();
    }

    @Test
    public void getFruitBalance_validFruitTransactionsInStorage_Ok() {
        List<Fruit> expectedFruits = List.of(
                new Fruit("apple", 90), new Fruit("banana", 152),
                new Fruit("melon", 20), new Fruit("orange", 100));
        List<Fruit> actualFruits = fruitService.getFruitBalance();
        assertEquals(expectedFruits, actualFruits);
    }

    @Test
    public void getFruitBalance_emptyFruitTransactionStorage_Ok() {
        Storage.fruitTransactions.clear();
        List<Fruit> expectedFruits = Collections.emptyList();
        List<Fruit> actualFruits = fruitService.getFruitBalance();
        assertEquals(expectedFruits, actualFruits);
    }
}
