package service;

import model.FruitTransaction;

public class FruitParserImpl implements FruitParser {
    public static final int OPERATION_INDEX = 0;
    public static final int FRUIT_INDEX = 1;
    public static final int QUANTITY_INDEX = 2;

    @Override
    public FruitTransaction parseFruitTransaction(String line) {
        String[] fields = line.split(" ");
        FruitTransaction fruitTransaction = new FruitTransaction();
        for (String field : fields) {
            if (field.isEmpty()) {
                continue;
            }
            String[] split = field.split(",");
            if (split[OPERATION_INDEX] == null
                    || split[OPERATION_INDEX].isEmpty()
                    || split[FRUIT_INDEX] == null
                    || split[FRUIT_INDEX].isEmpty()
                    || split[QUANTITY_INDEX] == null
                    || split[QUANTITY_INDEX].isEmpty()) {
                throw new NullPointerException("This line cannot be empty");
            }
            fruitTransaction.setOperation(FruitTransaction.Operation.findByAbbreviation(split[0]));
            fruitTransaction.setFruit(split[1]);
            fruitTransaction.setQuantity(Integer.parseInt(split[2]));
        }
        return fruitTransaction;
    }
}
