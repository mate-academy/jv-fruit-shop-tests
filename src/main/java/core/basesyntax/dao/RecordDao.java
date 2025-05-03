package core.basesyntax.dao;

import core.basesyntax.model.Record;
import java.util.List;

public interface RecordDao {
    void add(Record record);

    List<Record> getRecord();
}
