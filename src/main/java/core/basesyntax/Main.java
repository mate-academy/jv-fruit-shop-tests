package core.basesyntax;

import core.basesyntax.model.ActivityType;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FileReaderService;
import core.basesyntax.service.FileWriterService;
import core.basesyntax.service.FruitService;
import core.basesyntax.service.ProcessService;
import core.basesyntax.service.ReportService;
import core.basesyntax.service.impl.FileReaderServiceImpl;
import core.basesyntax.service.impl.FileWriterServiceImpl;
import core.basesyntax.service.impl.FruitServiceImpl;
import core.basesyntax.service.impl.ProcessServiceImpl;
import core.basesyntax.service.impl.ReportServiceImpl;
import core.basesyntax.strategy.OperationsHandler;
import core.basesyntax.strategy.impl.BalanceOperationsHandler;
import core.basesyntax.strategy.impl.PurchaseOperationsHandler;
import core.basesyntax.strategy.impl.ReturnOperationsHandler;
import core.basesyntax.strategy.impl.SupplyOperationsHandler;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        FileReaderService fileReaderService = new FileReaderServiceImpl();
        List<String> textLines = fileReaderService.readFromFileName("src/main/resources/input.csv");

        Map<ActivityType, OperationsHandler> storeOperationsHandlerMap = new HashMap<>();
        storeOperationsHandlerMap
                .put(ActivityType.BALANCE, new BalanceOperationsHandler());
        storeOperationsHandlerMap
                .put(ActivityType.SUPPLY, new SupplyOperationsHandler());
        storeOperationsHandlerMap
                .put(ActivityType.PURCHASE, new PurchaseOperationsHandler());
        storeOperationsHandlerMap
                .put(ActivityType.RETURN, new ReturnOperationsHandler());

        FruitService fruitService = new FruitServiceImpl();
        List<FruitTransaction> fruitTransactions = fruitService.processFruitLines(textLines);

        ProcessService processService = new ProcessServiceImpl(storeOperationsHandlerMap);
        processService.process(fruitTransactions);

        ReportService reportService = new ReportServiceImpl();
        String report = reportService.createReport();

        FileWriterService fileWriterService = new FileWriterServiceImpl();
        fileWriterService.writeToFile(report, "src/main/resources/output.csv");
    }
}
