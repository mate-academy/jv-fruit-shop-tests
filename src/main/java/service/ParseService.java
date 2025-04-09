package service;

import model.FruitTransaction;

public interface ParseService {
    FruitTransaction parseCsvLine(String line);
}
