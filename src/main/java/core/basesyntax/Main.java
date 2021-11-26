package core.basesyntax;

import core.basesyntax.model.TransactionDto;
import core.basesyntax.service.FileReader;
import core.basesyntax.service.Parser;
import core.basesyntax.service.impl.FileReaderImpl;
import core.basesyntax.service.impl.FileWriterImpl;
import core.basesyntax.service.impl.ParserImpl;
import core.basesyntax.service.impl.ReportServiceImpl;
import core.basesyntax.service.impl.ValidatorImpl;
import core.basesyntax.service.operation.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationStrategyImpl;
import java.util.List;

public class Main {
    private static final String INPUT_FILE = "src/main/resources/fruits-log.csv";
    private static final String OUTPUT_FILE = "src/main/resources/fruits-result.csv";

    public static void main(String[] args) {
        FileReader reader = new FileReaderImpl();
        List<String> inputData = reader.read(INPUT_FILE);
        Parser<TransactionDto> parser = new ParserImpl(new ValidatorImpl());
        List<TransactionDto> transactionDtoS = parser.parseLine(inputData);
        OperationStrategy operationStrategy = new OperationStrategyImpl();

        for (TransactionDto transaction : transactionDtoS) {
            String operation = transaction.getOperation();
            OperationHandler operationHandler = operationStrategy.get(operation);
            operationHandler.apply(transaction.getFruitName(), transaction.getQuantity());
        }
        String report = new ReportServiceImpl().formReport();
        new FileWriterImpl().write(report, OUTPUT_FILE);
    }
}
