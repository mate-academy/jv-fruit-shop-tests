package core.basesyntax.service;

import core.basesyntax.model.Transaction;
import java.util.List;

public interface CsvParser {
    List<Transaction> parse(List<String> data);
}
