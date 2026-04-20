package core.basesyntax.service;

import core.basesyntax.model.Transaction;
import java.util.List;
import java.util.Map;

public interface ShopService {
    void process(List<Transaction> transactions);
    
    Map<String, Integer> getData();
}
