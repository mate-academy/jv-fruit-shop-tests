package service;

import core.basesyntax.model.ParseLine;
import core.basesyntax.service.InputDataValidatorImpl;
import java.util.LinkedList;
import java.util.List;

public interface LineParser {
    InputDataValidator inputValidator = new InputDataValidatorImpl();
    List<ParseLine> parseLineList = new LinkedList<>();

    List<ParseLine> lineParcer(List<String> input);
}

