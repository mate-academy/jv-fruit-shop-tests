package core.basesyntax.dao;

import core.basesyntax.model.Record;
import java.util.List;

public interface RecordDao {
    void addRecord(Record record);

    List<Record> getRecords();
}
