package core.basesyntax.service.imp;

import core.basesyntax.model.GoodsOperation;
import core.basesyntax.service.CsvParseService;
import java.util.ArrayList;
import java.util.List;

public class CsvParser implements CsvParseService {
    private static final String LINE_DIVIDER = ",";
    private static final int OPERATION_TYPE_INDEX = 0;
    private static final int OPERATION_ITEM_INDEX = 1;
    private static final int OPERATION_QUANTITY_INDEX = 2;
    private static final String CSV_COLUMN_NAMES_PATTERN = "type,fruit,quantity";

    @Override
    public List<GoodsOperation> listOperationsFromCsv(List<String> csvOperationsList) {
        List<GoodsOperation> operationsList = new ArrayList<>();
        int startLine = 0;
        String firstLine = csvOperationsList.get(0);
        if (firstLine.contains(CSV_COLUMN_NAMES_PATTERN)) {
            startLine = 1;
        }
        for (int i = startLine; i < csvOperationsList.size(); i++) {
            String csvOperation = csvOperationsList.get(i);
            GoodsOperation operation = convertToOperationFromCsv(csvOperation);
            operationsList.add(operation);
        }
        return operationsList;
    }

    @Override
    public GoodsOperation convertToOperationFromCsv(String operation) {
        String [] line = operation.split(LINE_DIVIDER);
        GoodsOperation.TransactionType type = GoodsOperation.TransactionType
                .getByCode(line[OPERATION_TYPE_INDEX]);
        String item = line[OPERATION_ITEM_INDEX];
        int quantity = Integer.parseInt(line[OPERATION_QUANTITY_INDEX]);
        return new GoodsOperation(type, item, quantity);
    }
}
