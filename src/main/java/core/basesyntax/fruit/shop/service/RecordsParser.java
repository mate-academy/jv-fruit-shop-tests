package core.basesyntax.fruit.shop.service;

import core.basesyntax.fruit.shop.model.FruitTransaction;

import java.util.List;

public interface RecordsParser {
    List<FruitTransaction> parseRecords(List<String> records);
}
