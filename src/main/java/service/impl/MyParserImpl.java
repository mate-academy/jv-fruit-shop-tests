package service.impl;

import java.util.ArrayList;
import java.util.List;
import model.TransactionDto;
import service.MyParser;
import service.Validator;

public class MyParserImpl implements MyParser<TransactionDto> {
    private static final int OPERATION_TYPE_INDEX = 0;
    private static final int FRUIT_NAME_INDEX = 1;
    private static final int FRUIT_AMOUNT_INDEX = 2;
    private static final String FIRST_LINE_IN_FILE_CSV = "type,fruit,quantity";
    private final Validator validator;

    public MyParserImpl(Validator validator) {
        this.validator = validator;
    }

    @Override
    public List<TransactionDto> parseLines(List<String> lines) {
        if (lines.isEmpty()) {
            throw new RuntimeException("input is empty");
        }
        List<TransactionDto> transactionDtoList = new ArrayList<>();
        if (!lines.get(0).equals(FIRST_LINE_IN_FILE_CSV)) {
            throw new RuntimeException("First line not correct");
        }
        for (int i = 1; i < lines.size(); i++) {
            validator.validate(lines.get(i));
            String[] information = lines.get(i).split(",");
            transactionDtoList.add(new TransactionDto(information[OPERATION_TYPE_INDEX],
                    information[FRUIT_NAME_INDEX],
                    Integer.parseInt(information[FRUIT_AMOUNT_INDEX])));
        }
        return transactionDtoList;
    }
}
