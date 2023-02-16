package Day21;

public class PartA {
    private int p1field = 3;
    private int p2field = 5;
    private final int maxDice = 100;
    private int counter = 0;
    private int p1Score = 0;
    private int p2Score = 0;

    public static void main(String[] args) {
        PartA part = new PartA();
        part.solution(true, 0);
    }

    public void solution(boolean p1Turn, int lastDiceThrow) {
        int toAdd = lastDiceThrow + 1 == 100 ? 100 : (lastDiceThrow + 1) % maxDice;
        toAdd += lastDiceThrow + 2 == 100 ? 100 : (lastDiceThrow + 2) % maxDice;
        toAdd += lastDiceThrow + 3 == 100 ? 100 : (lastDiceThrow + 3) % maxDice;
        counter += 3;
        if (p1Turn) {
            p1field = (toAdd + p1field) % 10 == 0 ? 10 : (toAdd + p1field) % 10;
            p1Score += p1field;
        } else {
            p2field = (toAdd + p2field) % 10 == 0 ? 10 : (toAdd + p2field) % 10;
            p2Score += p2field;
        }

        if (p1Score >= 1000) {
            System.out.println(p2Score*counter);
        } else if (p2Score >= 1000) {
            System.out.println(p1Score*counter);
        } else {
            solution(!p1Turn, (lastDiceThrow + 3) % maxDice);
        }
    }
}
