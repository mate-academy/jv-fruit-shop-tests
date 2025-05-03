package core.basesyntax.service;

import core.basesyntax.strategy.transactions.FruitTransaction;
import java.util.List;

public interface FruitTransactionParser {
    List<FruitTransaction> parse(List<String> strings);
}
