package core.basesyntax.service;

import core.basesyntax.dao.RecordDao;
import core.basesyntax.dao.RecordDaoImpl;
import core.basesyntax.database.Database;
import core.basesyntax.model.Record;
import core.basesyntax.operation.OperationHandler;
import core.basesyntax.operation.OperationStrategy;
import java.util.List;
import java.util.stream.Collectors;

public class ReportCreatorImpl implements ReportCreator {
    private final OperationStrategy operationStrategy;

    public ReportCreatorImpl(OperationStrategy operationStrategy) {
        this.operationStrategy = operationStrategy;
    }

    @Override
    public List<String> createReport() {
        RecordDao recordDao = new RecordDaoImpl();
        List<Record> records = recordDao.getRecords();
        for (Record record: records) {
            OperationHandler operationHandler = operationStrategy
                    .get(record.getOperationType());
            operationHandler.apply(record);
        }
        return Database.FRUIT_BALANCE.entrySet().stream()
                .map(string -> string.getKey() + "," + string.getValue())
                .collect(Collectors.toList());
    }
}
