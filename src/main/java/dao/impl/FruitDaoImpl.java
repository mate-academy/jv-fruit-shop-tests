package dao.impl;

import dao.FruitDao;
import db.FruitStorage;
import java.util.HashMap;
import java.util.Map;

public class FruitDaoImpl implements FruitDao {
    @Override
    public void add(String fruit, int quantity) {
        // merge зручний для початкового додавання або поставок
        FruitStorage.fruits.merge(fruit, quantity, Integer::sum);
    }

    @Override
    public int getQuantity(String fruit) {
        return FruitStorage.fruits.getOrDefault(fruit, 0);
    }

    @Override
    public void update(String fruit, int newQuantity) {
        // Цей метод просто встановлює фінальне значення
        FruitStorage.fruits.put(fruit, newQuantity);
    }

    @Override
    public Map<String, Integer> getAll() {
        // Повертаємо нову мапу, щоб ніхто зовні не міг видалити дані зі сховища
        return new HashMap<>(FruitStorage.fruits);
    }
}
