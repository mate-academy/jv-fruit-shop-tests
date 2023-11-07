package core.basesyntax.serviceimpl;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import core.basesyntax.service.ReportMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReportToArrayConverterTest {
    private static ReportMapper reportToArrayConverter;
    private static final String REPORT = "Line 1\nLine 2\nLine 3\n";
    private static final String[] EXPECTED_ARRAY = {"Line 1", "Line 2", "Line 3"};

    @BeforeAll
    static void beforeAll() {
        reportToArrayConverter = new ReportToArrayConverter();
    }

    @Test
    void testPrepareReportForWriting_ValidInput() {
        String[] resultArray = reportToArrayConverter.prepareReportForWriting(REPORT);
        assertArrayEquals(EXPECTED_ARRAY, resultArray);
    }

    @Test
    void testPrepareReportForWriting_EmptyReport() {
        String report = "";
        String[] expectedArray = {""};
        String[] resultArray = reportToArrayConverter.prepareReportForWriting(report);
        assertArrayEquals(expectedArray, resultArray);
    }
}
