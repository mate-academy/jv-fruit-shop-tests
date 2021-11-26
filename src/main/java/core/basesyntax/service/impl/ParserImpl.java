package core.basesyntax.service.impl;

import core.basesyntax.model.TransactionDto;
import core.basesyntax.service.Parser;
import core.basesyntax.service.Validator;
import java.util.ArrayList;
import java.util.List;

public class ParserImpl implements Parser<TransactionDto> {
    private Validator validator;

    public ParserImpl(Validator validator) {
        this.validator = validator;
    }

    @Override
    public List<TransactionDto> parseLines(List<String> lines) {
        List<TransactionDto> transactionDtoList = new ArrayList<>();
        lines.remove(0);
        for (String line : lines) {
            validator.validate(line);
            String[] information = line.split(",");
            transactionDtoList.add(new TransactionDto(information[0],
                    information[1],
                    Integer.parseInt(information[2])));
        }
        return transactionDtoList;
    }
}
