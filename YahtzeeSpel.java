import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

class YahtzeeSpel {
    private ArrayList<Dobbelsteen> dobbelstenen = new ArrayList<>();
    private boolean spelen;
    private int[] blokkeerRij = {0, 0, 0, 0, 0};
    private Speler spelerA = new Speler(0);
    private Evaluatie evaluatie1 = new Evaluatie();
    private Random rand = new Random();

    static final String IS_AL_INGEVULD = " is al ingevuld";
    static final String KEUZE = " keuze: ";

    YahtzeeSpel() {
        this.spelen = true;
        for (int k = 1; k <= 5; k++) {
            dobbelstenen.add(new Dobbelsteen());
        }
    }

    void prt(String prt) {
        System.out.print("                                           ");
        System.out.println(prt);
    }

    void prt(String prt, int w) {
        System.out.print("                                 ");
        System.out.println(prt);
    }

    void toonMenu() {
        System.out.println("======================= Yahtzee =============================");
        System.out.println("Geef aan waar op de kaart de score geplaatst mag worden (a..m)");
        System.out.println("Posities vasthouden: 1, 2, 3, 4 en/of 5. 0 is niets vasthouden.");
    }

    boolean isAllesVasthouden() {
        return !(blokkeerRij[0] == 0 || blokkeerRij[1] == 0 || blokkeerRij[2] == 0 || blokkeerRij[3] == 0 || blokkeerRij[4] == 0);
    }

    void vasthouden(char blokkeer) {
        switch (blokkeer) {
            case '1':
                blokkeerRij[0] = 1;
                break;
            case '2':
                blokkeerRij[1] = 1;
                break;
            case '3':
                blokkeerRij[2] = 1;
                break;
            case '4':
                blokkeerRij[3] = 1;
                break;
            case '5':
                blokkeerRij[4] = 1;
                break;
        }
    }

    void vasthouden(String inputLine) {
        blokkeerRijVrij();
        System.out.println(inputLine + " " + inputLine.length());
        for (int k = 0; k < inputLine.length(); k++) {
            vasthouden(inputLine.charAt(k));
        }
        if (isAllesVasthouden()) {
            prt("Alles wordt vastgehouden! Einde beurt");
        } else if (spelerA.getWorpNr() < 3) {
            spelerA.setWorpNr(spelerA.getWorpNr() + 1);
            werpBeurt(spelerA.getWorpNr());
            evaluatie1.bepaalCombinaties(dobbelstenen);
            evaluatie1.toonScoreblok();
        }
    }

    void toonWerpBeurt(int worpnr, String melding) {
        System.out.println("");
        for (int k = 1; k <= dobbelstenen.size(); k++) {
            System.out.print(k + " ");
        }
        System.out.println(" dobbelstenen" + "      WORP " + worpnr + melding);

        // toon de worpen
        for (Dobbelsteen dobbelsteen : dobbelstenen) {
            System.out.print(dobbelsteen.getWorp() + " ");
        }
        System.out.print(" ogen gegooid");
        if (worpnr == 3) System.out.print("      3 maal geworpen einde worpbeurt");
    }

    void werpBeurt(int worpnr) {
        System.out.println("");
        for (int k = 1; k <= dobbelstenen.size(); k++) {
            System.out.print(k + " ");
        }
        System.out.println(" dobbelstenen" + "      WORP " + worpnr + " (ongesorteerd):");

        // vul; assign dobbelsteen.worp met behulp van dobbelsteen.werpen(1...6)
        for (int k = 0; k < dobbelstenen.size(); k++) {
            if (worpnr == 1) {
                dobbelstenen.get(k).setWorp(dobbelstenen.get(k).werpen(rand));
            } else if (blokkeerRij[k] == 0) {
                dobbelstenen.get(k).setWorp(dobbelstenen.get(k).werpen(rand));
            }
        }
        // toon de worpen
        for (Dobbelsteen dobbelsteen : dobbelstenen) {
            System.out.print(dobbelsteen.getWorp() + " ");
        }
        System.out.print(" ogen gegooid");
        if (worpnr == 3) System.out.print("      3 maal geworpen einde worpbeurt");
    }

    void spelen() throws InterruptedException {
        boolean isNieuweBeurt = true;
        while (this.spelen) {
            if (spelerA.getWorpNr() == 0) {
                if (spelerA.getWorpNr() < 3) {
                    spelerA.setWorpNr(spelerA.getWorpNr() + 1);
                    werpBeurt(spelerA.getWorpNr());
                    evaluatie1.bepaalCombinaties(dobbelstenen);
                    evaluatie1.toonScoreblok();
                }
                isNieuweBeurt = false;
            }
            toonMenu();
            prt(" ");
            invoer();
        }
    }

    void blokkeerRijVrij() {
        for (int k = 0; k < dobbelstenen.size(); k++) {
            blokkeerRij[k] = 0;
        }
    }

    void initialiseerNieuweBeurt() throws InterruptedException {
        spelerA.setWorpNr(0);
        blokkeerRijVrij();
        TimeUnit.SECONDS.sleep(3);
    }

    void controleVolgendeBeurt() throws InterruptedException {
        if (!evaluatie1.isAllesIngevuld()) {
            initialiseerNieuweBeurt();
        } else {
            System.out.println("Alle categorieen zijn ingevuld, het spel is beeindigd");
            this.spelen = false;
        }
    }

    void invoer() throws InterruptedException {
        Scanner reader = new Scanner(System.in);
        char input;
        String inputLine = "";
        inputLine = reader.nextLine();
        input = inputLine.charAt(0);
        switch (input) {
            case 'q':
                this.spelen = false;
                break;
            case '0':
                invoer_0();
                break;
            case '1':
            case '2':
            case '3':
            case '4':
            case '5':
                vasthouden(inputLine);
                break; /* posities vasthouden */
            case 'a':
                invoer_1Tm6(0, "Enen");
                break;
            case 'b':
                invoer_1Tm6(1, "Tweeen");
                break;
            case 'c':
                invoer_1Tm6(2, "Drieen");
                break;
            case 'd':
                invoer_1Tm6(3, "Vieren");
                break;
            case 'e':
                invoer_1Tm6(4, "Vijven");
                break;
            case 'f':
                invoer_1Tm6(5, "Zessen");
                break;
            case 'g':
                invoer_3TmVrijeKeuze(0,"3 Dezelfde");
                break;
            case 'h':
                invoer_3TmVrijeKeuze(1,"4 Dezelfde");
                break;
            case 'i':
                invoer_3TmVrijeKeuze(2,"Full House");
                break;
            case 'j':
                invoer_3TmVrijeKeuze(3,"Kl.Straat");
                break;
            case 'k':
                invoer_3TmVrijeKeuze(4,"Gr.Straat");
                break;
            case 'l':
                invoer_3TmVrijeKeuze(5,"Yahtzee");
                break;
            case 'm':
                invoer_3TmVrijeKeuze(6,"Vrije Keus");
                break;
            default:
                invoer_default();
                break;
        } // einde switch
    }

    void maakWorpObject() {
        Worp worp1 = new Worp(dobbelstenen.get(0).getWorp(),
                dobbelstenen.get(1).getWorp(),
                dobbelstenen.get(2).getWorp(),
                dobbelstenen.get(3).getWorp(),
                dobbelstenen.get(4).getWorp());
        spelerA.AddArrayListWorpHistory(worp1);
    }

    void invoer_0() {
        for (int k = 0; k < dobbelstenen.size(); k++) {
            blokkeerRij[k] = 0; // geen posities vasthouden
        }
        if (spelerA.getWorpNr() < 3) {
            spelerA.setWorpNr(spelerA.getWorpNr() + 1);
            werpBeurt(spelerA.getWorpNr());
            evaluatie1.bepaalCombinaties(dobbelstenen);
            evaluatie1.toonScoreblok();
        }
    }
    void invoer_1Tm6(int k, String s1) throws InterruptedException {
        if (Selecteer1tm6(k)) {
            Selecteer1tmVrijeKeuze(KEUZE + s1);
        } else {
            NietSelecteer1tmVrijeKeuze(KEUZE + s1 + IS_AL_INGEVULD);
        }
    }
    void invoer_3TmVrijeKeuze(int k,String s1) throws InterruptedException {
        if (Selecteer3tmVrijeKeuze(k)) {
            Selecteer1tmVrijeKeuze(KEUZE + s1 );
        } else {
            NietSelecteer1tmVrijeKeuze(KEUZE + s1  + IS_AL_INGEVULD);
        }
    }

    private void Selecteer1tmVrijeKeuze(String s) throws InterruptedException {
        maakWorpObject();
        evaluatie1.opschonenScoreNietIngevuld();
        NietSelecteer1tmVrijeKeuze(s);
        toonMenu();
        controleVolgendeBeurt();
    }
    private void NietSelecteer1tmVrijeKeuze(String s) {
        toonWerpBeurt(spelerA.getWorpNr(), s);
        System.out.println("");
        evaluatie1.toonScoreblok();
    }

    void invoer_default() throws InterruptedException {
        NietSelecteer1tmVrijeKeuze(" keuze: verkeerd!");
    }

    boolean Selecteer1tm6(int k) {
        boolean isGeselecteerd = false;
        if (!evaluatie1.getScoreCardUpperPlayer1Ingevuld(k)) {
            return evaluatieSetScoreCardUpperPlayerIngevuld(k);
        }
        return isGeselecteerd;
    }

    private boolean evaluatieSetScoreCardUpperPlayerIngevuld(int i) {
        boolean isGeselecteerd;
        isGeselecteerd = true;
        evaluatie1.setScoreCardUpperPlayer1Ingevuld(i, isGeselecteerd);
        return isGeselecteerd;
    }

    private boolean evaluatieSetScoreCardLowerPlayerIngevuld(int i) {
        boolean isGeselecteerd;
        isGeselecteerd = true;
        evaluatie1.setScoreCardLowerPlayer1Ingevuld(i, isGeselecteerd);
        return isGeselecteerd;
    }

    boolean Selecteer3tmVrijeKeuze(int k) {
        boolean isGeselecteerd = false;
        if (!evaluatie1.getScoreCardLowerPlayer1Ingevuld(k)) {
            return evaluatieSetScoreCardLowerPlayerIngevuld(k);
        }
        return isGeselecteerd;
    }
}
