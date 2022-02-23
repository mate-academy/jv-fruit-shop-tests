package core.basesyntax.service;

import core.basesyntax.model.FruitModel;
import core.basesyntax.model.ReturnModelImpl;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.StrategyImpl;
import core.basesyntax.strategy.handlers.BalanceOperationHandler;
import core.basesyntax.strategy.handlers.PurchaseOperationHandler;
import core.basesyntax.strategy.handlers.ReturnOperationHandler;
import core.basesyntax.strategy.handlers.SupplyOperationHandler;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProcessInputData {
    private static final int OPERATION_INDEX = 0;
    private final Map<String, OperationHandler> operationsWithFruits;

    public ProcessInputData() {
        operationsWithFruits = new HashMap<>();
        operationsWithFruits.put("b", new BalanceOperationHandler());
        operationsWithFruits.put("s", new SupplyOperationHandler());
        operationsWithFruits.put("r", new ReturnOperationHandler());
        operationsWithFruits.put("p", new PurchaseOperationHandler());
    }

    private void executeOperation(String[] line) {
        ReturnModelImpl returnModel = new ReturnModelImpl();
        FruitModel fruitModel = returnModel.getModel(line);
        StrategyImpl strategyImpl = new StrategyImpl(operationsWithFruits
                .get(line[OPERATION_INDEX]));
        strategyImpl.executeStrategy(fruitModel);
    }

    public void processInput(List<String[]> parsedData) {
        for (String[] operation: parsedData) {
            executeOperation(operation);
        }
    }
}
