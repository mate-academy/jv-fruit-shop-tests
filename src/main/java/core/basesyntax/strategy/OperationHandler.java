package core.basesyntax.strategy;

import core.basesyntax.service.StorageSupplyService;

public interface OperationHandler {
    void execute(String fruit, Integer amount, StorageSupplyService supplyService);
}
