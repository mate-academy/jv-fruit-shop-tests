package core.basesyntax.strategy;

import core.basesyntax.model.FruitTransaction;
import java.util.Map;

public class SupplyOperation implements OperationHandler {

    @Override
    public void handle(FruitTransaction transaction, Map<String, Integer> storage) {
        // ОБОВ'ЯЗКОВО: Перевірки на null (для проходження тестів)
        if (transaction == null) {
            throw new IllegalArgumentException("Transaction cannot be null");
        }
        if (storage == null) {
            throw new IllegalArgumentException("Storage cannot be null");
        }

        // Основна логіка - додаємо кількість до існуючого запасу (як і у ReturnOperation)
        storage.put(transaction.getFruit(), storage
                .getOrDefault(transaction.getFruit(), 0) + transaction.getQuantity());
    }
}
