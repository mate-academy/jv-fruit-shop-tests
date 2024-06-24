package strategy;

import java.util.Map;
import model.FruitTransaction;

public interface OperationHandler {
    Map.Entry<String, Integer> apply(FruitTransaction transaction);
}
