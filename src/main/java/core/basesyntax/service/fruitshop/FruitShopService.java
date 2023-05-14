package core.basesyntax.service.fruitshop;

import core.basesyntax.model.FruitTransaction;
import java.util.List;
import java.util.Map;

public interface FruitShopService {
    List<FruitTransaction> readAllFromCsv(String readFilePath);

    void exportReport(String writeFilePath);

    int balance(String fruit, int quantity);

    int supply(String fruit, int quantity);

    int purchase(String fruit, int quantity);

    int returnFruits(String fruit, int quantity);

    FruitTransaction mapToFruitTransaction(String row);

    List<String> generateReport(Map<String, Integer> storageMap);
}
