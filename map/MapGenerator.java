import java.util.Random;

public class MapGenerator {
    public char[][] generateMap(int width, int height) {
        char[][] map = new char[height][width];
        Random random = new Random();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int chance = random.nextInt(100);
                if (chance < 10) {
                    map[i][j] = 'I'; // Przedmiot
                } else if (chance < 20) {
                    map[i][j] = 'E'; // Wróg
                } else if (chance < 30) {
                    map[i][j] = '?'; // Zagadka
                } else {
                    map[i][j] = '.'; // Wolne pole
                }
            }
        }
        map[0][0] = 'P'; // Pozycja gracza
        map[height - 1][width - 1] = 'X'; // Wyjście
        return map;
    }
}