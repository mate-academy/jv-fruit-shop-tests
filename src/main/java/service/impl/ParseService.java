package service.impl;

import model.FruitTransaction;

public interface ParseService {
    FruitTransaction parseCsvLine(String line);
}
