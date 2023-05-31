package core.basesyntax.parser;

import core.basesyntax.fruittransaction.FruitTransaction;
import java.util.List;

public interface TransactionParser {
    List<FruitTransaction> parse(List<String> lines);
}
