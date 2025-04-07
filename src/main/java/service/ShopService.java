package service;

import java.util.List;
import model.FruitTransaction;

public interface ShopService {
    void processTransactions(List<FruitTransaction> transactions);

    void run(String inputFilePath, String outputFilePath);
}
