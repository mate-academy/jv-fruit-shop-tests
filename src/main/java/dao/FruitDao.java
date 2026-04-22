package dao;

public interface FruitDao {
    void add(String fruit, int quantity);

    int getQuantity(String fruit);

    void update(String fruit, int newQuantity);

    java.util.Map<String, Integer> getAll();
}
