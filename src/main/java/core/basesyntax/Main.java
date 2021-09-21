package core.basesyntax;

import core.basesyntax.dao.CsvFileService;
import core.basesyntax.dao.CsvFileServiceImpl;
import core.basesyntax.service.ActivityStrategy;
import core.basesyntax.service.ActivityStrategyImpl;
import core.basesyntax.service.InputValidator;
import core.basesyntax.service.InputValidatorImpl;
import core.basesyntax.service.ReportService;
import core.basesyntax.service.ReportServiceImpl;
import core.basesyntax.service.activityhandler.ActivityHandler;
import core.basesyntax.service.activityhandler.BalanceActivityHandler;
import core.basesyntax.service.activityhandler.PurchaseActivityHandler;
import core.basesyntax.service.activityhandler.ReturnActivityHandler;
import core.basesyntax.service.activityhandler.SupplyActivityHandler;
import java.util.HashMap;
import java.util.Map;

public class Main {
    private static final String IN_FILE_PATH = "src/main/resources/shopInputTest_OK.csv";
    private static final String OUT_FILE_PATH = "src/main/resources/shopReport.csv";

    public static void main(String[] args) {
        Map<String, ActivityHandler> activityHandlerMap = new HashMap<>();
        activityHandlerMap.put("b", new BalanceActivityHandler());
        activityHandlerMap.put("p", new PurchaseActivityHandler());
        activityHandlerMap.put("r", new ReturnActivityHandler());
        activityHandlerMap.put("s", new SupplyActivityHandler());

        ActivityStrategy strategy = new ActivityStrategyImpl(activityHandlerMap);
        CsvFileService fileDao = new CsvFileServiceImpl();
        InputValidator validator = new InputValidatorImpl();

        ReportService reportService = new ReportServiceImpl(fileDao, validator, strategy);
        reportService.generateReport(IN_FILE_PATH, OUT_FILE_PATH);

        System.out.println("Report has been generated at " + OUT_FILE_PATH);
    }
}

