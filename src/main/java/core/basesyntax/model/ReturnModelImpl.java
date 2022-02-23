package core.basesyntax.model;

public class ReturnModelImpl implements ReturnModel {
    private static final int OPERATION_INDEX = 0;
    private static final int NAME_INDEX = 1;
    private static final int AMOUNT_INDEX = 2;

    @Override
    public FruitModel getModel(String[] line) {
        if (elementIsEmptyOrNull(line[OPERATION_INDEX])) {
            throw new RuntimeException("Operation is empty");
        } else if (elementIsEmptyOrNull(line[NAME_INDEX])) {
            throw new RuntimeException("Fruit name is empty");
        } else if (elementIsEmptyOrNull(line[AMOUNT_INDEX])) {
            throw new RuntimeException("Amount is empty");
        }
        char[] characterOfAmount = line[AMOUNT_INDEX].toCharArray();
        for (char symbol: characterOfAmount) {
            if (!Character.isDigit(symbol)) {
                throw new RuntimeException("Incorrect amount " + line[AMOUNT_INDEX]);
            }
        }
        return new FruitModel(line[NAME_INDEX], Integer.valueOf(line[AMOUNT_INDEX]));
    }

    @Override
    public boolean elementIsEmptyOrNull(String element) {
        return element == null || element.isEmpty();
    }
}
