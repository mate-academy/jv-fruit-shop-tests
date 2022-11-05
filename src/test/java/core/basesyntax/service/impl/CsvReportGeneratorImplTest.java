package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.CsvReportGenerator;
import java.util.Map;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class CsvReportGeneratorImplTest {
    private static final String NULL_DATA_EXCEPTION_MESSAGE = "Data can`t be null";
    private static final Map<String, Integer> VALID_STORAGE_DATA = Map.of("apple", 90,
            "banana", 152);
    private static final String EXPECTED_DATA = "fruit,quantity" + System.lineSeparator()
            + "banana,152" + System.lineSeparator() + "apple,90";
    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    private CsvReportGenerator<String, Map<String, Integer>> csvReportGenerator;

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
    public void generateCsvReport_nullDataThrowsException_notOk() {
        expectedException.expect(RuntimeException.class);
        csvReportGenerator.generateCsvReport(null);
    }

    @Test
    public void generateCsvReport_nullDataExceptionMessage_Ok() {
        expectedException.expectMessage(NULL_DATA_EXCEPTION_MESSAGE);
        csvReportGenerator.generateCsvReport(null);
    }
}
