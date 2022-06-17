package core.basesyntax.dao;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ProductDaoImp implements ProductDao {
    private final Map<String, Integer> productMap;

    public ProductDaoImp(Map<String, Integer> productMap) {
        this.productMap = productMap;
    }

    @Override
    public void add(String product, Integer quantity) {
        productMap.put(product, quantity);
    }

    @Override
    public Optional<Integer> get(String product) {
        return Optional.ofNullable(productMap.get(product));
    }

    @Override
    public Map<String, Integer> getAll() {
        return new HashMap<>(productMap);
    }
}
