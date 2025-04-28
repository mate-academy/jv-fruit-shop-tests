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
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        InventoryService inventoryService = new InventoryService();
        OperationStrategyProvider strategyProvider =
                new OperationStrategyProvider(inventoryService);

        FileReader fileReader = new FileReaderImpl();
        FruitTransactionParser parser = new FruitTransactionParser();
        FruitShopService fruitShopService =
                new FruitShopService(inventoryService, strategyProvider);
        CsvFileWriter fileWriter = new FileWriterImpl();
        ReportGeneratorService reportGeneratorService = new ReportGeneratorService();

        try {
            ClassLoader classLoader = Main.class.getClassLoader();
            String inputFileName = "reportToRead.csv";
            URL resource = classLoader.getResource(inputFileName);
            if (resource == null) {
                throw new RuntimeException("File not found in resources: " + inputFileName);
            }
            String inputFilePath = Paths.get(resource.toURI()).toString();
            List<String> lines = fileReader.readFile(inputFilePath);

            List<FruitTransaction> transactions = parser.parse(lines);
            fruitShopService.processTransactions(transactions);
            String report = reportGeneratorService.generateReport(Storage.inventory);

            String outputFileName = "finalReport.csv";
            Path outputPath = Paths.get("src", "main", "resources", outputFileName);
            fileWriter.writeFile(outputPath.toString(), report);
        } catch (URISyntaxException e) {
            System.err.println("Error forming file path: " + e.getMessage());
        }
    }
}
