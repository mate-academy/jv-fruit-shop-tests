package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.CsvTransactionParser;
import java.util.ArrayList;
import java.util.List;

public class CsvTransactionParserImpl
        implements CsvTransactionParser<List<FruitTransaction>,List<String>> {
    private static final String CSV_SPLITTER = ",";
    private static final int OPERATION_INDEX = 0;
    private static final int PRODUCT_NAME_INDEX = 1;
    private static final int AMOUNT_INDEX = 2;

    @Override
    public List<FruitTransaction> csvParse(List<String> fruitTransactionList) {
        List<FruitTransaction> fruitTransactions = new ArrayList<>();
        int csvColumnCount = 0;
        for (int i = 0; i < fruitTransactionList.size(); i++) {
            String[] singleDataLine = fruitTransactionList.get(i).split(CSV_SPLITTER);
            if (i == 0) {
                csvColumnCount = singleDataLine.length;
                continue;
            } else if (csvColumnCount != singleDataLine.length) {
                throw new RuntimeException("Incorrect number of data in row");
            }
            try {
                Integer.parseInt(singleDataLine[AMOUNT_INDEX].trim());
            } catch (NumberFormatException nfException) {
                throw new RuntimeException("Wrong input type, int type expected");
            }
            if (!FruitTransaction.Operation.contains(singleDataLine[OPERATION_INDEX].trim())) {
                throw new RuntimeException("Such operation does not exist");
            }
            fruitTransactions.add(new FruitTransaction(
                    FruitTransaction.Operation.valueOfLabel(singleDataLine[OPERATION_INDEX].trim()),
                    singleDataLine[PRODUCT_NAME_INDEX].trim(),
                    Integer.parseInt(singleDataLine[AMOUNT_INDEX].trim())));
        }
        return fruitTransactions;
    }
}
