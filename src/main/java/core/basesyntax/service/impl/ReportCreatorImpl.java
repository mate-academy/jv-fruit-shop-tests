package core.basesyntax.service.impl;

import core.basesyntax.dao.ShopDao;
import core.basesyntax.dao.ShopDaoImpl;
import core.basesyntax.service.ReportCreator;

public class ReportCreatorImpl implements ReportCreator {
    private final ShopDao shopDao = new ShopDaoImpl();

    @Override
    public String createReport() {
        StringBuilder report = new StringBuilder();
        report.append("fruit, quantity");
        report.append(System.lineSeparator());
        report.append(shopDao.getAll());
        return report.toString();
    }
}
