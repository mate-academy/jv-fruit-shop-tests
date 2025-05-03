package core.basesyntax;

import core.basesyntax.model.Operation;
import core.basesyntax.model.Transaction;
import core.basesyntax.service.FileService;
import core.basesyntax.service.FileServiceImpl;
import core.basesyntax.service.Parser;
import core.basesyntax.service.ParserCsvLineService;
import core.basesyntax.service.ReportService;
import core.basesyntax.service.ReportToCsvService;
import core.basesyntax.service.strategy.AddOperationHandler;
import core.basesyntax.service.strategy.BalanceOperationHandler;
import core.basesyntax.service.strategy.OperationHandler;
import core.basesyntax.service.strategy.SubtractOperationHandler;
import core.basesyntax.service.strategy.TransactionStrategy;
import core.basesyntax.service.strategy.TransactionStrategyImpl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    private static final String INPUT_FILE_NAME = "src/main/resources/input.csv";

    public static void main(String[] args) {
        Map<Operation, OperationHandler> handlersMap = new HashMap<>();
        handlersMap.put(Operation.BALANCE, new BalanceOperationHandler());
        handlersMap.put(Operation.SUPPLY, new AddOperationHandler());
        handlersMap.put(Operation.RETURN, new AddOperationHandler());
        handlersMap.put(Operation.PURCHASE, new SubtractOperationHandler());

        FileService reader = new FileServiceImpl();
        List<String> recordsFromFile = reader.read(INPUT_FILE_NAME);

        List<Transaction> transactions = new ArrayList<>();
        Parser parser = new ParserCsvLineService();

        for (int i = 1; i < recordsFromFile.size(); i++) {
            transactions.add(parser.parseLine(recordsFromFile.get(i)));
        }
        TransactionStrategy strategy = new TransactionStrategyImpl(handlersMap);
        strategy.execute(transactions);
        ReportService reportToCsv = new ReportToCsvService();
        reportToCsv.generateReport();
    }
}
