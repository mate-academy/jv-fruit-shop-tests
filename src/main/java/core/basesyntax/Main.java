package core.basesyntax;

import core.basesyntax.dto.Transaction;
import core.basesyntax.service.FileWriter;
import core.basesyntax.service.Parser;
import core.basesyntax.service.ReportService;
import core.basesyntax.service.Validator;
import core.basesyntax.service.impl.FileReaderImpl;
import core.basesyntax.service.impl.FileWriterImpl;
import core.basesyntax.service.impl.FruitParser;
import core.basesyntax.service.impl.FruitReportService;
import core.basesyntax.service.impl.FruitValidator;
import core.basesyntax.strategy.BalanceOperationHandler;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.PurchaseOperationHandler;
import core.basesyntax.strategy.SupplyOperationHandler;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Map<String, OperationHandler> handlerMap = new HashMap<>();
        handlerMap.put("b", new BalanceOperationHandler());
        handlerMap.put("s", new SupplyOperationHandler());
        handlerMap.put("p", new PurchaseOperationHandler());
        handlerMap.put("r", new SupplyOperationHandler());

        List<String> parsedLines = new FileReaderImpl()
                .readFromFile("src/main/resources/shop_operations.csv");
        parsedLines.remove(0);
        Validator fruitValidator = new FruitValidator();
        Parser fruitParser = new FruitParser(fruitValidator);

        for (String line : parsedLines) {
            Transaction transaction = fruitParser.parse(line);
            OperationHandler handler =
                    handlerMap.get(transaction.getOperation());
            handler.perform(transaction);
        }
        ReportService reportService = new FruitReportService();
        String report = reportService.getReport();
        FileWriter writeFile = new FileWriterImpl();
        writeFile.writeToFile("src/main/resources/report.csv", report);
    }
}
