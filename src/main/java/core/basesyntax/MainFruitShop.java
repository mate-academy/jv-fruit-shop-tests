package core.basesyntax;

import core.basesyntax.dao.FruitParser;
import core.basesyntax.dao.ShopParser;
import core.basesyntax.dao.impl.FruitParserImpl;
import core.basesyntax.dao.impl.ShopParserImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FileReaderService;
import core.basesyntax.service.FileWriterService;
import core.basesyntax.service.FruitShopService;
import core.basesyntax.service.ReportService;
import core.basesyntax.service.impl.FileReaderServiceImpl;
import core.basesyntax.service.impl.FileWriterServiceImpl;
import core.basesyntax.service.impl.FruitShopServiceImpl;
import core.basesyntax.service.impl.ReportServiceImpl;
import core.basesyntax.strategy.GeneralOperation;
import core.basesyntax.strategy.impl.BalanceHandler;
import core.basesyntax.strategy.impl.PurchaseHandler;
import core.basesyntax.strategy.impl.ReturnHandler;
import core.basesyntax.strategy.impl.SupplyHandler;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainFruitShop {
    private static final String INPUT_PATH = "src/main/resources/InputFile.csv";
    private static final String REPORT_PATH = "src/main/resources/ReportFile.csv";

    public static void main(String[] args) {
        FileReaderService fileReaderService = new FileReaderServiceImpl();
        String dataFromFile = fileReaderService.readFile(INPUT_PATH);

        ShopParser shopParser = new ShopParserImpl();
        String[] parsedData = shopParser.parse(dataFromFile);

        Map<FruitTransaction.Operation, GeneralOperation> operationHandlersMap = new HashMap<>();
        operationHandlersMap.put(FruitTransaction.Operation.BALANCE, new BalanceHandler());
        operationHandlersMap.put(FruitTransaction.Operation.SUPPLY, new SupplyHandler());
        operationHandlersMap.put(FruitTransaction.Operation.PURCHASE, new PurchaseHandler());
        operationHandlersMap.put(FruitTransaction.Operation.RETURN, new ReturnHandler());

        FruitParser parseFruit = new FruitParserImpl();
        List<FruitTransaction> fruitTransactions = parseFruit.parse(parsedData);

        FruitShopService fruitShopService = new FruitShopServiceImpl(operationHandlersMap);
        fruitShopService.transfer(fruitTransactions);

        ReportService reportService = new ReportServiceImpl();
        String report = reportService.getReport();

        FileWriterService fileWriterService = new FileWriterServiceImpl();
        fileWriterService.writeToFile(report, REPORT_PATH);
        System.out.println(fileReaderService.readFile(REPORT_PATH));
    }
}
