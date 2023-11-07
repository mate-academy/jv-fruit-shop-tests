package fruitshop.service;

import fruitshop.model.FruitTransaction;
import java.util.List;

public interface Parser {
    List<FruitTransaction> parse(List<String> rowsList);
}
