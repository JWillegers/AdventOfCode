package Day21;

public class PartB {
    private int p1Start = 3;
    private int p2Start = 5;
    private long p1Wins = 0;
    private long p2Wins = 0;
    private int[] nOfPaths = {0,0,0,1,3,6,7,6,3,1};

    public static void main(String[] args) {
        PartB part = new PartB();
        part.solution(true, 0, 0, part.p1Start, part.p2Start, 1);
        System.out.println("p1 wins: " + part.p1Wins);
        System.out.println("p2 wins: " + part.p2Wins);
    }

    public void solution(boolean p1Turn, int p1Score, int p2Score, int p1field, int p2field, long paths) {
        if (p1Score >= 21) {
            p1Wins += paths;
        } else if (p2Score >= 21) {
            p2Wins += paths;
        } else {
            for (int i = 3; i <= 9; i++) {
                if (p1Turn) {
                    int number = (p1field + i) % 10;
                    int newField = number == 0 ? 10 : number;
                    solution(false, p1Score + newField, p2Score, newField, p2field, paths * nOfPaths[i]);
                } else {
                    int number = (p2field + i) % 10;
                    int newField = number == 0 ? 10 : number;
                    solution(true, p1Score, p2Score + newField, p1field, newField, paths * nOfPaths[i]);
                }
            }
        }
    }
}