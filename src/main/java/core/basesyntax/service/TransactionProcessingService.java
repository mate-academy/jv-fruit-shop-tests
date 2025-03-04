package core.basesyntax.service;

import static core.basesyntax.Storage.inventory;

import core.basesyntax.dao.CsvFileWriter;
import core.basesyntax.dao.FileReader;
import core.basesyntax.model.FruitTransaction;
import java.util.List;
import java.util.stream.Collectors;

public class TransactionProcessingService {
    private final FileReader fileReader;
    private final FruitTransactionParser parser;
    private final FruitShopService fruitShopService;
    private final CsvFileWriter fileWriter;
    private final ReportGeneratorService reportGeneratorService;
    private InventoryService inventoryService;

    public TransactionProcessingService(FileReader fileReader,
                                        FruitTransactionParser parser,
                                        FruitShopService fruitShopService,
                                        CsvFileWriter fileWriter,
                                        ReportGeneratorService reportGeneratorService) {
        this.fileReader = fileReader;
        this.parser = parser;
        this.fruitShopService = fruitShopService;
        this.fileWriter = fileWriter;
        this.reportGeneratorService = reportGeneratorService;
    }

    public void processTransactions(String sourceFilePath, String targetFilePath) {
        List<FruitTransaction> transactions = readAndParseTransactions(sourceFilePath);
        processTransactions(transactions);
        String reportContent = generateReport();
        writeInventoryToFile(targetFilePath, reportContent);
    }

    private void processTransactions(List<FruitTransaction> transactions) {
        fruitShopService.processTransactions(transactions);
    }

    private List<FruitTransaction> readAndParseTransactions(String sourceFilePath) {
        List<String> lines = fileReader.readFile(sourceFilePath);
        return parser.parse(lines);
    }

    private String generateReport() {
        return inventory.entrySet().stream()
                .map(entry -> entry.getKey() + ": " + entry.getValue())
                .collect(Collectors.joining("\n"));
    }

    private void writeInventoryToFile(String targetFilePath, String reportContent) {
        fileWriter.writeFile(targetFilePath, reportContent);
    }

    public void setInventoryService(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }
}
