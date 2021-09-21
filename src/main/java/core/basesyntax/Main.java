package core.basesyntax;

import core.basesyntax.model.OperationTypes;
import core.basesyntax.model.TransactionDto;
import core.basesyntax.services.Reporting;
import core.basesyntax.services.ReportingImpl;
import core.basesyntax.services.operations.Operation;
import core.basesyntax.services.operations.OperationBalanceImpl;
import core.basesyntax.services.operations.OperationPurchaseImpl;
import core.basesyntax.services.operations.OperationReturnImpl;
import core.basesyntax.services.operations.OperationSupplyImpl;
import core.basesyntax.services.operations.strategy.OperationsStrategy;
import core.basesyntax.services.operations.strategy.OperationsStrategyImpl;
import core.basesyntax.services.readfromfile.ReadingFromFile;
import core.basesyntax.services.readfromfile.ReadingFromFileImpl;
import core.basesyntax.services.stockservice.StockService;
import core.basesyntax.services.stockservice.StockServiceImpl;
import core.basesyntax.services.writetofile.WriteToFile;
import core.basesyntax.services.writetofile.WriteToFileImpl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Map<String, Operation> stringOperationMap = new HashMap<>();
        stringOperationMap.put(OperationTypes.BALANCE.getShortName(), new OperationBalanceImpl());
        stringOperationMap.put(OperationTypes.SUPPLY.getShortName(), new OperationSupplyImpl());
        stringOperationMap.put(OperationTypes.PURCHASE.getShortName(), new OperationPurchaseImpl());
        stringOperationMap.put(OperationTypes.RETURN.getShortName(), new OperationReturnImpl());
        OperationsStrategy strategyOperations = new OperationsStrategyImpl(stringOperationMap);
        String filePath = "src/main/resources/file.csv";
        ReadingFromFile readingFromFile = new ReadingFromFileImpl();
        List<TransactionDto> transactionDtos = readingFromFile.readingFromFile(filePath);
        StockService stockService = new StockServiceImpl(strategyOperations);
        stockService.applyOperationsOnFruitsDto(transactionDtos);
        Reporting reporting = new ReportingImpl();
        List<String> report = reporting.createReport();
        WriteToFile writeToFile = new WriteToFileImpl();
        writeToFile.writeToFile(report);
        report.forEach(System.out::println);
    }
}
