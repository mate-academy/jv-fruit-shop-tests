package core.basesyntax.service.fruittransactionparser;

import core.basesyntax.model.FruitTransaction;
import java.util.List;

public interface FruitTransactionParser {
    List<FruitTransaction> parseToFruitTransactions(List<String> rowFruitTransaction);
}
