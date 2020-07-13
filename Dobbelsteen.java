import java.util.Random;

class Dobbelsteen {
    private int worp;
    int werpen(Random rand) {
        return rand.nextInt(6) + 1;
    }

    public int getWorp() {
        return worp;
    }
    public void setWorp(int worp) {
        this.worp = worp;
    }
}
