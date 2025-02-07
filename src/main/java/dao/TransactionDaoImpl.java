package dao;

import db.Storage;
import java.util.Map;

public class TransactionDaoImpl implements TransactionsDao {

  @Override
  public Map<String, Integer> getAll() {
    return Storage.fruitsStore;
  }

  public Integer getTransactionByName(String fruitName) {
    return Storage.fruitsStore.get(fruitName);
  }

  public void clearTransactions() {
    Storage.fruitsStore.clear();
  }
}
