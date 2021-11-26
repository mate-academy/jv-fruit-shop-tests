package core.basesyntax;

import core.basesyntax.model.TransactionDto;
import core.basesyntax.service.Parser;
import core.basesyntax.service.Reader;
import core.basesyntax.service.implementation.ParserImpl;
import core.basesyntax.service.implementation.ReaderImpl;
import core.basesyntax.service.implementation.ReportImpl;
import core.basesyntax.service.implementation.ValidatorImpl;
import core.basesyntax.service.implementation.WriterImpl;
import core.basesyntax.strategy.AddOperationHandler;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationType;
import core.basesyntax.strategy.PurchaseOperationHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static final String FILE_PATH_TO_READ = "./src/main/resources/fruitInput.csv";
    public static final String FILE_PATH_TO_WRITE = "./src/main/resources/fruitOutput.csv";
    private static final Map<OperationType, OperationHandler> operationHandlerMap = new HashMap<>();

    static {
        operationHandlerMap.put(OperationType.b, new AddOperationHandler());
        operationHandlerMap.put(OperationType.p, new PurchaseOperationHandler());
        operationHandlerMap.put(OperationType.r, new AddOperationHandler());
        operationHandlerMap.put(OperationType.s, new AddOperationHandler());
    }

    public static void main(String[] args) {
        Reader reader = new ReaderImpl();
        List<String> lines = reader.readFromFile(FILE_PATH_TO_READ);
        Parser<TransactionDto> parser = new ParserImpl(new ValidatorImpl());
        List<TransactionDto> transactionDtoList = new ArrayList<>();
        for (String line : lines) {
            transactionDtoList.add(parser.parseTo(line));
        }

        for (TransactionDto transactionDto : transactionDtoList) {
            OperationType operation = transactionDto.getOperation();
            OperationHandler handler = operationHandlerMap.get(operation);
            handler.operate(transactionDto.getFruitName(),transactionDto.getQuantity());
        }

        String report = new ReportImpl().formReport();
        new WriterImpl().writeToFile(FILE_PATH_TO_WRITE,report);
    }
}
