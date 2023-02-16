package Day22;

public class XORException extends Exception {
    public XORException(String methondname, boolean x, boolean y, boolean z) {
        super("\nERROR in " + methondname + ":\nx: " + x + "\ny: " + y + "\nz: " + z);
    }
}
