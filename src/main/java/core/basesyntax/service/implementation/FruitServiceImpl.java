package core.basesyntax.service.implementation;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.FruitService;
import core.basesyntax.storage.Storage;
import java.util.List;
import java.util.stream.Collectors;

public class FruitServiceImpl implements FruitService {
    private final FruitDao fruitDao;

    public FruitServiceImpl(FruitDao fruitDao) {
        this.fruitDao = fruitDao;
    }

    @Override
    public void add(String fruitName, Integer amount) {
        if (fruitName == null || amount == null) {
            throw new RuntimeException("Value cannot be null!");
        } else if (amount < 0) {
            throw new RuntimeException("Amount value cannot be less than 0!");
        } else if (fruitName.isBlank()) {
            throw new RuntimeException("Fruit name cannot be blank!");
        }
        fruitDao.add(fruitName, amount);
    }

    @Override
    public Integer getQuantity(String fruitName) {
        if (fruitName == null) {
            throw new RuntimeException("Fruit name cannot be null!");
        }
        return fruitDao.getQuantity(fruitName);
    }

    @Override
    public List<Fruit> getAll() {
        return Storage.fruits.entrySet().stream()
                .map(f -> new Fruit(f.getKey(), f.getValue()))
                .collect(Collectors.toList());
    }
}
