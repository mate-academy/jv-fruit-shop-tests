package core.basesyntax;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.impl.StorageDaoImpl;
import core.basesyntax.models.TransactionDto;
import core.basesyntax.services.FileReader;
import core.basesyntax.services.OperationHandler;
import core.basesyntax.services.Parser;
import core.basesyntax.services.Validator;
import core.basesyntax.services.impl.AddOperationHandler;
import core.basesyntax.services.impl.FileReaderImpl;
import core.basesyntax.services.impl.FileWriterImpl;
import core.basesyntax.services.impl.ParserImpl;
import core.basesyntax.services.impl.PurchaseOperationHandler;
import core.basesyntax.services.impl.ReportMakerImpl;
import core.basesyntax.services.impl.ValidatorImpl;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationStrategyImpl;
import core.basesyntax.strategy.OperationType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    private static final String INPUT_FILE_PATH = "src/main/resources/data.csv";
    private static final String OUTPUT_FILE_PATH = "src/main/resources/report.csv";

    public static void main(String[] args) {
        FileReader fileReader = new FileReaderImpl();
        Validator validator = new ValidatorImpl();
        Parser<TransactionDto> parser = new ParserImpl(validator);
        StorageDao storageDao = new StorageDaoImpl();
        OperationHandler addOperationHandler = new AddOperationHandler(storageDao);
        OperationHandler purchaseOperationHandler = new PurchaseOperationHandler(storageDao);
        Map<String, OperationHandler> operationHandlers = new HashMap<>();
        operationHandlers.put(OperationType.BALANCE.getValue(), addOperationHandler);
        operationHandlers.put(OperationType.SUPPLY.getValue(), addOperationHandler);
        operationHandlers.put(OperationType.PURCHASE.getValue(), purchaseOperationHandler);
        operationHandlers.put(OperationType.RETURN.getValue(), addOperationHandler);
        OperationStrategy operationStrategy = new OperationStrategyImpl(operationHandlers);
        List<String> lines = fileReader.read(INPUT_FILE_PATH);
        List<TransactionDto> transactions = parser.parse(lines);
        for (TransactionDto transactionDto : transactions) {
            String operation = transactionDto.getOperation();
            OperationHandler operationHandler = operationStrategy.getOperationHandler(operation);
            operationHandler.apply(transactionDto);
        }
        String report = new ReportMakerImpl().createReport();
        new FileWriterImpl().write(OUTPUT_FILE_PATH, report);
    }
}
