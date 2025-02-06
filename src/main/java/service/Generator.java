package service;

import java.util.Map;

public interface Generator {
    String generateReport(Map<String, Integer> allTransactions);
}
