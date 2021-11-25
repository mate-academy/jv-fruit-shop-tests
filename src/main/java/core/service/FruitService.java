package core.service;

import core.model.Fruit;
import core.model.TransactionDto;
import java.util.List;

public interface FruitService {
    List<Fruit> changeBalance(List<TransactionDto> transactions);
}
