package core.basesyntax;

import core.basesyntax.dao.FruitTransactionDao;
import core.basesyntax.dao.FruitTransactionDaoImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.operation.BalanceOperationHandler;
import core.basesyntax.operation.OperationHandler;
import core.basesyntax.operation.OperationStrategy;
import core.basesyntax.operation.OperationStrategyImpl;
import core.basesyntax.operation.PurchaseOperationHandler;
import core.basesyntax.operation.ReturnOperationHandler;
import core.basesyntax.operation.SupplyOperationHandler;
import core.basesyntax.read.file.ReaderFromFile;
import core.basesyntax.read.file.ReaderFromFileImpl;
import core.basesyntax.service.FruitTransactionService;
import core.basesyntax.service.FruitTransactionServiceImpl;
import core.basesyntax.service.TransactionService;
import core.basesyntax.service.TransactionServiceImpl;
import core.basesyntax.write.file.WriteToFile;
import core.basesyntax.write.file.WriteToFileImpl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        final String inputFile = "src\\main\\resources\\inputData.csv";
        final String reportFile = "src\\main\\resources\\reportData.csv";
        FruitTransactionDao fruitTransactionDao = new FruitTransactionDaoImpl();
        Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(FruitTransaction.Operation.BALANCE,
                new BalanceOperationHandler(fruitTransactionDao));
        operationHandlerMap.put(FruitTransaction.Operation.SUPPLY,
                new SupplyOperationHandler(fruitTransactionDao));
        operationHandlerMap.put(FruitTransaction.Operation.PURCHASE,
                new PurchaseOperationHandler(fruitTransactionDao));
        operationHandlerMap.put(FruitTransaction.Operation.RETURN,
                new ReturnOperationHandler(fruitTransactionDao));
        ReaderFromFile readerFromFile = new ReaderFromFileImpl();
        List<String[]> fruitsInputDataList = readerFromFile.readFromFile(inputFile);
        FruitTransactionService fruitTransactionService =
                new FruitTransactionServiceImpl();
        List<FruitTransaction> fruitTransactionList =
                fruitTransactionService.getFruitsTransactions(fruitsInputDataList);
        OperationStrategy operationStrategy = new OperationStrategyImpl(operationHandlerMap);
        TransactionService transactionService =
                new TransactionServiceImpl(operationStrategy);
        Map<String, Integer> qtyOfFruitsAfterWorkingDay
                = transactionService.countsFruitsAfterWorkDay(fruitTransactionList);
        WriteToFile writeToFile = new WriteToFileImpl();
        writeToFile.write(qtyOfFruitsAfterWorkingDay,reportFile);
    }
}
