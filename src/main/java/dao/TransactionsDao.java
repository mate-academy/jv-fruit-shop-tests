package dao;

import java.util.Map;
import model.FruitTransaction;

public interface TransactionsDao {

    void processTransaction(FruitTransaction transaction);

    Map<String, Integer> getAll();

}
