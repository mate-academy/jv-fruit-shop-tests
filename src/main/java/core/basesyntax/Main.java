package core.basesyntax;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.operation.*;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationStrategyImpl;

import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Map<FruitTransaction.Operation, OperationHandler> operationOperationHandlerMap = new HashMap<>();
        operationOperationHandlerMap.put(FruitTransaction.Operation.BALANCE, new BalanceOperationHandlerImpl());
        operationOperationHandlerMap.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperationHandlerImpl());
        operationOperationHandlerMap.put(FruitTransaction.Operation.SUPPLY, new SupplyOperationHandlerImpl());
        operationOperationHandlerMap.put(FruitTransaction.Operation.RETURN, new ReturnOperationHandlerImpl());
        OperationStrategy operationStrategy = new OperationStrategyImpl(operationOperationHandlerMap);

        //FileWriterService writerService = new FileWriterServiceImpl();
        //String content = "banana";
        //String filePath = "src/test/resources/FileWriter.csv";
        //writerService.write(content, filePath);
    }
}
