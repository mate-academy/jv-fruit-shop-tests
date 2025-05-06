package core.basesyntax;

import core.basesyntax.dao.CsvFileWriter;
import core.basesyntax.dao.FileReader;
import core.basesyntax.dao.FileReaderImpl;
import core.basesyntax.dao.FileWriterImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitShopService;
import core.basesyntax.service.FruitTransactionParser;
import core.basesyntax.service.InventoryService;
import core.basesyntax.service.ReportGeneratorService;
import core.basesyntax.strategy.OperationStrategyProvider;

import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Main {
    private static final String INPUT_FILE_NAME = "reportToRead.csv";
    private static final String OUTPUT_FILE_NAME = "finalReport.csv";
    private static final Path OUTPUT_FILE_PATH =
            Paths.get("src", "main", "resources", OUTPUT_FILE_NAME);

    public static void main(String[] args) throws Exception {
        InventoryService inventoryService = new InventoryService();
        OperationStrategyProvider strategyProvider =
                new OperationStrategyProvider(inventoryService);
        FileReader fileReader = new FileReaderImpl();
        FruitTransactionParser parser = new FruitTransactionParser();
        FruitShopService fruitShopService =
                new FruitShopService(inventoryService, strategyProvider);
        CsvFileWriter fileWriter = new FileWriterImpl();
        ReportGeneratorService reportGeneratorService = new ReportGeneratorService();

        URL resource = Main.class.getClassLoader().getResource(INPUT_FILE_NAME);
        if (resource == null) {
            throw new IllegalStateException("File not found in resources: " + INPUT_FILE_NAME);
        }

        String inputFilePath = Paths.get(resource.toURI()).toString();
        List<String> lines = fileReader.readFile(inputFilePath);
        List<FruitTransaction> transactions = parser.parse(lines);
        fruitShopService.processTransactions(transactions);
        String report = reportGeneratorService.generateReport(Storage.inventory);
        fileWriter.writeFile(OUTPUT_FILE_PATH.toString(), report);
    }
}
