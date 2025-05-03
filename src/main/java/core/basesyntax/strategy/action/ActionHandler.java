package core.basesyntax.strategy.action;

import core.basesyntax.dao.ProductDao;
import core.basesyntax.model.ProductTransaction;

public interface ActionHandler {
    void process(ProductDao productDao, ProductTransaction transaction);
}
