package core.basesyntax.service;

import java.util.List;
import core.basesyntax.model.FruitTransaction;

public interface DataParserService {
    List<FruitTransaction> parseDataToFruitTransaction(List<String> data);
}
