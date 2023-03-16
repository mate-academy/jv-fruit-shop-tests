package core.basesyntax.service.impl;

import core.basesyntax.model.TransactionDto;
import core.basesyntax.service.TransactionParser;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class TransactionParserImpl implements TransactionParser {
    private static final String SEPARATOR = ",";
    private static final int ACTION_INDEX = 0;
    private static final int FRUIT_INDEX = 1;
    private static final int QUANTITY_INDEX = 2;
    private static final String VALID_DATA = "[bprs],[a-z]*,[0-9]*";

    @Override
    public List<TransactionDto> parserTransactionOperation(List<String> line) {
        if (line.size() < 1 || line == null) {
            throw new NullPointerException("File is empty");
        }
        List<TransactionDto> parsedTransaction = new ArrayList<>();
        isValidData(line);
        for (int i = 1; i < line.size(); i++) {
            TransactionDto transactionDto = new TransactionDto();
            String[] splited = line.get(i).split(SEPARATOR);
            transactionDto.setOperation(TransactionDto.Operation.getByCode(splited[ACTION_INDEX]));
            transactionDto.setFruitName(splited[FRUIT_INDEX]);
            transactionDto.setQuantity(Integer.parseInt(splited[QUANTITY_INDEX]));
            parsedTransaction.add(transactionDto);
        }
        return parsedTransaction;
    }

    public void isValidData(List<String> inputData) {
        for (String str : inputData) {
            if (!Pattern.matches(VALID_DATA, str) && !str.equals("type,fruit,quantity")) {
                throw new RuntimeException("Invalid input data");
            }
        }
    }
}
