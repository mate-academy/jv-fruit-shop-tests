package core.basesyntax;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.CalculationService;
import core.basesyntax.service.CalculationStrategy;
import core.basesyntax.service.FileService;
import core.basesyntax.service.OperationHandler;
import core.basesyntax.service.Parser;
import core.basesyntax.service.ReportCreator;
import core.basesyntax.service.impl.AddingOperationHandler;
import core.basesyntax.service.impl.CalculationServiceImpl;
import core.basesyntax.service.impl.CalculationStrategyImpl;
import core.basesyntax.service.impl.FileServiceImpl;
import core.basesyntax.service.impl.ParserImpl;
import core.basesyntax.service.impl.ReportCreatorImpl;
import core.basesyntax.service.impl.SubtractOperationHandler;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    private static final String FROM_FILE_NAME =
            "src/main/resources/daily_transactions.csv";
    private static final String REPORT_FILE_NAME =
            "src/main/resources/report.csv";

    public static void main(String[] args) {
        Map<Operation, OperationHandler> calculationHandlerMap = new HashMap<>();
        calculationHandlerMap.put(Operation.RETURN, new AddingOperationHandler());
        calculationHandlerMap.put(Operation.SUPPLY, new AddingOperationHandler());
        calculationHandlerMap.put(Operation.BALANCE, new AddingOperationHandler());
        calculationHandlerMap.put(Operation.PURCHASE, new SubtractOperationHandler());

        FileService fileService = new FileServiceImpl();
        List<String> dataFromFile = fileService.readFromFile(FROM_FILE_NAME);

        Parser parser = new ParserImpl();
        List<FruitTransaction> transactions = parser.parse(dataFromFile);

        CalculationStrategy calculationStrategy =
                new CalculationStrategyImpl(calculationHandlerMap);
        CalculationService calculationService = new CalculationServiceImpl(calculationStrategy);
        calculationService.calculate(transactions);

        ReportCreator reportCreator = new ReportCreatorImpl();
        List<String> report = reportCreator.create();
        fileService.writeToFile(report, REPORT_FILE_NAME);
    }
}
