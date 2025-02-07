package strategy;

import model.FruitTransaction;

public class BalanceHandler implements TransactionHandler {
  @Override
  public Integer apply(Integer fruit, FruitTransaction transaction) {
    return transaction.getQuantity() + fruit;
  }
}
