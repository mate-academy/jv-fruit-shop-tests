package core.basesyntax.service;

import static core.basesyntax.db.Storage.fruitsMap;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ProcessInputServiceImplTest {
    private static ProcessInputServiceImpl processInputService;

    @BeforeAll
    static void beforeAll() {
        processInputService = new ProcessInputServiceImpl();
        fruitsMap.clear();
    }

    @Test
    void parseInput_correctData_ok() {
        List<String> input = new ArrayList<>();
        input.add("type,fruit,quantity");
        input.add("b,banana,20");
        input.add("b,apple,100");
        input.add("s,banana,100");
        input.add("p,banana,13");
        input.add("r,apple,10");
        input.add("p,apple,20");
        input.add("p,banana,5");
        input.add("s,banana,50");

        processInputService.parseInput(input);
        Map<String, Integer> expected = new HashMap<>();
        expected.put("banana", 152);
        expected.put("apple", 90);
        assertEquals(expected, fruitsMap);
    }

    @Test
    void parseInput_negativeBalance_notOk() {
        List<String> input = new ArrayList<>();
        input.add("type,fruit,quantity");
        input.add("b,banana,20");
        input.add("b,apple,100");
        input.add("s,banana,100");
        input.add("p,banana,130");
        assertThrows(RuntimeException.class,
                () -> processInputService.parseInput(input));
    }
}
