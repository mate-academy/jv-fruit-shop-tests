package core.basesyntax.service.impl;

import core.basesyntax.dao.ProductDao;
import core.basesyntax.service.ReportService;
import java.util.stream.Collectors;

public class ReportServiceImpl implements ReportService {
    public static final String HEADER_IN_FILE = "fruit,quantity";
    public static final String FIELDS_DELIMITER = ",";
    public static final String NEW_LINE = System.lineSeparator();
    private final ProductDao productDao;

    public ReportServiceImpl(ProductDao productDao) {
        this.productDao = productDao;
    }

    @Override
    public String create() {
        return productDao.getAll().entrySet().stream()
                .map(e -> e.getKey() + FIELDS_DELIMITER + e.getValue())
                .sorted()
                .collect(Collectors.joining(NEW_LINE, HEADER_IN_FILE + NEW_LINE, ""));
    }
}
