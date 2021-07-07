package core.basesyntax.basesyntax;

import core.basesyntax.dto.Transaction;
import core.basesyntax.service.FileReader;
import core.basesyntax.service.FileReaderImpl;
import core.basesyntax.service.FileWriter;
import core.basesyntax.service.FileWriterImpl;
import core.basesyntax.service.FruitReportService;
import core.basesyntax.service.FruitReportServiceImpl;
import core.basesyntax.service.Parser;
import core.basesyntax.service.ParserImpl;
import core.basesyntax.strategy.AppendOperationHandler;
import core.basesyntax.strategy.BalanceOperationHandler;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.PurchaseOperationHandler;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Map<String, OperationHandler> operationsMap = new HashMap<>();
        operationsMap.put("r", new AppendOperationHandler());
        operationsMap.put("s", new AppendOperationHandler());
        operationsMap.put("b", new BalanceOperationHandler());
        operationsMap.put("p", new PurchaseOperationHandler());
        FileReader reader = new FileReaderImpl();
        List<String> linesFromReader = reader.readFromFile("src/main/resources/Data.csv");
        Parser parser = new ParserImpl();
        for (int i = 1; i < linesFromReader.size(); i++) {
            Transaction transaction = parser.parseLine(linesFromReader.get(i));
            OperationHandler handler = operationsMap.get(transaction.getOperation());
            handler.apply(transaction);
        }
        FruitReportService reportService = new FruitReportServiceImpl();
        String report = reportService.returnReport();

        FileWriter writer = new FileWriterImpl();
        writer.writeToFile(report, "src/main/resources/ResultData.csv");
    }
}
