package core.basesyntax.db;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class FruitTransactionDbImpl implements FruitTransactionDb {
    private Map<String, Integer> fruitStore;

    public FruitTransactionDbImpl() {
        fruitStore = new HashMap<>();
    }

    @Override
    public Set<Map.Entry<String, Integer>> getAllFruitsTransaction() {
        return fruitStore.entrySet();
    }

    @Override
    public int getQuantity(String fruit) {
        return fruitStore.get(fruit);
    }

    @Override
    public void addQuantity(String fruit, int quantity) {
        fruitStore.merge(fruit, quantity, Integer::sum);
    }

    @Override
    public void subtractQuantity(String fruit, int quantity) {
        fruitStore.merge(fruit, -quantity, Integer::sum);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        FruitTransactionDbImpl that = (FruitTransactionDbImpl) object;
        return Objects.equals(fruitStore, that.fruitStore);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fruitStore);
    }
}
