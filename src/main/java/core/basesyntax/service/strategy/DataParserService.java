package core.basesyntax.service.strategy;

import core.basesyntax.model.FruitTransaction;
import java.util.List;

public interface DataParserService {
    List<FruitTransaction> createFruitTransaction(List<String> data);
}
