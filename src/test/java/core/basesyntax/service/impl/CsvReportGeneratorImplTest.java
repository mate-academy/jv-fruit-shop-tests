package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.CsvReportGenerator;
import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class CsvReportGeneratorImplTest {
    private static final String NULL_DATA_EXCEPTION_MESSAGE = "Data can`t be null";
    private static final Map<String, Integer> VALID_STORAGE_DATA = new HashMap<>();
    private static final Map<String, Integer> EMPTY_STORAGE_DATA = new HashMap<>();
    private static final String EMPTY_STORAGE_DATA_REPORT = "fruit,quantity";
    private static final String EXPECTED_DATA = "fruit,quantity" + System.lineSeparator()
            + "banana,152" + System.lineSeparator()
            + "apple,90";
    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    private CsvReportGenerator<String, Map<String, Integer>> csvReportGenerator;

    @BeforeClass
    public static void beforeClass() {
        VALID_STORAGE_DATA.put("banana", 152);
        VALID_STORAGE_DATA.put("apple", 90);
    }

    @Before
    public void setUp() {
        csvReportGenerator = new CsvReportGeneratorImpl();
    }

    @Test
    public void generateCsvReport_validDataEqualsExpected_ok() {
        String actualValue = csvReportGenerator.generateCsvReport(VALID_STORAGE_DATA);
        assertEquals(EXPECTED_DATA, actualValue);
    }

    @Test
    public void generateCsvReport_withEmptyStorageData_ok() {
        String actualValue = csvReportGenerator.generateCsvReport(EMPTY_STORAGE_DATA);
        assertEquals(EMPTY_STORAGE_DATA_REPORT, actualValue);
    }

    @Test
    public void generateCsvReport_nullDataThrowsException_notOk() {
        expectedException.expect(RuntimeException.class);
        expectedException.expectMessage(NULL_DATA_EXCEPTION_MESSAGE);
        csvReportGenerator.generateCsvReport(null);
    }
}
