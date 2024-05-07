package core.basesyntax.servise;

import core.basesyntax.servise.impl.FruitTransaction;
import java.util.List;

public interface ParserService {
    List<FruitTransaction> parsingData(List<String> inputList);
}
