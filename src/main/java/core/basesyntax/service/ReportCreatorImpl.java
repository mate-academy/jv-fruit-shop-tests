package core.basesyntax.service;

import core.basesyntax.dao.RecordDao;
import core.basesyntax.dao.RecordDaoImpl;
import core.basesyntax.model.Record;
import core.basesyntax.report.FruitBalance;
import core.basesyntax.strategy.ActivityStrategy;
import core.basesyntax.strategy.activity.ActivityHandler;
import java.util.List;
import java.util.stream.Collectors;

public class ReportCreatorImpl implements ReportCreator {
    private final ActivityStrategy activityStrategy;
    private final RecordDao recordDao = new RecordDaoImpl();

    public ReportCreatorImpl(ActivityStrategy activityStrategy) {
        this.activityStrategy = activityStrategy;
    }

    @Override
    public List<String> createReport() {
        List<Record> listRecords = recordDao.getRecord();
        for (Record record:listRecords) {
            ActivityHandler activityHandler = activityStrategy.get(record.getActivityType());
            activityHandler.apply(record);
        }
        return FruitBalance.FRUIT_BALANCE
                .entrySet()
                .stream()
                .map(string -> string.getKey() + "," + string.getValue())
                .collect(Collectors.toList());
    }
}
