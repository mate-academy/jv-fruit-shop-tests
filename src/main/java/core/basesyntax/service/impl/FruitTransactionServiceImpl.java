package core.basesyntax.service.impl;

import core.basesyntax.dao.FruitTranzactionDao;
import core.basesyntax.dao.impl.FruitTransactionDaoImpl;
import core.basesyntax.exception.ValidationException;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.FruitTransactionService;
import java.util.Arrays;
import java.util.List;

public class FruitTransactionServiceImpl implements FruitTransactionService {
    private static final int INDEX_OPERATION = 0;
    private static final int INDEX_FRUIT = 1;
    private static final int INDEX_QUANTITY = 2;
    private static final String COMMA = ",";
    private final FruitTranzactionDao fruitTranzactionDao = new FruitTransactionDaoImpl();
    
    @Override
    public void createNewFruitTransaction(List<String> fileData) {
        for (String data : fileData) {
            FruitTransaction fruitTransaction = new FruitTransaction();
            String[] dataValue = data.split(COMMA);
            checkValidOperationType(dataValue[INDEX_OPERATION]);
            checkValidFruitName(dataValue[INDEX_FRUIT]);
            checkValidQuantity(Integer.parseInt(dataValue[INDEX_QUANTITY]));
            
            for (Operation operation : Operation.values()) {
                if (operation.getCode().equals(dataValue[INDEX_OPERATION])) {
                    fruitTransaction.setOperation(operation);
                    break;
                }
            }
            fruitTransaction.setFruit(dataValue[INDEX_FRUIT]);
            fruitTransaction.setQuantity(Integer.parseInt(dataValue[INDEX_QUANTITY]));
            fruitTranzactionDao.add(fruitTransaction);
        }
    }
    
    private void checkValidFruitName(String data) {
        char[] characters = data.toCharArray();
        for (Character ch : characters) {
            if (!Character.isAlphabetic(ch)) {
                throw new ValidationException(
                        "The name of the fruit must contain only letters but was: " + data);
            }
        }
    }
    
    private void checkValidOperationType(String data) {
        String[] arrayOperations = new String[Operation.values().length];
        int index = 0;
        for (Operation operation : Operation.values()) {
            arrayOperations[index] = operation.getCode();
            index++;
        }
      
        for (String operation : arrayOperations) {
            if (data.equals(operation)) {
                return;
            }
        }
        throw new ValidationException("No such operation expected: "
                + Arrays.toString(arrayOperations) + ", but was: " + data);
    }
    
    private void checkValidQuantity(int quantity) {
        if (quantity < 0) {
            throw new ValidationException("Quantity cannot be negative, but was: " + quantity);
        }
    }
}
