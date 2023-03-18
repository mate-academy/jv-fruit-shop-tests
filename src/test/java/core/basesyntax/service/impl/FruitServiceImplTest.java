package core.basesyntax.service.impl;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitService;
import org.junit.*;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class FruitServiceImplTest {
    private static final String FRUIT = "apple";
    private static final int INITIAL_QUANTITY = 10;
    private static final int SUPPLY_QUANTITY = 60;
    private static final int NOT_VALID_QUANTITY = 40;
    private static FruitService fruitService;
    private static List<FruitTransaction> fruitTransactions;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @BeforeClass
    public static void beforeClass() throws Exception {
        fruitService = new FruitServiceImpl();
    }

    @Before
    public void setUp() throws Exception {
        fruitTransactions = new ArrayList<>();
        fruitTransactions.add(new FruitTransaction(FruitTransaction.Operation.BALANCE, FRUIT, INITIAL_QUANTITY));
    }

    @Test
    public void calculateTotalQuantity_NotEnoughFruits_NotOk() {
        expectedException.expect(RuntimeException.class);
        expectedException.expectMessage("Not enough "
                + FRUIT + "'s on store to purchase");
        fruitTransactions.add(new FruitTransaction(FruitTransaction.Operation.PURCHASE, FRUIT, NOT_VALID_QUANTITY));
        fruitService.calculateTotalQuantity(fruitTransactions);
    }

    @Test
    public void calculateTotalQuantity_ValidInputs_Ok() {
        fruitTransactions.add(new FruitTransaction(FruitTransaction.Operation.SUPPLY, FRUIT, SUPPLY_QUANTITY));
        Map<String, Integer> expected = Map.of(FRUIT, INITIAL_QUANTITY + SUPPLY_QUANTITY);
        fruitService.calculateTotalQuantity(fruitTransactions);
        assertEquals(expected, FruitStorage.fruitStorage);
    }
}