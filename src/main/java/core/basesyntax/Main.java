package core.basesyntax;

import core.basesyntax.model.Transaction;
import core.basesyntax.service.CreateReportService;
import core.basesyntax.service.ParseService;
import core.basesyntax.service.Reader;
import core.basesyntax.service.Writer;
import core.basesyntax.service.impl.ParseServiceImpl;
import core.basesyntax.service.impl.ReadFromFileImpl;
import core.basesyntax.service.impl.ReportCreatorService;
import core.basesyntax.service.impl.WriteToFileImpl;
import core.basesyntax.strategy.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    private static final String OUTPUT_PATH = "src/main/java/resource/report.csv";
    private static final String INPUT_PATH = "src/main/java/resource/data.csv";

    public static void main(String[] args) {
        Map<String, OperationHandler> map = new HashMap<>();
        map.put("b", new BalanceOperationImpl());
        map.put("p", new PurchaseOperationImpl());
        map.put("r", new ReturnOperationImpl());
        map.put("s", new SupplyOperationImpl());

        OperationStrategy strategy = new OperationStrategy(map);

        Reader readFromFile = new ReadFromFileImpl();
        List<String> lines = readFromFile.readFromFile(INPUT_PATH);

        ParseService parseService = new ParseServiceImpl();
        List<Transaction> transactions = parseService.transactionsParser(lines);

        for (Transaction transaction : transactions) {
            OperationHandler operationHandler = strategy.getByOperation(transaction.getOperation());
            operationHandler.apply(transaction);
        }

        Writer writeToFile = new WriteToFileImpl();
        CreateReportService createReportService = new ReportCreatorService();
        writeToFile.writeToFile(OUTPUT_PATH,
                createReportService.createReport());
    }
}
