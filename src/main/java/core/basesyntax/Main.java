package core.basesyntax;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.FruitTransaction;
import service.OperationStrategy;
import service.ReportMaking;
import service.Reporter;
import serviceimpl.FruitPackerImpl;
import serviceimpl.OperationStrategyImpl;
import serviceimpl.ReaderImpl;
import serviceimpl.ReportMakingImpl;
import serviceimpl.ReporterImpl;
import serviceimpl.WriterImpl;
import strategy.BalanceOperation;
import strategy.Operating;
import strategy.PurchaseOperation;
import strategy.ReturnOperation;
import strategy.SupplyOperation;

public class Main {
    public static final String SOURCE_FILE_NAME = "src/main/java/resources/sourceFile.csv";
    public static final String RESULT_FILE_NAME = "src/main/java/resources/resultFile.csv";
    private static final Map<FruitTransaction.Operation, Operating> operationsMap = new HashMap<>();

    public static void main(String[] args) {
        operationsMap.putAll(allOperationsMap());
        List<String> transactionsStringList = new ReaderImpl().readDataFromFile(SOURCE_FILE_NAME);
        List<FruitTransaction> transactionsList = new FruitPackerImpl()
                .makeList(transactionsStringList);
        OperationStrategy operationStrategyService = new OperationStrategyImpl(operationsMap);
        ReportMaking fruitTransactionService = new ReportMakingImpl(operationStrategyService);
        fruitTransactionService.makeReport(transactionsList);
        Reporter reportService = new ReporterImpl();
        List<String> dailyTransactionsReport = reportService.report();
        new WriterImpl().writeToFile(dailyTransactionsReport, RESULT_FILE_NAME);
    }

    public static Map<FruitTransaction.Operation, Operating> allOperationsMap() {
        Map<FruitTransaction.Operation, Operating> operationsMap = new HashMap<>();
        operationsMap.put(FruitTransaction.Operation.SUPPLY, new SupplyOperation());
        operationsMap.put(FruitTransaction.Operation.BALANCE, new BalanceOperation());
        operationsMap.put(FruitTransaction.Operation.RETURN, new ReturnOperation());
        operationsMap.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperation());
        return operationsMap;
    }
}

