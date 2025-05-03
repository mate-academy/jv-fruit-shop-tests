package core.basesyntax.service.impl;

import core.basesyntax.dao.ActionsDao;
import core.basesyntax.service.ReportMaker;
import java.util.Map;

public class ReportMakerImpl implements ReportMaker {
    private ActionsDao actionsDao;

    public ReportMakerImpl(ActionsDao actionsDao) {
        this.actionsDao = actionsDao;
    }

    @Override
    public String makeReport() {
        StringBuilder builder = new StringBuilder();
        builder.append("fruit,quantity");
        for (Map.Entry<String, Integer> entry : actionsDao.getAllFruits()) {
            builder.append(System.lineSeparator() + entry.getKey() + "," + entry.getValue());
        }
        return builder.toString();
    }
}
