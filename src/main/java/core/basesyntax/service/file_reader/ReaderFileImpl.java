package core.basesyntax.service.file_reader;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class ReaderFileImpl implements ReaderFile {
    private static final String SEPARATOR = ",";
    private static final String FILE_HEADER = "type,fruit,quantity";
    private static final int TYPE_INDEX = 0;
    private static final int FRUIT_INDEX = 1;
    private static final int QUANTITY_INDEX = 2;

    @Override
    public List<FruitTransaction> readFile(String inputFileName) {
        List<FruitTransaction> fruitTransactionList = new ArrayList<>();
        File file = new File(inputFileName);
        try {
            List<String> stringList = Files.readAllLines(file.toPath());
            stringList.remove(FILE_HEADER);

            for (String s : stringList) {
                String[] list = s.split(SEPARATOR);
                Operation operation = Operation.getOperationHandler(list[TYPE_INDEX]);
                String fruit = list[FRUIT_INDEX];
                int quantity = Integer.parseInt(list[QUANTITY_INDEX]);
                if (quantity < 0) {
                    throw new RuntimeException("Quantity can't be less then 0!");
                }
                fruitTransactionList.add(new FruitTransaction(operation, fruit, quantity));
            }
        } catch (IOException e) {
            throw new RuntimeException("Can't read from file named: " + inputFileName, e);
        }
        return fruitTransactionList;
    }
}
