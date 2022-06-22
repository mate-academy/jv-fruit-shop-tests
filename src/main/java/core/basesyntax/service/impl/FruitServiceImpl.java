package core.basesyntax.service.impl;

import core.basesyntax.model.OperationWithFruit;
import core.basesyntax.model.TransactionInfo;
import core.basesyntax.service.FruitService;
import core.basesyntax.service.OperationService;
import java.util.List;

public class FruitServiceImpl implements FruitService {
    private final OperationService operationService;

    public FruitServiceImpl(OperationService operationService) {
        this.operationService = operationService;
    }

    public OperationService getOperationService() {
        return operationService;
    }

    @Override
    public void process(List<TransactionInfo> transferFruitList) {
        for (TransactionInfo item : transferFruitList) {
            OperationWithFruit type = item.getOperationType();
            operationService.getOperationHandler(type).handle(item);
        }
    }
}
