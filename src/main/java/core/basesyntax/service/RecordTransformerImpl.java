package core.basesyntax.service;

import core.basesyntax.dao.RecordDao;
import core.basesyntax.dao.RecordDaoImpl;
import core.basesyntax.model.Record;
import core.basesyntax.validation.RecordValidatorImpl;
import java.util.List;

public class RecordTransformerImpl implements RecordTransformer {
    private static final String SEPARATOR = ",";
    private static final int ACTIVITY_INDEX = 0;
    private static final int FRUIT_INDEX = 1;
    private static final int AMOUNT_INDEX = 2;

    @Override
    public void transform(List<String> stringRecords) {
        RecordDao recordDao = new RecordDaoImpl();
        for (String string: stringRecords) {
            String[] recordData = string.split(SEPARATOR);
            RecordValidatorImpl recordValidator = new RecordValidatorImpl();
            recordValidator.isValidInput(recordData);
            String activityType = recordData[ACTIVITY_INDEX];
            String fruitName = recordData[FRUIT_INDEX];
            int amount = Integer.parseInt(recordData[AMOUNT_INDEX]);
            recordDao.add(new Record(activityType, fruitName, amount));
        }
    }
}
