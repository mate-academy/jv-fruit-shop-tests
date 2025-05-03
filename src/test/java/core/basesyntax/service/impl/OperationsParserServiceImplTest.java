package core.basesyntax.service.impl;

import static org.junit.Assert.assertArrayEquals;

import core.basesyntax.service.OperationsParserService;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationsParserServiceImplTest {
    private static final List<String> INPUT_LIST = new ArrayList<>();
    private static final List<String[]> EXPECTED_LIST = new ArrayList<>();
    private static OperationsParserService operationsParserService;

    @BeforeClass
    public static void beforeClass() {
        operationsParserService = new OperationsParserServiceImpl();
        INPUT_LIST.add("b,apple,24");
        INPUT_LIST.add("p,apple,21");
        INPUT_LIST.add("r,apple,23");
        EXPECTED_LIST.add(new String[]{"p", "apple", "21"});
        EXPECTED_LIST.add(new String[]{"r", "apple", "23"});
    }

    @Test
    public void parseOperations_Ok() {
        List<String[]> actual = operationsParserService.parseOperations(INPUT_LIST);
        for (int i = 0; i < EXPECTED_LIST.size(); i++) {
            assertArrayEquals(EXPECTED_LIST.get(i), actual.get(i));
        }
    }

    @Test(expected = RuntimeException.class)
    public void parseOperationsWithNullInput() {
        operationsParserService.parseOperations(null);
    }
}
