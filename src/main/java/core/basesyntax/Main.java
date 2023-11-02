package core.basesyntax;

import static core.basesyntax.model.FruitTransaction.OperationType;

import core.basesyntax.dao.InventoryDao;
import core.basesyntax.dao.InventoryDaoImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ReadDataParser;
import core.basesyntax.service.TransactionProcessor;
import core.basesyntax.service.impl.CsvDataReader;
import core.basesyntax.service.impl.CsvDataWriter;
import core.basesyntax.service.impl.CsvReadDataParserImpl;
import core.basesyntax.service.impl.CsvReportGenerator;
import core.basesyntax.service.impl.TransactionProcessorImpl;
import core.basesyntax.strategy.HandlerStrategy;
import core.basesyntax.strategy.handlers.BalanceHandler;
import core.basesyntax.strategy.handlers.PurchaseHandler;
import core.basesyntax.strategy.handlers.ReturnHandler;
import core.basesyntax.strategy.handlers.SupplyHandler;
import java.util.HashMap;
import java.util.List;

public class Main {
    private static final String FILE_FROM = "src/main/resources/Input.txt";
    private static final String FILE_TO = "src/main/resources/Output.txt";

    public static void main(String[] args) {
        InventoryDao inventoryDao = new InventoryDaoImpl();
        final HandlerStrategy strategy = new HandlerStrategy(new HashMap<>());
        strategy.getStrategyMap().put(OperationType.BALANCE, new BalanceHandler(inventoryDao));
        strategy.getStrategyMap().put(OperationType.PURCHASE, new PurchaseHandler(inventoryDao));
        strategy.getStrategyMap().put(OperationType.RETURN, new ReturnHandler(inventoryDao));
        strategy.getStrategyMap().put(OperationType.SUPPLY, new SupplyHandler(inventoryDao));

        CsvDataReader csvDataReader = new CsvDataReader();
        List<String> readData = csvDataReader.readFileLines(FILE_FROM);

        ReadDataParser readDataParser = new CsvReadDataParserImpl();
        List<FruitTransaction> transactionList = readDataParser.convertToFruitTransactionList(
                readData);

        TransactionProcessor transactionProcessor = new TransactionProcessorImpl(strategy);
        for (FruitTransaction transaction : transactionList) {
            transactionProcessor.processTransaction(transaction);
        }

        CsvReportGenerator csvReportGenerator = new CsvReportGenerator();
        String reportString = csvReportGenerator.generateReport();

        CsvDataWriter csvDataWriter = new CsvDataWriter();
        csvDataWriter.writeToFile(FILE_TO, reportString);
    }
}
