package core.basesyntax;

import core.basesyntax.model.Transaction;
import core.basesyntax.service.ParseService;
import core.basesyntax.service.ReaderService;
import core.basesyntax.service.ReportService;
import core.basesyntax.service.WriterService;
import core.basesyntax.service.impl.ParseServiceImpl;
import core.basesyntax.service.impl.ReaderServiceImpl;
import core.basesyntax.service.impl.ReportServiceImpl;
import core.basesyntax.service.impl.WriterServiceImpl;
import core.basesyntax.strategy.BalanceOperationHandler;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.PurchaseOperationHandler;
import core.basesyntax.strategy.ReturnOperationHandler;
import core.basesyntax.strategy.SupplyOperationHandler;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        final String inputFile = "src/main/resources/input.csv";
        final String outputFile = "src/main/resources/output.csv";

        ReaderService readerService = new ReaderServiceImpl();
        List<String> processedData = readerService.read(inputFile);

        Map<String, OperationHandler> handlerMap = new HashMap<>();
        handlerMap.put("b", new BalanceOperationHandler());
        handlerMap.put("s", new SupplyOperationHandler());
        handlerMap.put("p", new PurchaseOperationHandler());
        handlerMap.put("r", new ReturnOperationHandler());

        ParseService parseService = new ParseServiceImpl();
        List<Transaction> parsedTransaction = parseService.parse(processedData);

        for (Transaction transaction : parsedTransaction) {
            OperationHandler handler = handlerMap.get(transaction.getOperation());
            handler.process(transaction);
        }

        ReportService reportService = new ReportServiceImpl();
        String report = reportService.getReport();

        WriterService writerService = new WriterServiceImpl();
        writerService.writeToFile(outputFile, report);
    }
}
