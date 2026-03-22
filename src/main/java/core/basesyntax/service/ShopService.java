package core.basesyntax.service;

import core.basesyntax.model.ShopTransaction;
import java.util.List;

public interface ShopService {
    void process(List<ShopTransaction> transactions);
}
