package core.basesyntax.dao;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ActionsDaoImpl implements ActionsDao {
    private Map<String, Integer> dataDao = new HashMap<>();

    public ActionsDaoImpl(Map<String, Integer> dataDao) {
        this.dataDao = dataDao;
    }

    @Override
    public void add(String fruit, Integer amount) {
        dataDao.put(fruit, amount);
    }

    @Override
    public void update(String fruit, Integer amount) {
        dataDao.put(fruit, amount);
    }

    @Override
    public int getAmount(String fruit) {
        return dataDao.get(fruit);
    }

    @Override
    public boolean isPresentFruit(String fruit) {
        return dataDao.containsKey(fruit);
    }

    @Override
    public Set<Map.Entry<String, Integer>> getAllFruits() {
        return dataDao.entrySet();
    }

    @Override
    public void clear() {
        dataDao.clear();
    }
}
