package core.basesyntax;

import core.basesyntax.dto.Transaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.Parser;
import core.basesyntax.service.ReaderService;
import core.basesyntax.service.ReportCreator;
import core.basesyntax.service.WriterService;
import core.basesyntax.service.impl.DataValidatorImpl;
import core.basesyntax.service.impl.ParserImpl;
import core.basesyntax.service.impl.ReaderServiceImpl;
import core.basesyntax.service.impl.ReportCreatorImpl;
import core.basesyntax.service.impl.WriterServiceImpl;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.impl.AdditionOperationHandler;
import core.basesyntax.strategy.impl.BalanceOperationHandler;
import core.basesyntax.strategy.impl.SubtractionOperatorHandler;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Map<Operation, OperationHandler> handlers = new HashMap<>();
        handlers.put(Operation.BALANCE, new BalanceOperationHandler());
        handlers.put(Operation.PURCHASE, new SubtractionOperatorHandler());
        handlers.put(Operation.RETURN, new AdditionOperationHandler());
        handlers.put(Operation.SUPPLY, new AdditionOperationHandler());

        ReaderService reader = new ReaderServiceImpl();
        List<String> stringsList = reader.readFromFile("src/main/resources/input-file.txt");
        Parser parser = new ParserImpl(new DataValidatorImpl());
        List<Transaction> list = parser.parseToTransactionList(stringsList);
        for (Transaction transaction : list) {
            OperationHandler operationHandler = handlers.get(transaction.getOperation());
            operationHandler.apply(transaction);
        }
        ReportCreator creator = new ReportCreatorImpl();
        String report = creator.createReport();
        WriterService writerService = new WriterServiceImpl();
        writerService.writeToFile("src/main/resources/report.txt", report);
    }
}
