package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.TransactionService;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TransactionServiceImpl implements TransactionService {
    private static final String REGEX = "[a-z],[a-z]+,\\d+";
    private static final int OPERATION_TYPE_INDEX = 0;
    private static final int FRUIT_NAME_INDEX = 1;
    private static final int QUANTITY_INDEX = 2;
    private static final String COMMA = ",";
    private final Pattern pattern = Pattern.compile(REGEX, Pattern.CASE_INSENSITIVE);

    @Override
    public List<FruitTransaction> createTransactions(List<String> fileLines) {
        if (fileLines == null || fileLines.isEmpty()) {
            throw new RuntimeException(
                    "Can't create transactions from NULL or empty data source");
        }
        List<FruitTransaction> fruitTransactions = new ArrayList<>();
        for (String fileLine : fileLines) {
            if (fileLine.isEmpty()) {
                continue;
            }
            checkIfValidString(fileLine);

            String[] transactionInfo = fileLine.split(COMMA);
            fruitTransactions.add(new FruitTransaction(
                    FruitTransaction.Operation.getByCode(transactionInfo[OPERATION_TYPE_INDEX]),
                    transactionInfo[FRUIT_NAME_INDEX].toLowerCase(),
                    Integer.parseInt(transactionInfo[QUANTITY_INDEX])));
        }
        return fruitTransactions;
    }

    private void checkIfValidString(String lineFromFile) {
        Matcher matcher = pattern.matcher(lineFromFile);
        if (!matcher.matches()) {
            throw new RuntimeException("Can't create transaction"
                    + " as we get invalid data "
                    + "\'" + lineFromFile + "\'");
        }
    }
}


