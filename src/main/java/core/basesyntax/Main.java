package core.basesyntax;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.CreateReportService;
import core.basesyntax.service.FruitShopService;
import core.basesyntax.service.ReaderService;
import core.basesyntax.service.TransactionParserService;
import core.basesyntax.service.WriterService;
import core.basesyntax.service.impl.CreateReportServiceImpl;
import core.basesyntax.service.impl.FruitShopServiceImpl;
import core.basesyntax.service.impl.ReaderServiceImpl;
import core.basesyntax.service.impl.TransactionParserServiceImpl;
import core.basesyntax.service.impl.WriterServiceImpl;
import core.basesyntax.strategy.ActivitiesStrategyImpl;
import core.basesyntax.strategy.activities.ActivitiesHandler;
import core.basesyntax.strategy.activities.BalanceHandler;
import core.basesyntax.strategy.activities.PurchaseHandler;
import core.basesyntax.strategy.activities.ReturnHandler;
import core.basesyntax.strategy.activities.SupplyHandler;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    private static final String FILE_TO_READ_PATH = "./src/main/resources/database.csv";
    private static final String FILE_TO_WRITE_PATH = "./src/main/resources/report.csv";
    private static Map<String, ActivitiesHandler> activitiesHandlerMap = new HashMap<>();

    private static ReaderService reader = new ReaderServiceImpl();
    private static TransactionParserService transactionParserService
            = new TransactionParserServiceImpl();

    private static FruitShopService fruitShopService = new FruitShopServiceImpl(
            new ActivitiesStrategyImpl(activitiesHandlerMap));

    private static CreateReportService createReportService = new CreateReportServiceImpl();

    private static WriterService writer = new WriterServiceImpl();

    public static void main(String[] args) {
        activitiesHandlerMap.put("b", new BalanceHandler());
        activitiesHandlerMap.put("s", new SupplyHandler());
        activitiesHandlerMap.put("p", new PurchaseHandler());
        activitiesHandlerMap.put("r", new ReturnHandler());

        List<String> inputData = reader.read(FILE_TO_READ_PATH);
        List<FruitTransaction> fruitTransactions
                = transactionParserService.parseInputData(inputData);
        fruitShopService.processData(fruitTransactions);
        String report = createReportService.generateReport();
        writer.write(FILE_TO_WRITE_PATH, report);
    }
}
