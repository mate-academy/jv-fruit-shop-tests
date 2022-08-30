package core.basesyntax.service.impl;

import core.basesyntax.dao.FruitsDao;
import core.basesyntax.service.ReportCreator;
import java.util.ArrayList;
import java.util.Map;

public class ReportCreatorImpl implements ReportCreator {
    private String header;
    private FruitsDao fruitsDao;

    public ReportCreatorImpl(String header, FruitsDao fruitsDao) {
        if (header == null) {
            throw new RuntimeException("Header can't be null");
        }
        this.header = header;
        if (fruitsDao == null) {
            throw new RuntimeException("fruitsDao can't be null");
        }
        this.fruitsDao = fruitsDao;
    }

    @Override
    public String create(Map<String, Integer> data) {
        if (data == null) {
            throw new IllegalArgumentException("Input data can't be null");
        }
        ArrayList<String> fruits = new ArrayList<>(fruitsDao.getAll().keySet());
        StringBuilder reportBuilder = new StringBuilder(header);
        for (String fruit : fruits) {
            reportBuilder.append(System.lineSeparator())
                    .append(fruit)
                    .append(",")
                    .append(data.get(fruit));
        }
        return reportBuilder.toString();
    }
}
