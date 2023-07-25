package core.basesyntax;

import core.basesyntax.service.FruitServiceImpl;
import core.basesyntax.service.Operation;
import core.basesyntax.service.ParserServiceImpl;
import core.basesyntax.service.ReaderServiceImpl;
import core.basesyntax.service.WriterService;
import core.basesyntax.service.impl.ParserServiceImplImpl;
import core.basesyntax.service.impl.ReaderServiceImplImpl;
import core.basesyntax.service.impl.WriterServiceImpl;
import core.basesyntax.strategy.AddOperationHandler;
import core.basesyntax.strategy.BalanceOperationHandler;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.PurchaseOperationHandler;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static final String SOURCE_ADDRESS = "src/main/java/resources/input.csv";
    public static final String DESTINATION_ADDRESS = "src/main/java/resources/report.csv";
    public static final ReaderServiceImpl READER_FILE = new ReaderServiceImplImpl();
    public static final ParserServiceImpl PARSER_FILE = new ParserServiceImplImpl();
    public static final WriterService WRITER_FILE = new WriterServiceImpl();

    public static void main(String[] args) {
        Map<Operation, OperationHandler> operationStrategyMap = new HashMap<>();
        operationStrategyMap.put(Operation.PURCHASE, new PurchaseOperationHandler());
        operationStrategyMap.put(Operation.SUPPLY, new AddOperationHandler());
        operationStrategyMap.put(Operation.BALANCE, new BalanceOperationHandler());
        operationStrategyMap.put(Operation.RETURN, new AddOperationHandler());
        List<String> contentFromFile = READER_FILE.readFromFileInput(SOURCE_ADDRESS);
        contentFromFile.remove(0);
        contentFromFile.stream()
                .map(PARSER_FILE::parseLine)
                .forEach(transaction -> operationStrategyMap
                        .get(Operation.checkTypeOperation(transaction.getOperation()))
                        .apply(transaction));
        FruitServiceImpl reportService = new core.basesyntax.service.impl.FruitServiceImpl();
        String report = reportService.getReport();
        WRITER_FILE.writeToFile(DESTINATION_ADDRESS, report);
    }
}
