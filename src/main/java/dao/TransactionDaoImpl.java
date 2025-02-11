package dao;

import db.Storage;
import java.util.Map;

public class TransactionDaoImpl implements TransactionsDao {

  @Override
  public Map<String, Integer> getAll() {
    return Map.copyOf(Storage.fruitsStore);
  }

  public Integer getTransactionByName(String fruitName) {
    return Storage.fruitsStore.getOrDefault(fruitName, 0);
  }

  public void saveTransaction(String fruitName, int quantity) {
    Storage.fruitsStore.put(fruitName, quantity);
  }

  public void clearTransactions() {
    Storage.fruitsStore.clear();
  }
}