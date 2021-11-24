package core.basesyntax.service;

import core.basesyntax.dao.RecordDao;
import core.basesyntax.dao.RecordDaoImpl;
import core.basesyntax.model.Record;
import core.basesyntax.validation.InputValidator;
import core.basesyntax.validation.InputValidatorImpl;
import java.util.List;

public class RecordParserImpl implements RecordParser {
    private static final String SEPARATOR = ",";
    private static final int OPERATION_INDEX = 0;
    private static final int FRUIT_INDEX = 1;
    private static final int AMOUNT_INDEX = 2;

    @Override
    public void parseRecords(List<String> linesFromFile) {
        if (linesFromFile.isEmpty()) {
            throw new RuntimeException("Cannot parse empty list!!!");
        }
        RecordDao recordDao = new RecordDaoImpl();
        for (String line: linesFromFile) {
            String[] recordData = line.split(SEPARATOR);
            InputValidator inputValidator = new InputValidatorImpl();
            inputValidator.isValidInput(recordData);
            String operationType = recordData[OPERATION_INDEX];
            String fruitName = recordData[FRUIT_INDEX];
            int amount = Integer.parseInt(recordData[AMOUNT_INDEX]);
            recordDao.addRecord(new Record(operationType, fruitName, amount));
        }
    }
}
