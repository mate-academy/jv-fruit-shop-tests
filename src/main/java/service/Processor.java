package service;

import java.util.List;
import model.FruitTransaction;

public interface Processor {

  void processCsv(List<FruitTransaction> transactions);
}
