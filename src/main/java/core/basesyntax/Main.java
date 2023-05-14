package core.basesyntax;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.ActivityType;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FileReaderService;
import core.basesyntax.service.FileWriterService;
import core.basesyntax.service.FruitService;
import core.basesyntax.service.FruitTransactionParser;
import core.basesyntax.service.ReportService;
import core.basesyntax.service.impl.FileReaderServiceImpl;
import core.basesyntax.service.impl.FileWriterServiceImpl;
import core.basesyntax.service.impl.FruitServiceImpl;
import core.basesyntax.service.impl.FruitTransactionParserImpl;
import core.basesyntax.service.impl.ReportServiceImpl;
import core.basesyntax.strategy.ActivityHandler;
import core.basesyntax.strategy.ActivityStrategy;
import core.basesyntax.strategy.impl.ActivityStrategyImpl;
import core.basesyntax.strategy.impl.BalanceActivityHandler;
import core.basesyntax.strategy.impl.PurchaceActivityHandler;
import core.basesyntax.strategy.impl.ReturnActivityHandler;
import core.basesyntax.strategy.impl.SupplyActivityHandler;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    private static final String FROM_FILE_PATH =
            "src/main/resources/fruit-shop-activities.csv";
    private static final String TO_FILE_PATH =
            "src/main/resources/fruit-shop-report.csv";

    public static void main(String[] args) {
        Map<ActivityType, ActivityHandler> activityHandlerMap = new HashMap<>();
        activityHandlerMap.put(
                ActivityType.BALANCE, new BalanceActivityHandler());
        activityHandlerMap.put(
                ActivityType.SUPPLY, new SupplyActivityHandler());
        activityHandlerMap.put(
                ActivityType.PURCHASE, new PurchaceActivityHandler());
        activityHandlerMap.put(
                ActivityType.RETURN, new ReturnActivityHandler());

        FileReaderService fileReader = new FileReaderServiceImpl();
        FruitTransactionParser dataParser = new FruitTransactionParserImpl();
        ActivityStrategy activityStrategy = new ActivityStrategyImpl(activityHandlerMap);
        FruitService fruitService = new FruitServiceImpl(activityStrategy);
        ReportService reportService = new ReportServiceImpl();
        FileWriterService fileWriter = new FileWriterServiceImpl();

        List<String> inputLines = fileReader.readFromFile(FROM_FILE_PATH);
        List<FruitTransaction> transactionList = dataParser.parse(inputLines);
        fruitService.processTransaction(transactionList);
        String report = reportService.createReport(FruitStorage.fruitStorage);
        fileWriter.writeToFile(TO_FILE_PATH, report);
        System.out.println(report);
    }
}
