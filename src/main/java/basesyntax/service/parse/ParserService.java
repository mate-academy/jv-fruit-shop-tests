package basesyntax.service.parse;

import basesyntax.model.FruitTransaction;
import java.util.List;

public interface ParserService {
    List<FruitTransaction> parse(List<String> list);
}
