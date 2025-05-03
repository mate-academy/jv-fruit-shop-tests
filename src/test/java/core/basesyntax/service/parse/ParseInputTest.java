package core.basesyntax.service.parse;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class ParseInputTest {
    private static ParseInput parseInput;

    @BeforeClass
    public static void beforeClass() throws Exception {
        parseInput = new ParseInput();
    }

    @Test(expected = RuntimeException.class)
    public void parse_inputListIsEmpty_NotOk() {
        List<String> emptyList = new ArrayList<>();
        parseInput.parse(emptyList);
    }

    @Test
    public void parse_deletedHeader_Ok() {
        String header = "type,fruit,quantity";
        List<String> testData = new ArrayList<>();
        testData.add(header);
        testData.add("b,banana,20");
        List<String> expectedResult = new ArrayList<>();
        expectedResult.add("b,banana,20");
        parseInput.parse(testData);
        assertEquals(testData, expectedResult);
    }

    @Test
    public void parse_isParsedDataCorrect_Ok() {
        String header = "type,fruit,quantity";
        List<String> testData = new ArrayList<>();
        testData.add(header);
        testData.add("b,banana,20");
        testData.add("r,apple,10");
        List<String[]> expectedResult = new ArrayList<>();
        expectedResult.add(new String[] {"b", "banana", "20"});
        expectedResult.add(new String[] {"r", "apple", "10"});
        List<String[]> methodResult = parseInput.parse(testData);
        assertTrue(methodResult.retainAll(expectedResult));
    }

    @Test(expected = RuntimeException.class)
    public void parse_hasLineWithIncorrectData_NotOk() {
        String header = "type,fruit,quantity";
        List<String> testData = new ArrayList<>();
        testData.add(header);
        testData.add("r,apple,10");
        testData.add("b,banana");
        parseInput.parse(testData);
    }
}
