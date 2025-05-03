package core.basesyntax.parser;

import core.basesyntax.errors.InputDataEqualNullException;
import core.basesyntax.fruittransaction.FruitTransaction;
import java.util.ArrayList;
import java.util.List;

public class TransactionParserImpl implements TransactionParser {
    private static final int INDEX_FOR_OPERATION = 0;
    private static final int INDEX_FOR_FRUITS = 1;
    private static final int INDEX_FOR_QUANTITY = 2;

    @Override
    public List<FruitTransaction> parse(List<String> lines) {
        if (lines == null) {
            throw new InputDataEqualNullException("Data equal null");
        }
        List<FruitTransaction> completeParseList = new ArrayList<>();
        for (String line : lines) {
            String[] splitLineFromLines = line.split(",");
            FruitTransaction transaction = new FruitTransaction();
            FruitTransaction.Operation operationByCode = FruitTransaction.Operation
                    .getOperationByCode(splitLineFromLines[INDEX_FOR_OPERATION]);
            transaction.setOperation(operationByCode);
            transaction
                    .setFruit(splitLineFromLines[INDEX_FOR_FRUITS]);
            transaction
                    .setQuantity(Integer.parseInt(splitLineFromLines[INDEX_FOR_QUANTITY]));
            completeParseList.add(transaction);
        }
        return completeParseList;
    }
}

