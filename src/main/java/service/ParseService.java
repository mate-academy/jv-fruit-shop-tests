package service;

import java.util.List;
import model.FruitTransaction;

public interface ParseService {
    FruitTransaction parseCsvLine(String line);

    List<FruitTransaction> parseList(List<String> filePath);
}
