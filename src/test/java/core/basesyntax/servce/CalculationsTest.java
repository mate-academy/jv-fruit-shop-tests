package core.basesyntax.servce;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitMovement;
import core.basesyntax.model.MovementType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.junit.BeforeClass;
import org.junit.Test;

public class CalculationsTest {
    private static Calculations calculations;
    private List<FruitMovement> fruitsMovements;

    @BeforeClass
    public static void setUp() {
        calculations = new Calculations();
    }

    @Test
    public void generateReport_NullData_Ok() {
        assertTrue("You should return an empty map for null input.",
                calculations.generateReport(null).isEmpty());
    }

    @Test
    public void generateReport_EmptyList_Ok() {
        assertTrue("You should return an empty map for an empty list in input.",
                calculations.generateReport(Collections.emptyList()).isEmpty());
    }

    @Test
    public void generateReport_OneListItem_Ok() {
        List<FruitMovement> oneMovement = new ArrayList<>();
        Fruit fruit = new Fruit("fruit");
        oneMovement.add(new FruitMovement(fruit, MovementType.BALANCE, 10));
        Map<Fruit, Integer> actual = calculations.generateReport(oneMovement);
        assertEquals(actual.get(fruit), Integer.valueOf(10));
    }

    @Test
    public void generateReport_OnlyOneFruit_Ok() {
        List<FruitMovement> movements = new ArrayList<>();
        Fruit fruit = new Fruit("fruit");
        movements.add(new FruitMovement(fruit, MovementType.BALANCE, 0));
        movements.add(new FruitMovement(fruit, MovementType.RETURN, 5));
        movements.add(new FruitMovement(fruit, MovementType.SUPPLY, 10));
        movements.add(new FruitMovement(fruit, MovementType.PURCHASE, 7));
        Map<Fruit, Integer> actual = calculations.generateReport(movements);
        assertEquals("For 'b'=0, 'r'=5, 's'=10 and 'p'=7 you should return amount 8.",
                Integer.valueOf(8), actual.get(fruit));

        movements.add(new FruitMovement(fruit, MovementType.PURCHASE, 5));
        movements.add(new FruitMovement(fruit, MovementType.RETURN, 2));
        actual = calculations.generateReport(movements);
        assertEquals("For 'b'=0, 'r'=5, 's'=10, 'p'=7, 'p'=5 and 'r'=2 you should return amount 5.",
                Integer.valueOf(5), actual.get(fruit));
    }

    @Test
    public void generateReport_ManyFruits_Ok() {
        List<FruitMovement> movements = new ArrayList<>();
        Fruit[] fruits = new Fruit[10];
        for (int i = 0; i < fruits.length; i++) {
            fruits[i] = new Fruit(String.valueOf(i));
        }
        for (Fruit fruit : fruits) {
            movements.add(new FruitMovement(fruit, MovementType.BALANCE, 10));
            movements.add(new FruitMovement(fruit, MovementType.PURCHASE, 5));
            movements.add(new FruitMovement(fruit, MovementType.SUPPLY, 3));
            movements.add(new FruitMovement(fruit, MovementType.RETURN, 2));
        }
        Map<Fruit, Integer> actual = calculations.generateReport(movements);
        assertTrue("The Map must contains 10 keys.", actual.keySet().size() == 10);
        assertEquals(Integer.valueOf(10), actual.get(fruits[0]));
        assertEquals(Integer.valueOf(10), actual.get(fruits[9]));
    }
}
