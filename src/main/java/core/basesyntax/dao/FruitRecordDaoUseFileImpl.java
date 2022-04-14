package core.basesyntax.dao;

import core.basesyntax.bd.Storage;
import core.basesyntax.model.FruitRecordDto;

public class FruitRecordDaoUseFileImpl implements FruitRecordDao {

    @Override
    public void add(FruitRecordDto fruitRecordDto) {
        Storage.records.add(fruitRecordDto);
    }
}
