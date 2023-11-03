package core.basesyntax.dao.impl;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.db.FruitStorage;
import java.util.Map;

public class FruitDaoImpl implements FruitDao {
    private static final int GET_FAILURE_INDEX = -1;

    @Override
    public void addFirst(String fruitName, int amount) {
        FruitStorage.goods.put(fruitName, amount);
    }

    @Override
    public void add(String fruitName, int amount) {
        for (Map.Entry<String, Integer> good : FruitStorage.goods.entrySet()) {
            if (fruitName.equals(good.getKey())) {
                good.setValue(good.getValue() + amount);
                break;
            }
        }
    }

    @Override
    public Integer get(String fruitName) {
        for (Map.Entry<String, Integer> good : FruitStorage.goods.entrySet()) {
            if (fruitName.equals(good.getKey())) {
                return good.getValue();
            }
        }
        return GET_FAILURE_INDEX;
    }

    @Override
    public Map<String, Integer> getAll() {
        return FruitStorage.goods;
    }

    @Override
    public void reduce(String fruitName, int amount) {
        for (Map.Entry<String, Integer> good : FruitStorage.goods.entrySet()) {
            if (fruitName.equals(good.getKey())) {
                good.setValue(good.getValue() - amount);
                break;
            }
        }
    }

    @Override
    public void removeAll() {
        FruitStorage.goods.clear();
    }

    @Override
    public Integer size() {
        return FruitStorage.goods.size();
    }
}
