package core.basesyntax.service.impl;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.impl.FruitDaoImpl;
import core.basesyntax.model.FruitInputData;
import core.basesyntax.service.DataHandlerService;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.StorageUpdateHandler;
import core.basesyntax.strategy.impl.OperationStrategyImpl;
import java.util.Arrays;
import java.util.List;

public class DataHandlerServiceImpl implements DataHandlerService {
    private static final String CALCULATE_INPUT_NULL_MESSAGE = "Input data cannot be null!";
    private static final String REPORT_COLUMNS = "fruit,quantity";
    private static final String LINE_SEPARATOR = "\n";
    private static final String EMPTY_STRING = "";
    private static final int INDEX_OF_REPORT_COLUMNS = 1;
    private static final String SEPARATE_SYMBOL = ",";
    private final OperationStrategy operationStrategy;
    private final FruitDao fruitDao;

    public DataHandlerServiceImpl(List<StorageUpdateHandler> storageUpdateHandlers) {
        operationStrategy = new OperationStrategyImpl(storageUpdateHandlers);
        fruitDao = new FruitDaoImpl();
    }

    @Override
    public String calculateInputData(String inputData) {
        if (inputData == null) {
            throw new RuntimeException(CALCULATE_INPUT_NULL_MESSAGE);
        }
        if (inputData.length() == 0) {
            return EMPTY_STRING;
        }
        List<String> productTransactionsList = getOperationsList(inputData);
        for (String productTransaction : productTransactionsList) {
            addProductTransaction(productTransaction);
        }
        return getFinalReport();
    }

    private String getFinalReport() {
        StringBuilder reportBuilder = new StringBuilder(REPORT_COLUMNS);
        fruitDao.getAll().forEach((key, value) ->
                reportBuilder.append(LINE_SEPARATOR)
                        .append(key)
                        .append(SEPARATE_SYMBOL)
                        .append(value));
        return reportBuilder + LINE_SEPARATOR;
    }

    private void addProductTransaction(String productTransaction) {
        FruitInputData fruitInputData = new FruitInputData();
        String[] separatedTransaction = productTransaction.split(SEPARATE_SYMBOL);
        fruitInputData.setOperationCode(separatedTransaction[0]);
        fruitInputData.setFruitName(separatedTransaction[1]);
        fruitInputData.setAmount(Integer.parseInt(separatedTransaction[2]));
        operationStrategy.manageStorageCells(fruitInputData);
    }

    private List<String> getOperationsList(String inputData) {
        return Arrays.stream(inputData.split(LINE_SEPARATOR))
                .skip(INDEX_OF_REPORT_COLUMNS)
                .toList();
    }
}
