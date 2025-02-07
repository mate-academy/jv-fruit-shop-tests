package strategy;

import model.FruitTransaction;

public class PurchaseHandler implements TransactionHandler {
  @Override
  public Integer apply(Integer fruit, FruitTransaction transaction) {
    return fruit < transaction.getQuantity() ? 0 : fruit - transaction.getQuantity();
  }
}
