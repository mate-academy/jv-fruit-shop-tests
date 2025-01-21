package core.basesyntax;

import core.basesyntax.dao.FileReader;
import core.basesyntax.dao.FileReaderImpl;
import core.basesyntax.dao.FileWriter;
import core.basesyntax.dao.FileWriterImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.OperationType;
import core.basesyntax.service.FruitShopService;
import core.basesyntax.service.ReportGenerator;
import core.basesyntax.service.TransactionParser;
import core.basesyntax.strategy.BalanceOperationHandler;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.PurchaseOperationHandler;
import core.basesyntax.strategy.ReturnOperationHandler;
import core.basesyntax.strategy.SupplyOperationHandler;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    private static final String SOURCE_PATH = "src/main/resources/fruit_shop_transactions";
    private static final String REPORT_PATH = "src/main/resources/report";

    public static void main(String[] args) {
        FileReader fileReader = new FileReaderImpl();
        List<String> lines = fileReader.readFile(SOURCE_PATH);

        Map<OperationType, OperationHandler> operationStrategy = new HashMap<>();
        operationStrategy.put(OperationType.BALANCE, new BalanceOperationHandler());
        operationStrategy.put(OperationType.SUPPLY, new SupplyOperationHandler());
        operationStrategy.put(OperationType.PURCHASE, new PurchaseOperationHandler());
        operationStrategy.put(OperationType.RETURN, new ReturnOperationHandler());

        TransactionParser transactionParser = new TransactionParser();
        List<FruitTransaction> transactions = transactionParser.parseTransactions(lines);
        FruitShopService fruitShopService = new FruitShopService(operationStrategy);
        fruitShopService.processTransactions(transactions);

        Map<String, Integer> inventory = fruitShopService.getInventory();
        ReportGenerator reportGenerator = new ReportGenerator();
        String report = reportGenerator.generateReport(inventory);

        FileWriter fileWriter = new FileWriterImpl();
        fileWriter.writeFile(REPORT_PATH, report);
        System.out.println("Report was successfully written in: " + REPORT_PATH);
    }
}
