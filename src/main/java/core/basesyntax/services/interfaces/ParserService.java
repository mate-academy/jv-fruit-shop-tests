package core.basesyntax.services.interfaces;

import core.basesyntax.model.FruitTransaction;
import java.util.List;

public interface ParserService {
    List<FruitTransaction> parser(List<String> stringList);
}
