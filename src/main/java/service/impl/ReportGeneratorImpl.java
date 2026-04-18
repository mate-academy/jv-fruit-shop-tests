package service.impl;

import dao.FruitDao;
import db.FruitStorage;
import java.util.Map;
import service.ReportGenerator;

public class ReportGeneratorImpl implements ReportGenerator {
    public ReportGeneratorImpl(FruitDao fruitDao) {
    }

    @Override
    public String getReport() {
        String header = "fruit,quantity";

        String data = FruitStorage.fruits.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(entry -> entry.getKey() + "," + entry.getValue())
                .collect(java.util.stream.Collectors.joining(System.lineSeparator()));

        return data.isEmpty() ? header : header + System.lineSeparator() + data;
    }
}
