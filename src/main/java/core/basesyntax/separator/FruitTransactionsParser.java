package core.basesyntax.separator;

import core.basesyntax.model.Transaction;

import java.util.List;

public interface FruitTransactionsParser {
    List<Transaction> transactionsParser(List<String> readLine);
}
