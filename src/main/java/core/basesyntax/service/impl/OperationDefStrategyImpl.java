package core.basesyntax.service.impl;

import core.basesyntax.exceptions.OperationDefinitionException;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.OperationDefStrategy;
import java.util.Map;

public class OperationDefStrategyImpl implements OperationDefStrategy {
    private Map<String, FruitTransaction.Operation> operationMap;

    public OperationDefStrategyImpl(Map<String, FruitTransaction.Operation> operationMap) {
        this.operationMap = operationMap;
    }

    @Override
    public FruitTransaction.Operation get(String code) {
        FruitTransaction.Operation operation;
        if ((operation = operationMap.get(code)) == null) {
            throw new OperationDefinitionException("Cannot define operation type with code \"" + code + "\"");
        }
        return operation;
    }
}
