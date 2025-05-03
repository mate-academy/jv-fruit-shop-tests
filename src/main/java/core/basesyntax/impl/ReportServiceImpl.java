package core.basesyntax.impl;

import core.basesyntax.dao.AccountDao;
import core.basesyntax.dao.AccountDaoImpl;
import core.basesyntax.service.ReportService;
import java.util.Map;

public class ReportServiceImpl implements ReportService {
    private static final String HEADER = "fruit,quantity";
    private static final String COMA = ",";
    private final AccountDao accountDao = new AccountDaoImpl();

    @Override
    public String createReport() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(HEADER);
        for (Map.Entry<String, Integer> entry : accountDao.getAll().entrySet()) {
            stringBuilder.append(System.lineSeparator())
                    .append(entry.getKey()).append(COMA).append(entry.getValue());
        }
        return stringBuilder.toString();
    }
}
