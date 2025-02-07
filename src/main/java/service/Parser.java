package service;

import java.util.List;
import model.FruitTransaction;

public interface Parser {

  List<FruitTransaction> parseTransactions(List<String> lines);
}
