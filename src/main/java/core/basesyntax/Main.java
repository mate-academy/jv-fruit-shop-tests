package core.basesyntax;

import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.model.TransactionDto;
import core.basesyntax.service.OperationStrategy;
import core.basesyntax.service.Parser;
import core.basesyntax.service.ReaderService;
import core.basesyntax.service.ReportService;
import core.basesyntax.service.WriterService;
import core.basesyntax.service.impl.OperationStrategyImpl;
import core.basesyntax.service.impl.ParserImpl;
import core.basesyntax.service.impl.ReaderServiceCsvImpl;
import core.basesyntax.service.impl.ReportServiceCsvImpl;
import core.basesyntax.service.impl.ValidatorCsvImpl;
import core.basesyntax.service.impl.WriterServiceCsvImpl;
import core.basesyntax.strategy.AddOperationHandler;
import core.basesyntax.strategy.BalanceOperationHandler;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.PurchaseOperationHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    private static final String INPUT_FILE = "src/main/resources/input.csv";
    private static final String OUTPUT_FILE = "src/main/resources/output.csv";

    public static void main(String[] args) {
        ReaderService readerService = new ReaderServiceCsvImpl();
        List<String> readLines = readerService.readFromFile(INPUT_FILE);
        Parser parser = new ParserImpl(new ValidatorCsvImpl());
        List<TransactionDto> transactions = new ArrayList<>();
        for (String line : readLines) {
            transactions.add(parser.parseLine(line));
        }

        Map<String, OperationHandler> operationHandlerMap = new HashMap<>();
        operationHandlerMap.put("s", new AddOperationHandler(new FruitDaoImpl()));
        operationHandlerMap.put("b", new BalanceOperationHandler(new FruitDaoImpl()));
        operationHandlerMap.put("p", new PurchaseOperationHandler(new FruitDaoImpl()));
        operationHandlerMap.put("r", new AddOperationHandler(new FruitDaoImpl()));

        OperationStrategy operationStrategy = new OperationStrategyImpl(operationHandlerMap);
        for (TransactionDto transactionDto : transactions) {
            OperationHandler operationHandler = operationStrategy
                    .getOperationHandler(transactionDto.getOperation());
            operationHandler.apply(transactionDto);
        }

        ReportService reportService = new ReportServiceCsvImpl();
        String report = reportService.formReport();

        WriterService writerService = new WriterServiceCsvImpl();
        writerService.writeToFile(report, OUTPUT_FILE);
    }
}
