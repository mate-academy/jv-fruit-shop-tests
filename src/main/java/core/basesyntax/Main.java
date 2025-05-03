package core.basesyntax;

import core.basesyntax.db.Storage;
import core.basesyntax.model.TransactionDto;
import core.basesyntax.service.ReportMaker;
import core.basesyntax.service.files.FileReader;
import core.basesyntax.service.files.FileWriter;
import core.basesyntax.service.operation.handler.BalanceHandler;
import core.basesyntax.service.operation.handler.OperationTypeHandler;
import core.basesyntax.service.operation.handler.Operations;
import core.basesyntax.service.operation.handler.PurchaseHandler;
import core.basesyntax.service.operation.handler.ReturnHandler;
import core.basesyntax.service.operation.handler.SupplyHandler;
import core.basesyntax.service.operation.strategy.OperationStrategy;
import core.basesyntax.service.operation.strategy.OperationStrategyImpl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    private static final String TO_FILE_NAME = "result.csv";
    private static final String FROM_FILE_NAME = "fruitsData.csv";

    public static void main(String[] args) {
        Map<Operations, OperationTypeHandler> strategy = new HashMap<>();
        strategy.put(Operations.RETURN, new ReturnHandler());
        strategy.put(Operations.BALANCE, new BalanceHandler());
        strategy.put(Operations.PURCHASE, new PurchaseHandler());
        strategy.put(Operations.SUPPLY, new SupplyHandler());
        OperationStrategy operationStrategyMap = new OperationStrategyImpl(strategy);
        TransactionDto transactionDto = new TransactionDto();
        ReportMaker reportMaker = new ReportMaker();
        FileReader readFromFile = new FileReader();
        FileWriter writeToFile = new FileWriter();
        List<String> data = readFromFile.read(FROM_FILE_NAME);
        transactionDto.convertFromListToDb(data, operationStrategyMap);
        String report = reportMaker.make(Storage.fruits);
        writeToFile.writeToCsv(report, TO_FILE_NAME);
    }
}
