package service;

import java.util.List;
import model.FruitTransaction;

public interface CsvFileReader {
    List<FruitTransaction> read(String filePath, FruitDao fruitDao);
}

