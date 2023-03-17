package core.basesyntax.service.impl;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import core.basesyntax.service.DataProcessorService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class DataProcessorServiceImplTest {
    private static final List<String> EMPTY_LIST = new ArrayList<>();
    private static final List<String> VALID_DATA_LIST =
            Arrays.asList("b,banana,20", "b,apple,100", "s,banana,100");
    private static final String[] VALID_DATA_STRING_1 = new String[]{"b", "banana", "20"};
    private static final String[] VALID_DATA_STRING_2 = new String[]{"b", "apple", "100"};
    private static final String[] VALID_DATA_STRING_3 = new String[]{"s", "banana", "100"};
    private DataProcessorService dataProcessorService;

    @Before
    public void setUp() {
        dataProcessorService = new DataProcessorServiceImpl();
    }

    @Test
    public void processData_validData_Ok() {
        List<String[]> processedData = dataProcessorService.processData(VALID_DATA_LIST);
        assertEquals(3, processedData.size());
        assertArrayEquals(VALID_DATA_STRING_1, processedData.get(0));
        assertArrayEquals(VALID_DATA_STRING_2, processedData.get(1));
        assertArrayEquals(VALID_DATA_STRING_3, processedData.get(2));
    }

    @Test(expected = RuntimeException.class)
    public void processData_nullData_notOk() {
        dataProcessorService.processData(null);
        fail("Should throw RuntimeException when we try to process null data");
    }

    @Test(expected = RuntimeException.class)
    public void processData_emptyData_notOk() {
        dataProcessorService.processData(EMPTY_LIST);
        fail("Should throw RuntimeException when we try to process null data");
    }
}
