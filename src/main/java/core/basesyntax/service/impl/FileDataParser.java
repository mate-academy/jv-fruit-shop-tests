package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import java.util.List;

public interface FileDataParser {
    List<FruitTransaction> parseData(List<String> dataFromFile);
}
