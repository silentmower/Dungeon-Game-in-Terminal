package game;

public class DifficultySettings {
    private int minDamage;
    private int maxDamage;
    private int puzzleXp;
    private int enemyXp;

    private DifficultySettings(int minDamage, int maxDamage, int puzzleXp, int enemyXp) {
        this.minDamage = minDamage;
        this.maxDamage = maxDamage;
        this.puzzleXp = puzzleXp;
        this.enemyXp = enemyXp;
    }

    public static DifficultySettings getSettings(int difficulty) {
        switch (difficulty) {
            case 1: // Łatwy
                return new DifficultySettings(0, 40, 20, 30);
            case 2: // Średni
                return new DifficultySettings(0, 80, 10, 20);
            case 3: // Trudny
                return new DifficultySettings(20, 100, 5, 15);
            default:
                return new DifficultySettings(0, 80, 10, 20);
        }
    }

    public int getMinDamage() {
        return minDamage;
    }

    public int getMaxDamage() {
        return maxDamage;
    }

    public int getPuzzleXp() {
        return puzzleXp;
    }

    public int getEnemyXp() {
        return enemyXp;
    }
}
