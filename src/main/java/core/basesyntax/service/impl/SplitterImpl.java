package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.Splitter;
import java.util.List;
import java.util.stream.Collectors;

public class SplitterImpl implements Splitter {
    private static final int FIRST_ELEMENT_FROM_LINE = 0;
    private static final int SECOND_ELEMENT_FROM_LINE = 1;
    private static final int THIRD_ELEMENT_FROM_LINE = 2;
    private static final String SPLIT_VLAUE = ",";

    @Override
    public List<FruitTransaction> createTransactionList(List<String> info) {
        return info.stream()
                .skip(1)
                .map(this::splitInfo)
                .collect(Collectors.toList());
    }
    
    private FruitTransaction splitInfo(String information) {
        FruitTransaction fruitTransaction = new FruitTransaction();
        String[] s = information.split(SPLIT_VLAUE);
        if (s[1] == null || s[1].equals("")) {
            throw new RuntimeException("Invalid fruit name");
        }
        fruitTransaction.setOperation(
                fruitTransaction.setValueForOperation(s[FIRST_ELEMENT_FROM_LINE]));
        fruitTransaction.setFruit(s[SECOND_ELEMENT_FROM_LINE]);
        fruitTransaction.setQuantity(Integer.parseInt(s[THIRD_ELEMENT_FROM_LINE]));
        return fruitTransaction;
    }
}
