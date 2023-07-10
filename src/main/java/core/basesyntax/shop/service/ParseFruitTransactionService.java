package core.basesyntax.shop.service;

import core.basesyntax.shop.model.FruitTransaction;
import java.util.List;

public interface ParseFruitTransactionService {
    List<FruitTransaction> getFruitOperations(List<String> list);
}
