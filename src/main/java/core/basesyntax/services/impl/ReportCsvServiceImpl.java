package core.basesyntax.services.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.ValidationDataException;
import core.basesyntax.services.ReportCsvService;
import core.basesyntax.util.ConstantsForCsvParse;
import java.util.Map;

public class ReportCsvServiceImpl implements ReportCsvService {
    private static String TITLE_VALUE = "fruit,quantity";
    private static final int TITLE_VALUE_SIZE = 1;
    private Storage fruitDB;

    public ReportCsvServiceImpl(Storage fruitDB) {
        this.fruitDB = fruitDB;
    }

    @Override
    public String[] createReport() {
        if (fruitDB == null) {
            throw new ValidationDataException("ReportCsvService error! Storage can't be null");
        }
        Map<String, Integer> reportMap = fruitDB.getStorageFruits();
        if (reportMap.isEmpty()) {
            throw new ValidationDataException("ReportCsvService error! Storage can't be empty");
        }
        String[] reportWrite = new String[reportMap.size() + TITLE_VALUE_SIZE];
        int index = 0;
        reportWrite[index] = TITLE_VALUE;
        for (Map.Entry<String, Integer> entry : reportMap.entrySet()) {
            String key = entry.getKey();
            Integer value = entry.getValue();
            validateData(key, value);
            ++index;
            reportWrite[index] = key + ConstantsForCsvParse.COMMA + value;
        }
        return reportWrite;
    }

    private void validateData(String key, Integer value) {
        if (key == null) {
            throw new ValidationDataException("ReportCsvService error! "
                    + "First Value(nameOfGood) can't be null");
        }
        if (key.isEmpty()) {
            throw new ValidationDataException("ReportCsvService error! "
                    + "First Value(nameOfGood) can't be empty");
        }
        if (value == null) {
            throw new ValidationDataException("ReportCsvService error! "
                    + "Second Value(value) can't be null");
        }
    }
}
