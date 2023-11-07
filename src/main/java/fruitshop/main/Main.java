package fruitshop.main;

import fruitshop.model.Operation;
import fruitshop.operation.OperationHandler;
import fruitshop.operation.impl.BalanceOperationHandlerImpl;
import fruitshop.operation.impl.OperationStrategyImpl;
import fruitshop.operation.impl.PurchaseOperationHandlerImpl;
import fruitshop.operation.impl.ReturnOperationHandlerImpl;
import fruitshop.operation.impl.SupplyOperationHandlerImpl;
import fruitshop.service.Parser;
import fruitshop.service.TransactionProcessor;
import fruitshop.service.impl.CsvParser;
import fruitshop.service.impl.ReaderServiceImpl;
import fruitshop.service.impl.ReportImpl;
import fruitshop.service.impl.TransactionProcessorImpl;
import fruitshop.service.impl.WriterServiceImpl;
import java.util.HashMap;
import java.util.Map;

public class Main {
    private static final String READ_FILE_NAME = "data.csv";
    private static final String WRITE_FILE_NAME = "newData.csv";

    public static void main(String[] args) {
        Map<Operation, OperationHandler> operationHandlers = new HashMap<>();
        OperationStrategyImpl operationStrategy = new OperationStrategyImpl(operationHandlers);

        operationHandlers.put(Operation.BALANCE, new BalanceOperationHandlerImpl());
        operationHandlers.put(Operation.SUPPLY, new SupplyOperationHandlerImpl());
        operationHandlers.put(Operation.PURCHASE, new PurchaseOperationHandlerImpl());
        operationHandlers.put(Operation.RETURN, new ReturnOperationHandlerImpl());

        ReaderServiceImpl reader = new ReaderServiceImpl();
        Parser csvParser = new CsvParser();
        TransactionProcessor transactionProcessorImpl =
                new TransactionProcessorImpl(operationStrategy);
        csvParser.parse(reader.readFromFile(READ_FILE_NAME))
                .forEach(transactionProcessorImpl::process);
        WriterServiceImpl writer = new WriterServiceImpl();
        writer.writeToFile(new ReportImpl().create(), WRITE_FILE_NAME);
    }
}
