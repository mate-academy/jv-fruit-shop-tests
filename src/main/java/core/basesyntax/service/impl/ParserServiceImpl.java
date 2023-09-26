package core.basesyntax.service.impl;

import core.basesyntax.service.ParserService;
import java.util.ArrayList;
import java.util.List;

public class ParserServiceImpl implements ParserService {
    @Override
    public List<String[]> parseInputData(List<String> inputData) {
        final int numberOfTitleLines = 1;
        final String transactionSeparator = ",";
        List<String[]> transactionsList = new ArrayList<>();
        List<String> inputDataList = inputData.stream()
                                              .skip(numberOfTitleLines)
                                              .toList();
        for (String action : inputDataList) {
            String[] transaction = action.trim().split(transactionSeparator);
            transactionsList.add(transaction);
        }
        return transactionsList;
    }
}
