package strategy;

import model.FruitTransaction;

public interface OperationHandler {
    void updateDatabase(FruitTransaction transaction);
}
