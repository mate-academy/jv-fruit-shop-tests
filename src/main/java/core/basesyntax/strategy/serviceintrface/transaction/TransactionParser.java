package core.basesyntax.strategy.serviceintrface.transaction;

import core.basesyntax.strategy.serviceintrface.operation.model.FruitTransaction;
import java.util.List;

public interface TransactionParser {
    List<FruitTransaction> parseTransactions(List<String> data);
}
