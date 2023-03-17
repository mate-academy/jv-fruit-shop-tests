package core.basesyntax.services.impl;

import core.basesyntax.exceptions.NullDataException;
import core.basesyntax.services.DataParcerService;
import core.basesyntax.services.ParametrsValidatorService;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DataParcerServiceImpl implements DataParcerService {
    private String valueSeparator;
    private int rowTitleIndex;
    private ParametrsValidatorService parametrValidator;

    public DataParcerServiceImpl(String valueSeparator, int rowTitleIndex,
                                 ParametrsValidatorService parametrValidator) {
        this.valueSeparator = "\\s*" + valueSeparator + "\\s*";
        this.rowTitleIndex = rowTitleIndex;
        this.parametrValidator = parametrValidator;
    }

    @Override
    public List<List<String>> parceDataFromCsv(List<String> nonParcedData) {
        if (nonParcedData == null) {
            throw new NullDataException("Can't parse data when input data is null");
        }
        return nonParcedData.stream()
                .skip(rowTitleIndex)
                .filter(s -> parametrValidator.isParametrsNotNull(s))
                .map(String::trim)
                .map(s -> Arrays.stream(s.split(valueSeparator)).collect(Collectors.toList()))
                .filter(l -> parametrValidator.isValidParameters(l))
                .collect(Collectors.toList());
    }
}
