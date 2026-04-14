package mate.academy.fruitshop.service;

import java.util.List;
import mate.academy.fruitshop.model.FruitTransaction;

public interface DataConverter {
    List<FruitTransaction> convertToTransaction(List<String> data);
}
