package core.basesyntax.service;

import java.util.List;
import java.util.Map;

public interface OperationDay {
    void createOperations(List<String> operationalDay, Map<String,
            StorageService> operationStorageMap);
}
