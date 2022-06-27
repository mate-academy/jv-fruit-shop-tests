package core.basesyntax;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.Operation;
import core.basesyntax.service.ParseDataService;
import core.basesyntax.service.ProcessDataService;
import core.basesyntax.service.ReaderService;
import core.basesyntax.service.ReportGeneratingService;
import core.basesyntax.service.ReportWriterService;
import core.basesyntax.service.impl.ParseDataServiceImpl;
import core.basesyntax.service.impl.ProcessDataServiceImpl;
import core.basesyntax.service.impl.ReaderServiceImpl;
import core.basesyntax.service.impl.ReportGeneratingServiceImpl;
import core.basesyntax.service.impl.ReportWriterServiceImpl;
import core.basesyntax.strategy.BalanceOperationService;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.PurchaseOperationService;
import core.basesyntax.strategy.ReturnOperationService;
import core.basesyntax.strategy.SupplyOperationService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    private static final String ACTIVITIES_IN_STORE = "src/main/my-resources/activities.csv";
    private static final String REPORT = "src/main/my-resources/report.csv";

    public static void main(String[] args) {
        Map<Operation, OperationHandler> operationHandler = new HashMap<>();
        operationHandler.put(Operation.BALANCE, new BalanceOperationService());
        operationHandler.put(Operation.SUPPLY, new SupplyOperationService());
        operationHandler.put(Operation.PURCHASE, new PurchaseOperationService());
        operationHandler.put(Operation.RETURN, new ReturnOperationService());
        OperationStrategy.setOperationHandler(operationHandler);
        ReaderService readerService = new ReaderServiceImpl();
        List<String> dataFromFile = readerService.readFile(ACTIVITIES_IN_STORE);
        ParseDataService parseDataService = new ParseDataServiceImpl();
        List<Fruit> parsedValues = parseDataService.parseData(dataFromFile);
        ProcessDataService processDataService = new ProcessDataServiceImpl();
        List<Fruit> db = processDataService.processData(parsedValues);
        ReportGeneratingService reportGeneratingService = new ReportGeneratingServiceImpl();
        List<String> report = reportGeneratingService.createReport(db);
        ReportWriterService reportWriterService = new ReportWriterServiceImpl();
        reportWriterService.writeReport(report, REPORT);
    }
}
