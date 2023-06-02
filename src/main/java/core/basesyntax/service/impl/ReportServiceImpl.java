package core.basesyntax.service.impl;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.service.ReportService;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ReportServiceImpl implements ReportService {
    private final String caption;
    private final String separator;
    private final StorageDao storageDao;

    public ReportServiceImpl(String caption, String separator) {
        this.caption = caption;
        this.separator = separator;
        this.storageDao = new StorageDaoImpl();
    }

    @Override
    public List<String> generateReport() {
        List<String> report = new ArrayList<>();
        report.add(caption);
        Map<String, Integer> fruitMap = storageDao.getAllFruits();
        for (Map.Entry<String, Integer> entry : fruitMap.entrySet()) {
            String fruit = entry.getKey();
            Integer quantity = entry.getValue();
            if (isValidFruit(fruit) && isValidQuantity(quantity)) {
                report.add(fruit + separator + quantity);
            }
        }
        return report;
    }

    private boolean isValidFruit(String fruit) {
        return fruit != null;
    }

    private boolean isValidQuantity(Integer quantity) {
        return quantity != null && quantity > 0;
    }
}
