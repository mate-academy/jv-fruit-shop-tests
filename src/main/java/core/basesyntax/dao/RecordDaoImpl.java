package core.basesyntax.dao;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Record;
import java.util.List;

public class RecordDaoImpl implements RecordDao {
    @Override
    public void add(Record record) {
        Storage.records.add(record);
    }

    @Override
    public List<Record> getRecord() {
        return Storage.records;
    }
}
