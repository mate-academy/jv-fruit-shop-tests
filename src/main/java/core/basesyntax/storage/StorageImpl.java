package core.basesyntax.storage;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class StorageImpl implements Storage {
    private Map<String, Integer> transactions;

    public StorageImpl() {
        this.transactions = new LinkedHashMap<>();
    }

    @Override
    public Map<String, Integer> getAll() {
        return new LinkedHashMap<>(this.transactions);
    }

    @Override
    public Integer getQuantity(String fruit) {
        if (!transactions.containsKey(fruit)) {
            throw new IllegalArgumentException("No " + fruit + " in storage");
        }
        return transactions.getOrDefault(fruit, 0);
    }

    @Override
    public void put(String fruit, int quantity) {
        if (fruit == null) {
            throw new IllegalArgumentException("Fruit cannot be null");
        }

        transactions.put(fruit, quantity);
    }

    @Override
    public void add(String fruit, int quantity) {
        if (fruit == null) {
            throw new IllegalArgumentException("Fruit cannot be null");
        }

        int current = transactions.getOrDefault(fruit, 0);
        transactions.put(fruit, current + quantity);
    }

    @Override
    public void remove(String fruit, int quantity) {
        if (!transactions.containsKey(fruit)) {
            throw new IllegalArgumentException(fruit + " doesn't exist");
        }

        int current = transactions.get(fruit);

        if (current < quantity) {
            throw new IllegalArgumentException("Not enough quantity for: " + fruit);
        }

        transactions.put(fruit, current - quantity);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof StorageImpl storage)) {
            return false;
        }
        return Objects.equals(transactions, storage.transactions);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(transactions);
    }
}
