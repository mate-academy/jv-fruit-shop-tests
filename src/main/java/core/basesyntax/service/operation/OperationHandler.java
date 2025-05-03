package core.basesyntax.service.operation;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.model.FruitTransaction;

public interface OperationHandler {
    void operation(FruitTransaction transaction, FruitDao dao);
}
