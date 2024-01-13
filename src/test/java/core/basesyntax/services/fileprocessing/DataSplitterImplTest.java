package core.basesyntax.services.fileprocessing;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.services.fileprocessing.DataSplitter;
import core.basesyntax.services.fileprocessing.impl.DataSplitterImpl;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class DataSplitterImplTest {
    private static DataSplitter dataSplitter;
    private static final List<String> RAW_DATA =
            List.of("type,fruit,quantity", "b,banana,20", "b,apple,100", "s,banana,100",
                    "p,banana,13", "r,apple,10", "p,apple,20", "p,banana,5", "s,banana,50");
    private static final String[] OFFSET_DATA = new String[] {"type", "fruit", "quantity"};

    private static final List<String[]> EXPECTED_RESULT =
            List.of(new String[] {"b", "banana", "20"}, new String[] {"b", "apple", "100"},
                    new String[] {"s", "banana", "100"}, new String[] {"p", "banana", "13"},
                    new String[] {"r", "apple", "10"}, new String[] {"p", "apple", "20"},
                    new String[] {"p", "banana", "5"}, new String[] {"s", "banana", "50"});
    private static final List<String[]> UNEXPECTED_RESULT_SWAPPED_NUMBERS_BY_TWO_LINES =
            List.of(new String[] {"b", "banana", "100"}, new String[] {"b", "apple", "20"},
                    new String[] {"s", "banana", "13"}, new String[] {"p", "banana", "100"},
                    new String[] {"r", "apple", "20"}, new String[] {"p", "apple", "10"},
                    new String[] {"p", "banana", "50"}, new String[] {"s", "banana", "5"});

    @BeforeAll
    static void initDataSplitter() {
        dataSplitter = new DataSplitterImpl();
    }

    @AfterAll
    static void closeDataSplitter() {
        dataSplitter = null;
    }

    @Test
    void divideData_skips1line_Ok() {
        List<String[]> actualResult = dataSplitter.divideData(RAW_DATA);
        assertFalse(actualResult.contains(OFFSET_DATA));
    }

    @Test
    void divideData_normalData_Ok() {
        List<String[]> actualResult = dataSplitter.divideData(RAW_DATA);
        assertEquals(EXPECTED_RESULT.size(), actualResult.size());
        assertTrue(assertListEquals(EXPECTED_RESULT, actualResult));
    }

    @Test
    void divideData_badData_notOk() {
        List<String[]> actualResult = dataSplitter.divideData(RAW_DATA);
        assertEquals(EXPECTED_RESULT.size(), actualResult.size());
        assertFalse(assertListEquals(UNEXPECTED_RESULT_SWAPPED_NUMBERS_BY_TWO_LINES, actualResult));
    }

    private boolean assertListEquals(List<String[]> expectedResult, List<String[]> actualResult) {
        for (int arrayPointer = 0; arrayPointer < expectedResult.size(); arrayPointer++) {
            for (int stringPointer = 0; stringPointer < expectedResult.get(arrayPointer).length;
                 stringPointer++) {
                if (!expectedResult.get(arrayPointer)[stringPointer].equals(
                        actualResult.get(arrayPointer)[stringPointer])) {
                    return false;
                }
            }
        }
        return true;
    }
}