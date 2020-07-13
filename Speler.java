import java.util.ArrayList;

class Speler {
    private int worpNr;
    private ArrayList<Worp> worpHistory = new ArrayList<>();

    Speler(int worpNr) {
        this.worpNr = worpNr;
    }

    public int getWorpNr(){
        return this.worpNr;
    }

    public void setWorpNr(int worpNr) {
        this.worpNr = worpNr;
    }
    public void AddArrayListWorpHistory(Worp worp){
        worpHistory.add(worp);
    }
}
