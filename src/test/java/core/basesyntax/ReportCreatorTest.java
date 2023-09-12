package core.basesyntax;

import core.basesyntax.db.Storage;
import core.basesyntax.service.impl.ReportCreatorImpl;
import core.basesyntax.service.reportcreator.ReportCreator;
import core.basesyntax.strategy.operation.BalanceOperationHandler;
import core.basesyntax.strategy.operation.OperationHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ReportCreatorTest {
    @Test
    void createReportFromStorage_Ok() {
        OperationHandler balanceOperationHandler = new BalanceOperationHandler();
        balanceOperationHandler.processOperation("Banana", 10);
        balanceOperationHandler.processOperation("Apple", 20);
        ReportCreator reportCreator = new ReportCreatorImpl();
        String actualReport = reportCreator.createReport();
        String expectedReport = "fruit,quantity" + System.lineSeparator()
                + "Apple,20" + System.lineSeparator()
                + "Banana,10";
        Assertions.assertEquals(expectedReport, actualReport);
    }

    @AfterEach
    void tearDown() {
        Storage.STOCK.clear();
    }
}
