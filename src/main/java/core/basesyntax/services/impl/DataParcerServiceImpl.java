package core.basesyntax.services.impl;

import core.basesyntax.exceptions.NullDataException;
import core.basesyntax.services.DataParcerService;
import core.basesyntax.services.ParametrsValidatorService;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DataParcerServiceImpl implements DataParcerService {
    private static final String VALUE_SEPARATOR = "\\s*,\\s*";
    private static final int ROW_TITLE_INDEX = 1;
    private ParametrsValidatorService parametrValidator;

    public DataParcerServiceImpl(ParametrsValidatorService parametrValidator) {
        this.parametrValidator = parametrValidator;
    }

    @Override
    public List<List<String>> parceDataFromCsv(List<String> nonParcedData) {
        if (nonParcedData == null) {
            throw new NullDataException("Can't parse data when input data is null");
        }
        return nonParcedData.stream()
                .skip(ROW_TITLE_INDEX)
                .filter(s -> parametrValidator.isParametrsNotNull(s))
                .map(String::trim)
                .map(s -> Arrays.stream(s.split(VALUE_SEPARATOR)).collect(Collectors.toList()))
                .filter(l -> parametrValidator.isValidParameters(l))
                .collect(Collectors.toList());
    }
}
