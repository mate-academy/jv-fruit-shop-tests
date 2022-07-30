package core.basesyntax.service.impl;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.service.FruitService;
import java.util.Map;
import java.util.NoSuchElementException;

public class FruitServiceImpl implements FruitService {

    private final FruitDao fruitDao;

    public FruitServiceImpl(FruitDao fruitDao) {
        this.fruitDao = fruitDao;
    }

    @Override
    public void update(String fruitName, int quantity) {
        fruitDao.update(fruitName, quantity);
    }

    @Override
    public int get(String fruitName) {
        return fruitDao.get(fruitName).orElseThrow(() -> new NoSuchElementException(
                "Could not get fruits quantity by fruitName = " + fruitName
        ));
    }

    @Override
    public Map<String, Integer> getAll() {
        return fruitDao.getAll();
    }
}
