package core.basesyntax;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.FruitTransaction;
import service.CreateReportService;
import service.FruitShopService;
import service.ReaderService;
import service.TransactionParserService;
import service.WriterService;
import service.impl.CreateReportServiceImpl;
import service.impl.FruitShopServiceImpl;
import service.impl.ReaderServiceImpl;
import service.impl.TransactionParserServiceImpl;
import service.impl.WriterServiceImpl;
import strategy.ActivitiesStrategyImpl;
import strategy.activities.ActivitiesHandler;
import strategy.activities.BalanceHandler;
import strategy.activities.PurchaseHandler;
import strategy.activities.ReturnHandler;
import strategy.activities.SupplyHandler;

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
