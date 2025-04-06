package core.basesyntax.service;

import static core.basesyntax.db.Storage.inventory;

import core.basesyntax.dao.CsvFileWriter;
import core.basesyntax.model.FruitTransaction;
import java.util.List;
import java.util.stream.Collectors;

public class TransactionProcessingService {
    private final FruitTransactionParser parser;
    private final FruitShopService fruitShopService;
    private final CsvFileWriter fileWriter;
    private final ReportGeneratorService reportGeneratorService;
    private InventoryService inventoryService;

    public TransactionProcessingService(FruitTransactionParser parser,
                                        FruitShopService fruitShopService,
                                        CsvFileWriter fileWriter,
                                        ReportGeneratorService reportGeneratorService) {
        this.parser = parser;
        this.fruitShopService = fruitShopService;
        this.fileWriter = fileWriter;
        this.reportGeneratorService = reportGeneratorService;
    }

    public void processTransactions(List<String> lines, String targetFilePath) {
        List<FruitTransaction> transactions = parser.parse(lines);
        processTransactions(transactions);
        String reportContent = generateReport();
        writeInventoryToFile(targetFilePath, reportContent);
    }

    private void processTransactions(List<FruitTransaction> transactions) {
        fruitShopService.processTransactions(transactions);
    }

    protected String generateReport() {
        return inventory.entrySet().stream()
                .map(entry -> entry.getKey() + ": " + entry.getValue())
                .collect(Collectors.joining(System.lineSeparator()));
    }

    private void writeInventoryToFile(String targetFilePath, String reportContent) {
        fileWriter.writeFile(targetFilePath, reportContent);
    }

    public void setInventoryService(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }
}
