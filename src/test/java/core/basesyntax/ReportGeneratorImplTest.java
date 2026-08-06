package core.basesyntax;

import core.basesyntax.service.ReportGenerator;
import core.basesyntax.service.impl.ReportGeneratorImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ReportGeneratorImplTest {

    @Test
    void getReport_emptyStorage_returnsOnlyHeader() {
        ReportGenerator reportGenerator = new ReportGeneratorImpl();
        String resultingReport = reportGenerator.getReport();
        Assertions.assertEquals("fruit,quantity\n", resultingReport);
    }
}
