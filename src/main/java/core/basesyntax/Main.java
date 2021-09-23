package core.basesyntax;

import core.basesyntax.model.Operation;
import core.basesyntax.service.FileService;
import core.basesyntax.service.FruitService;
import core.basesyntax.service.OperationHandler;
import core.basesyntax.service.ReportService;
import core.basesyntax.service.Strategy;
import core.basesyntax.service.TransactionDtoParse;
import core.basesyntax.service.impl.BalanceOperation;
import core.basesyntax.service.impl.FileServiceImpl;
import core.basesyntax.service.impl.FruitServiceImpl;
import core.basesyntax.service.impl.PurchaseOperation;
import core.basesyntax.service.impl.ReportServiceImpl;
import core.basesyntax.service.impl.ReturnOperation;
import core.basesyntax.service.impl.StrategyImpl;
import core.basesyntax.service.impl.SupplyOperation;
import core.basesyntax.service.impl.TransactionDto;
import core.basesyntax.service.impl.TransactionDtoParseImpl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    private static final String INPUT_FILE_PATH
            = "src/main/resources/inputFile";
    private static final String REPORT_FILE_PATH
            = "src/main/resources/reportFile";

    public static void main(String[] args) {
        Map<String, OperationHandler> operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(Operation.BALANCE.getOperation(), new BalanceOperation());
        operationHandlerMap.put(Operation.PURCHASE.getOperation(), new PurchaseOperation());
        operationHandlerMap.put(Operation.RETURN.getOperation(), new ReturnOperation());
        operationHandlerMap.put(Operation.SUPPLY.getOperation(), new SupplyOperation());
        FileService fileService = new FileServiceImpl();
        Strategy strategy = new StrategyImpl(operationHandlerMap);
        List<String> inputValues = fileService.readFromFile(INPUT_FILE_PATH);
        TransactionDtoParse transactionDtoParse = new TransactionDtoParseImpl();
        List<TransactionDto> parsedData = transactionDtoParse.parseData(inputValues);
        FruitService fruitService = new FruitServiceImpl(strategy);
        fruitService.saveDataToDb(parsedData);
        ReportService valueReport = new ReportServiceImpl();
        fileService.writeToReportFile(valueReport.getReport(), REPORT_FILE_PATH);
    }
}
