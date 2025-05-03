package core.basesyntax;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.service.impl.DataProcessServiceImpl;
import core.basesyntax.service.strategy.OperationHandler;
import core.basesyntax.service.strategy.OperationHandlerOfBalance;
import core.basesyntax.service.strategy.OperationHandlerOfPurchase;
import core.basesyntax.service.strategy.OperationHandlerOfReturn;
import core.basesyntax.service.strategy.OperationHandlerOfSupply;
import java.util.Map;

public class Main {
    private static final String INPUT_FILE = "src/main/resources/inputFile.csv";
    private static final String OUTPUT_FILE = "src/main/resources/reportFile.csv";

    public static void main(String[] args) {
        Map<FruitTransaction.Operation, OperationHandler> operationServiceMap = Map.of(
                FruitTransaction.Operation.BALANCE,
                new OperationHandlerOfBalance(),
                FruitTransaction.Operation.SUPPLY,
                new OperationHandlerOfSupply(),
                FruitTransaction.Operation.PURCHASE,
                new OperationHandlerOfPurchase(),
                FruitTransaction.Operation.RETURN,
                new OperationHandlerOfReturn());

        DataProcessServiceImpl dataProcess = new DataProcessServiceImpl(operationServiceMap);
        dataProcess.processReport(INPUT_FILE, OUTPUT_FILE);
    }
}
