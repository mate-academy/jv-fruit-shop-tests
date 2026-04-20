package core.basesyntax.handler;

import core.basesyntax.service.TransactionDao;
import java.util.Map;

public class FakeTransactionDao implements TransactionDao {
    private String lastMethod;
    private String fruit;
    private int quantity;

    public String getLastMethod() {
        return lastMethod;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getFruit() {
        return fruit;
    }

    @Override
    public void add(String fruit, int quantity) {
        lastMethod = "add";
        this.fruit = fruit;
        this.quantity = quantity;
    }

    @Override
    public void subtract(String fruit, int quantity) {
        lastMethod = "subtract";
        this.fruit = fruit;
        this.quantity = quantity;
    }

    @Override
    public void set(String fruit, int quantity) {
        lastMethod = "set";
        this.fruit = fruit;
        this.quantity = quantity;
    }

    @Override
    public Map<String, Integer> getAll() {
        return Map.of();
    }
}
