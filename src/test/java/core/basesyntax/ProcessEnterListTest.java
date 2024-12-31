package core.basesyntax;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProcessEnterListTest {
    private ProcessEnterList processEnterList;
    private OperationsStrategyImpl operationsStrategy;
    private Fruits fruits;

    @BeforeEach
    void setUp() {
        fruits = new Fruits();
        operationsStrategy = new OperationsStrategyImpl(fruits);
        processEnterList = new ProcessEnterList(operationsStrategy);
    }

    @Test
    void shouldReturnNotNullListWhenInputListIsNotEmpty() {
        List<String> strings = new ArrayList<>();
        strings.add("b,banana,20");
        strings.add("b,apple,100");

        List<String> newList = processEnterList.workWithEnterList(strings);

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

        List<String> newList = processEnterList.workWithEnterList(strings);
        assertEquals("banana,152", newList.get(0));
        assertEquals("apple,90", newList.get(1));
    }
}