package core.basesyntax;

import core.basesyntax.dao.CsvFileWriter;
import core.basesyntax.dao.FileReader;
import core.basesyntax.dao.FileReaderImpl;
import core.basesyntax.dao.FileWriterImpl;
import core.basesyntax.service.FruitShopService;
import core.basesyntax.service.FruitTransactionParser;
import core.basesyntax.service.InventoryService;
import core.basesyntax.service.ReportGeneratorService;
import core.basesyntax.service.TransactionProcessingService;
import core.basesyntax.strategy.BalanceOperationHandler;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategyProvider;
import core.basesyntax.strategy.ReturnOperationHandler;
import core.basesyntax.strategy.SupplyOperationHandler;
import core.basesyntax.strategy.impl.PurchaseOperationHandler;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        InventoryService inventoryService = new InventoryService();

        Map<String, OperationHandler> operationHandlers = new HashMap<>();
        operationHandlers.put("b", new BalanceOperationHandler());
        operationHandlers.put("p", new PurchaseOperationHandler());
        operationHandlers.put("r", new ReturnOperationHandler());
        operationHandlers.put("s", new SupplyOperationHandler());

        OperationStrategyProvider strategyProvider =
                new OperationStrategyProvider((InventoryService) operationHandlers);

        FileReader fileReader = new FileReaderImpl();
        FruitTransactionParser parser = new FruitTransactionParser();
        FruitShopService fruitShopService =
                new FruitShopService(inventoryService, strategyProvider);
        CsvFileWriter fileWriter = new FileWriterImpl();
        ReportGeneratorService reportGeneratorService = new ReportGeneratorService();

        TransactionProcessingService transactionProcessingService =
                new TransactionProcessingService(
                parser,
                fruitShopService,
                fileWriter,
                reportGeneratorService
        );

        List<String> lines = fileReader.readFile("reportToRead.csv");

        transactionProcessingService.processTransactions(lines, "finalReport.csv");
    }
}
