package core.basesyntax;

import core.basesyntax.model.ActivityType;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FileReaderService;
import core.basesyntax.service.FileWriterService;
import core.basesyntax.service.FruitService;
import core.basesyntax.service.ProcessService;
import core.basesyntax.service.ReportCreatorService;
import core.basesyntax.service.impl.FileReaderServiceImpl;
import core.basesyntax.service.impl.FileWriterServiceImpl;
import core.basesyntax.service.impl.FruitServiceImpl;
import core.basesyntax.service.impl.ProcessServiceImpl;
import core.basesyntax.service.impl.ReportCreatorServiceImpl;
import core.basesyntax.strategy.OperationsHandler;
import core.basesyntax.strategy.impl.BalanceOperationsHandler;
import core.basesyntax.strategy.impl.PurchaseOperationsHandler;
import core.basesyntax.strategy.impl.ReturnOperationsHandler;
import core.basesyntax.strategy.impl.SupplyOperationsHandler;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    private static final String FROM_FILE = "src/main/resources/input.csv";
    private static final String TO_FILE = "src/main/resources/output.csv";

    public static void main(String[] args) {
        FileReaderService fileReaderService = new FileReaderServiceImpl();
        List<String> textLines = fileReaderService.readFromFile(FROM_FILE);

        Map<ActivityType, OperationsHandler> storeOperationsHandlerMap = operationsHandlerMap();

        FruitService fruitService = new FruitServiceImpl();
        List<FruitTransaction> fruitTransactions = fruitService.processFruitLines(textLines);

        ProcessService processService = new ProcessServiceImpl(storeOperationsHandlerMap);
        processService.process(fruitTransactions);

        ReportCreatorService reportCreatorService = new ReportCreatorServiceImpl();
        String report = reportCreatorService.createReport();

        FileWriterService fileWriterService = new FileWriterServiceImpl();
        fileWriterService.writeToFile(report, TO_FILE);
    }

    private static Map<ActivityType, OperationsHandler> operationsHandlerMap() {
        Map<ActivityType, OperationsHandler> map = new HashMap<>();
        map.put(ActivityType.BALANCE, new BalanceOperationsHandler());
        map.put(ActivityType.SUPPLY, new SupplyOperationsHandler());
        map.put(ActivityType.PURCHASE, new PurchaseOperationsHandler());
        map.put(ActivityType.RETURN, new ReturnOperationsHandler());
        return map;
    }
}
