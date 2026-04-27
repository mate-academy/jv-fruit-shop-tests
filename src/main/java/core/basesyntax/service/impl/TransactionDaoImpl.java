package core.basesyntax.service.impl;

import core.basesyntax.checker.EntryValidator;
import core.basesyntax.db.Storage;
import core.basesyntax.service.TransactionDao;
import java.util.HashMap;
import java.util.Map;

public class TransactionDaoImpl implements TransactionDao {
    private final Storage storage;
    private final EntryValidator entryValidator;
    
    public TransactionDaoImpl(Storage storage, EntryValidator entryValidator) {
        this.storage = storage;
        this.entryValidator = entryValidator;
    }
    
    @Override
    public void set(String fruit, int quantity) {
        entryValidator.validateNewFruitEntry(fruit, quantity);
        storage.put(fruit, quantity);
    }

    @Override
    public void add(String fruit, int quantity) {
        entryValidator.validateFruit(fruit);
        entryValidator.validateQuantity(quantity);

        int current = storage.getQuantity(fruit)
                .orElseThrow(() -> new IllegalArgumentException("Cannot find fruit"));

        int newValue = current + quantity;
        storage.put(fruit, newValue);
    }

    @Override
    public void subtract(String fruit, int quantity) {
        entryValidator.validateFruit(fruit);
        entryValidator.validateQuantity(quantity);
        
        int currentValue = storage.getQuantity(fruit)
                .orElseThrow(() -> new IllegalArgumentException("Cannot find fruit"));
        
        int newValue = currentValue - quantity;
        
        if (newValue < 0) {
            throw new RuntimeException("balance can't be negative");
        }
        storage.put(fruit, newValue);
    }
    
    @Override
    public Map<String, Integer> getAll() {
        return new HashMap<>(storage.getStorage());
    }
}
