package core.basesyntax.service;

import core.basesyntax.handler.OperationHandler;
import core.basesyntax.transactor.Operation;
import java.util.List;
import java.util.Map;

public interface ShopUpdateService<T> {
    void update(List<T> data, Map<Operation, OperationHandler> map);
}
