package core.basesyntax.service.impl;

import core.basesyntax.service.OperationDay;
import core.basesyntax.service.StorageService;
import java.util.List;
import java.util.Map;

public class OperationsPerDay implements OperationDay {
    private List<String> operation;

    @Override
    public void createOperations(List<String> operationalDay, Map<String,
            StorageService> operationStorageMap) {
        for (int i = 1; i < operationalDay.size(); i++) {
            operation = List.of(operationalDay.get(i).split(","));
            operationStorageMap.get(operation.get(0))
                    .operateSupply(operation.get(1),Integer.valueOf(operation.get(2)));
        }
    }
}
