package core.basesyntax.service.impl;

import static core.basesyntax.Base.APPLE_IN_BALANCE;
import static core.basesyntax.Base.BANANA_IN_BALANCE;
import static core.basesyntax.Base.REPORT_LINE_01;
import static core.basesyntax.Base.REPORT_LINE_02;
import static core.basesyntax.Base.daoInitWithBalanceData;
import static core.basesyntax.Base.daoInitWithNegativeBalanceData;
import static core.basesyntax.model.BaseForModel.APPLE;
import static core.basesyntax.model.BaseForModel.BANANA;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.BaseTest;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.ReportGeneratorService;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

class ReportGeneratorServiceImplTest extends BaseTest {

    @Test
    void generateReport_isOk() {
        daoInitWithBalanceData();
        final ReportGeneratorService reportGeneratorService
                = new ReportGeneratorServiceImpl(operationStrategies);
        final List<String> report = reportGeneratorService.generateReport(DAO);
        String actualLine01 = report.get(1);
        String actualLine02 = report.get(2);
        assertEquals(REPORT_LINE_01, actualLine01,
                actualLine01 + " must be same as " + REPORT_LINE_01);
        assertEquals(REPORT_LINE_02, actualLine02,
                actualLine02 + " must be same as " + REPORT_LINE_02);
    }

    @Test
    void generateReport_nullDao_notOk() {
        daoInitWithBalanceData();
        final ReportGeneratorService reportGeneratorService
                = new ReportGeneratorServiceImpl(operationStrategies);
        assertThrows(NullPointerException.class,
                () -> reportGeneratorService.generateReport(null),
                "Must throws NullPointerException.");
    }

    @Test
    void generateBalance_isOk() {
        daoInitWithBalanceData();
        final ReportGeneratorService reportGeneratorService
                = new ReportGeneratorServiceImpl(operationStrategies);
        final Map<Fruit, Integer> balance = reportGeneratorService.generateBalance(DAO);
        final Integer appleActual = balance.get(APPLE);
        final Integer bananaActual = balance.get(BANANA);
        assertEquals(APPLE_IN_BALANCE, appleActual,
                "Apple quantity must be: " + APPLE_IN_BALANCE);
        assertEquals(BANANA_IN_BALANCE, bananaActual,
                "Banana quantity must be: " + BANANA_IN_BALANCE);
    }

    @Test
    void generateBalance_nullDao_notOk() {
        daoInitWithBalanceData();
        final ReportGeneratorService reportGeneratorService
                = new ReportGeneratorServiceImpl(operationStrategies);
        assertThrows(NullPointerException.class,
                () -> reportGeneratorService.generateBalance(null),
                "Must throws NullPointerException.");
    }

    @Test
    void generateBalance_negative_notOk() {
        daoInitWithNegativeBalanceData();
        final ReportGeneratorService reportGeneratorService
                = new ReportGeneratorServiceImpl(operationStrategies);
        assertThrows(RuntimeException.class,
                () -> reportGeneratorService.generateBalance(DAO),
                "Must throws NullPointerException.");
    }
}
