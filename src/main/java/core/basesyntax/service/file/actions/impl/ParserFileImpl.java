package core.basesyntax.service.file.actions.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.file.actions.ParserFile;
import java.util.ArrayList;
import java.util.List;

public class ParserFileImpl implements ParserFile {
    private static final String SEPARATOR = ",";
    private static final int OPERATION_INDEX = 0;
    private static final int FRUIT_INDEX = 1;
    private static final int QUANTITY_INDEX = 2;

    @Override
    public List<FruitTransaction> parseFruitShop(List<String> transactions) {
        List<FruitTransaction> fruitTransactions = new ArrayList<>();

        for (String transaction : transactions) {
            FruitTransaction fruitTransaction = extractFruitShop(transaction);
            fruitTransactions.add(fruitTransaction);
        }
        return fruitTransactions;
    }

    private FruitTransaction extractFruitShop(String infoFileFruitShop) {
        String[] fieldsFruitShop = infoFileFruitShop.split(SEPARATOR);
        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setOperation(FruitTransaction.Operation
                .getOperationFromCode(fieldsFruitShop[OPERATION_INDEX]
                .trim()));
        fruitTransaction.setFruit(fieldsFruitShop[FRUIT_INDEX].trim());
        fruitTransaction.setQuantity(Integer.parseInt(fieldsFruitShop[QUANTITY_INDEX]
                .trim()));
        return fruitTransaction;
    }
}
