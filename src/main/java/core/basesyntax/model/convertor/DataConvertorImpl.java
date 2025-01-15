package core.basesyntax.model.convertor;

import static java.lang.String.valueOf;

import core.basesyntax.model.FruitTransaction;
import java.util.ArrayList;
import java.util.List;

public class DataConvertorImpl implements DataConvertor {
    @Override
    public List<FruitTransaction> convertToTransaction(List<String> inputReport) {
        List<FruitTransaction> transactions = new ArrayList<>();

        inputReport.stream()
                .skip(1)
                .filter(line -> line != null && !line.isBlank())
                .map(line -> line.split(","))
                .filter(parts -> parts.length == 3 && parts[2].matches("\\d+"))
                .map(parts -> new FruitTransaction(
                        FruitTransaction.Operation.fromCode(valueOf(parts[0].trim())),
                        parts[1].trim(),
                        Integer.parseInt(parts[2].trim())
                ))
                .forEach(transactions::add);

        return transactions;
    }

}
