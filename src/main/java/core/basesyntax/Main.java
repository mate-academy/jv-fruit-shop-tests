package core.basesyntax;

import core.basesyntax.dto.ProductDto;
import core.basesyntax.fileservice.CsvParser;
import core.basesyntax.fileservice.CsvReader;
import core.basesyntax.fileservice.CsvWriter;
import core.basesyntax.reportcreator.CsvReporter;
import core.basesyntax.reportcreator.ReportCreator;
import core.basesyntax.storage.Storage;
import core.basesyntax.strategy.OperationStrategy;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        OperationStrategy operationStrategy = new OperationStrategy();
        CsvParser parser = new CsvParser();
        CsvReader csvReader = new CsvReader();
        CsvWriter csvWriter = new CsvWriter();
        ReportCreator csvReporter = new CsvReporter();

        List<String> list = csvReader.readFromFile("shop_operations.csv");
        List<ProductDto> products = parser.parse(list);

        for (ProductDto product : products) {
            operationStrategy.doAction(product);
        }

        csvWriter.writeToFile("report.csv", csvReporter.createReport(Storage.toList()));
    }
}
