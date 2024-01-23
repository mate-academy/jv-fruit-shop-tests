package core.basesyntax.service.convert;

import core.basesyntax.model.FruitTransaction;
import java.util.ArrayList;
import java.util.List;

public class ConvertDataImpl implements ConvertData {
    private List<FruitTransaction> fruitTransactions = new ArrayList<>();

    @Override
    public List<FruitTransaction> convert(List<String> data) {
        if (data == null) {
            throw new RuntimeException("No data");
        }
        for (String stringData : data) {
            if (!stringData.contains(",")) {
                throw new RuntimeException("Wrong string pattern");
            }
            String[] temporaryArr = stringData.split(",");
            fruitTransactions.add(new FruitTransaction(temporaryArr[0],
                                                        temporaryArr[1],
                                                        Integer.parseInt(temporaryArr[2])));
        }
        return fruitTransactions;
    }
}
