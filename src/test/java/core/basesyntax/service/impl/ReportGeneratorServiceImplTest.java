package core.basesyntax.service.impl;

import core.basesyntax.service.ReportGeneratorService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportGeneratorServiceImplTest {
    private static ReportGeneratorService reportGenerator;
    private Map<String, Integer> fruitsAtStorageMap;
    private List<String> reportStrings;

    @BeforeClass
    public static void beforeClass() throws Exception {
        reportGenerator = new ReportGeneratorServiceImpl();
    }

    @Before
    public void setUp() throws Exception {
        fruitsAtStorageMap = new HashMap<>();
        fruitsAtStorageMap.put("banana", 10);
        fruitsAtStorageMap.put("apple", 20);
        reportStrings = List.of("fruits,quantity","banana,10", "apple,20");
    }

    @Test(expected = RuntimeException.class)
    public void generate_inputMapIsNull_notOk() {
        reportGenerator.generate(null);
    }

    @Test
    public void generate_inputValueIsNormal_ok() {
        List<String> actual = reportGenerator.generate(fruitsAtStorageMap);
        List<String> expected = reportStrings;
        Assert.assertEquals(expected, actual);
    }
}
