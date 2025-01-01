package core.basesyntax;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import core.basesyntax.model.Fruit;
import core.basesyntax.services.impl.DataProcessingImpl;
import core.basesyntax.strategy.FruitStrategyImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProcessEnterListTest {
    private DataProcessingImpl processEnterList;
    private FruitStrategyImpl operationsStrategy;
    private Fruit fruits;

    @BeforeEach
    void setUp() {
        fruits = new Fruit();
        operationsStrategy = new FruitStrategyImpl(fruits);
        processEnterList = new DataProcessingImpl(operationsStrategy);
    }

    @Test
    void shouldReturnNotNullListWhenInputListIsNotEmpty() {
        List<String> strings = new ArrayList<>();
        strings.add("b,banana,20");
        strings.add("b,apple,100");

        List<String> newList = processEnterList.processData(strings);

        assertNotNull(newList);
    }

    @Test
    void shouldReturnCorrectListOfResultsWhenInputListIsNotEmpty() {
        List<String> strings = new ArrayList<>();
        strings.add("b,banana,20");
        strings.add("b,apple,100");
        strings.add("s,banana,100");
        strings.add("p,banana,13");
        strings.add("r,apple,10");
        strings.add("p,apple,20");
        strings.add("p,banana,5");
        strings.add("s,banana,50");

        List<String> newList = processEnterList.processData(strings);
        assertEquals("banana,152", newList.get(0));
        assertEquals("apple,90", newList.get(1));
    }
}
