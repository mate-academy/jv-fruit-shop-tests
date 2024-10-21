package service;

import java.util.List;
import model.FruitTransaction;
import strategy.OperationHandler;

public interface ShopService {
    void process(List<FruitTransaction> fruitTransactions);

    OperationHandler getHandler(FruitTransaction transaction);
}
