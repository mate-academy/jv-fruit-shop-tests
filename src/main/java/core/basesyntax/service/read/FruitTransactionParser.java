package core.basesyntax.service.read;

import core.basesyntax.model.FruitTransaction;
import java.util.List;

public interface FruitTransactionParser {
    List<FruitTransaction> parse(List<String> fileContent);
}
