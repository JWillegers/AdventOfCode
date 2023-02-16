package Day22;

public class IncorrectSizeException extends Exception {
    public IncorrectSizeException(String methodName, int expected, int actual) {
        super("\nERROR in " + methodName + "\nExpected size: " + expected +
                "\nActual size: " + actual);
    }
}
