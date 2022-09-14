import java.util.Random;

public class Util {
    public static int randomInRange(int min, int max){
        int diff = max - min;
        Random random = new Random();
        int i = random.nextInt(diff + 1);
        return i += min;
    }
}