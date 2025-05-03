package core.basesyntax.service.fileparser;

import core.basesyntax.model.FruitTransaction;
import java.util.List;

public interface FileParser {
    List<FruitTransaction> parsedFruitTransactions(List<String> fruitsTransactions);
}
