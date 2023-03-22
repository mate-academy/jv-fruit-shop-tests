package core.basesyntax;

import core.basesyntax.fileservice.CsvReadFileServiceImpl;
import core.basesyntax.fileservice.CsvWriteFileServiceImpl;
import core.basesyntax.fruittransaction.FruitTransaction;
import core.basesyntax.parser.TransactionParserImpl;
import core.basesyntax.reportservice.ReportServiceImpl;
import core.basesyntax.strategy.OperationStrategyImpl;
import core.basesyntax.transactionexecutor.OperationHandler;
import core.basesyntax.transactionexecutor.OperationHandlerBalanceImpl;
import core.basesyntax.transactionexecutor.OperationHandlerPurchaseImpl;
import core.basesyntax.transactionexecutor.OperationHandlerReturnImpl;
import core.basesyntax.transactionexecutor.OperationHandlerSupplyImpl;
import core.basesyntax.transactionexecutor.TransactionExecutorImpl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        OperationHandler operationHandlerBalanceImpl = new OperationHandlerBalanceImpl();
        OperationHandlerPurchaseImpl operationHandlerPurchase = new OperationHandlerPurchaseImpl();
        OperationHandlerReturnImpl operationHandlerReturn = new OperationHandlerReturnImpl();
        OperationHandlerSupplyImpl operationHandlerSupply = new OperationHandlerSupplyImpl();

        Map<FruitTransaction.Operation, OperationHandler> map = new HashMap<>();
        map.put(FruitTransaction.Operation.BALANCE, operationHandlerBalanceImpl);
        map.put(FruitTransaction.Operation.PURCHASE, operationHandlerPurchase);
        map.put(FruitTransaction.Operation.RETURN, operationHandlerReturn);
        map.put(FruitTransaction.Operation.SUPPLY, operationHandlerSupply);

        OperationStrategyImpl operationStrategy = new OperationStrategyImpl(map);
        CsvReadFileServiceImpl csvReadFileService = new CsvReadFileServiceImpl();
        TransactionParserImpl transactionParser = new TransactionParserImpl();
        TransactionExecutorImpl transactionExecutor
                = new TransactionExecutorImpl(operationStrategy);
        ReportServiceImpl reportService = new ReportServiceImpl();
        CsvWriteFileServiceImpl csvWriteFileService = new CsvWriteFileServiceImpl();

        List<String> listForParsing = csvReadFileService.read("src/main/resource/test.csv");
        List<FruitTransaction> completeFruitsObjectList = transactionParser.parse(listForParsing);
        transactionExecutor.execute(completeFruitsObjectList);
        String completeContentForNewFile = reportService.createReport();
        csvWriteFileService.write("src/main/resource/expectedResult.csv",
                completeContentForNewFile);
    }
}
