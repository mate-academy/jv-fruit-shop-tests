package core.basesyntax.services;

public class TestConstants {
    public static final int ONE_ELEMENT = 1;
    public static final int ZERO_POSITION = 0;
    public static final int TYPICAL_QUANTITY_TEN = 10;
    public static final String FRUIT_APPLE = "apple";

    public static final String CORRECT_LINE_BALANCE = "b,apple,10";
    public static final String CORRECT_LINE_RETURN = "r,apple,10";
    public static final String CORRECT_LINE_PURCHASE = "p,apple,10";
    public static final String CORRECT_LINE_SUPPLY = "s,apple,10";

    public static final String INCORRECT_LINE_LETTERS_INSTEAD_OF_NUMBER = "b,apple,ten";
    public static final String INCORRECT_LINE_ZERO_NUMBER = "b,apple,0";
    public static final String INCORRECT_LINE_NEGATIVE_NUMBER = "b,apple,-10";
    public static final String INCORRECT_LINE_MISSING_QUANTITY = "b,apple";
    public static final String INCORRECT_LINE_MISSING_OPERATION = "apple,10";
    public static final String INCORRECT_LINE_UNKNOWN_OPERATION = "x,apple,10";
    public static final String INCORRECT_LINE_WRONG_SEPARATOR = "b;apple;10";
    public static final String INCORRECT_LINE_EXTRA_ELEMENT = "b,apple,10,extra";
    public static final String INCORRECT_LINE_NULL_ENTRY = null;
    public static final String INCORRECT_LINE_EMPTY_STRING = "";
}
