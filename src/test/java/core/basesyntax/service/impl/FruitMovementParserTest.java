package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitMovement;
import core.basesyntax.model.MovementType;
import core.basesyntax.service.Parser;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitMovementParserTest {
    private static Parser parser;

    @BeforeClass
    public static void setUp() {
        parser = new FruitMovementParser();
    }

    @Test
    public void parse_Null_Ok() {
        assertEquals("You should return an empty List in case of incorrect input.",
                parser.parse(null), Collections.EMPTY_LIST);
    }

    @Test
    public void parse_EmptyList_Ok() {
        assertEquals("You should return an empty List in case of incorrect input.",
                parser.parse(Collections.EMPTY_LIST), Collections.EMPTY_LIST);
    }

    @Test
    public void parse_ListWithOneElement_Ok() {
        List<String[]> request = new ArrayList<>();
        request.add(new String[]{"b", "banana", "20"});
        List<FruitMovement> expected = new ArrayList<>();
        expected.add(new FruitMovement(new Fruit("banana"), MovementType.BALANCE, 20));
        List<FruitMovement> actual = parser.parse(request);
        assertEquals("The fruit is banana",
                expected.get(0).getFruit(), actual.get(0).getFruit());
        assertEquals("The type for b is balance",
                expected.get(0).getType(), actual.get(0).getType());
        assertEquals("The amount must be 20",
                expected.get(0).getAmount(), actual.get(0).getAmount());
    }

    @Test
    public void parse_ListWithManyElements_Ok() {
        MovementType[] movementTypes = {MovementType.BALANCE, MovementType.PURCHASE,
                MovementType.RETURN, MovementType.SUPPLY};
        String[] letters = {"b", "p", "r", "s"};
        List<String[]> request = new ArrayList<>();
        List<FruitMovement> expected = new ArrayList<>();
        int numberOfCounts = 100;
        for (int i = 0; i < numberOfCounts; i++) {
            request.add(new String[]{letters[i % letters.length],
                    String.valueOf(i), String.valueOf(i)});
            expected.add(new FruitMovement(new Fruit(String.valueOf(i)),
                    movementTypes[i % movementTypes.length], i));
        }
        List<FruitMovement> actual = parser.parse(request);
        assertEquals("Incorrect number of elements in this list of fruit movements.",
                expected.size(), actual.size());
        for (int i = 0; i < numberOfCounts; i++) {
            assertEquals("The amount must be " + i,
                    expected.get(i).getAmount(), expected.get(i).getAmount());
            assertEquals("The fruit must be " + i,
                    expected.get(i).getFruit(), expected.get(i).getFruit());
            assertEquals("The type must be " + expected.get(i).getType(),
                    expected.get(i).getType(), expected.get(i).getType());
        }
    }
}
