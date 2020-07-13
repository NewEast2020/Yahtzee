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
                invoer_a();
                break;
            case 'b':
                invoer_b();
                break;
            case 'c':
                invoer_c();
                break;
            case 'd':
                invoer_d();
                break;
            case 'e':
                invoer_e();
                break;
            case 'f':
                invoer_f();
                break;
            case 'g':
                invoer_g();
                break;
            case 'h':
                invoer_h();
                break;
            case 'i':
                invoer_i();
                break;
            case 'j':
                invoer_j();
                break;
            case 'k':
                invoer_k();
                break;
            case 'l':
                invoer_l();
                break;
            case 'm':
                invoer_m();
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

    void invoer_a() throws InterruptedException {
        if (SelecteerEnen1()) {
            maakWorpObject();
            evaluatie1.opschonenScoreNietIngevuld();
            toonWerpBeurt(spelerA.getWorpNr(), " keuze: Enen");
            System.out.println("");
            evaluatie1.toonScoreblok();
            toonMenu();
            controleVolgendeBeurt();
        } else {
            toonWerpBeurt(spelerA.getWorpNr(), " keuze: Enen is al ingevuld");
            System.out.println("");
            evaluatie1.toonScoreblok();
        }
    }

    void invoer_b() throws InterruptedException {
        if (SelecteerTweeen2()) {
            maakWorpObject();
            evaluatie1.opschonenScoreNietIngevuld();
            toonWerpBeurt(spelerA.getWorpNr(), " keuze: Tweeen");
            System.out.println("");
            evaluatie1.toonScoreblok();
            toonMenu();
            controleVolgendeBeurt();
        } else {
            toonWerpBeurt(spelerA.getWorpNr(), " keuze: Tweeen is al ingevuld");
            System.out.println("");
            evaluatie1.toonScoreblok();
        }
    }

    void invoer_c() throws InterruptedException {
        if (SelecteerDrieen3()) {
            maakWorpObject();
            evaluatie1.opschonenScoreNietIngevuld();
            toonWerpBeurt(spelerA.getWorpNr(), " keuze: Drieen");
            System.out.println("");
            evaluatie1.toonScoreblok();
            toonMenu();
            controleVolgendeBeurt();
        } else {
            toonWerpBeurt(spelerA.getWorpNr(), " keuze: Drieen is al ingevuld");
            System.out.println("");
            evaluatie1.toonScoreblok();
        }
    }

    void invoer_d() throws InterruptedException {
        if (SelecteerVieren4()) {
            maakWorpObject();
            evaluatie1.opschonenScoreNietIngevuld();
            toonWerpBeurt(spelerA.getWorpNr(), " keuze: Vieren");
            System.out.println("");
            evaluatie1.toonScoreblok();
            toonMenu();
            controleVolgendeBeurt();
        } else {
            toonWerpBeurt(spelerA.getWorpNr(), " keuze: Vieren is al ingevuld");
            System.out.println("");
            evaluatie1.toonScoreblok();
        }
    }

    void invoer_e() throws InterruptedException {
        if (SelecteerVijven5()) {
            maakWorpObject();
            evaluatie1.opschonenScoreNietIngevuld();
            toonWerpBeurt(spelerA.getWorpNr(), " keuze: Vijven");
            System.out.println("");
            evaluatie1.toonScoreblok();
            toonMenu();
            controleVolgendeBeurt();
        } else {
            toonWerpBeurt(spelerA.getWorpNr(), " keuze: Vijven is al ingevuld");
            System.out.println("");
            evaluatie1.toonScoreblok();
        }
    }

    void invoer_f() throws InterruptedException {
        if (SelecteerZessen6()) {
            maakWorpObject();
            evaluatie1.opschonenScoreNietIngevuld();
            toonWerpBeurt(spelerA.getWorpNr(), " keuze: Zessen");
            System.out.println("");
            evaluatie1.toonScoreblok();
            toonMenu();
            controleVolgendeBeurt();
        } else {
            toonWerpBeurt(spelerA.getWorpNr(), " keuze: Zessen is al ingevuld");
            System.out.println("");
            evaluatie1.toonScoreblok();
        }
    }

    void invoer_g() throws InterruptedException {
        if (Selecteer3Dezelfde1()) {
            maakWorpObject();
            evaluatie1.opschonenScoreNietIngevuld();
            toonWerpBeurt(spelerA.getWorpNr(), " keuze: 3 Dezelfde");
            System.out.println("");
            evaluatie1.toonScoreblok();
            toonMenu();
            controleVolgendeBeurt();
        } else {
            toonWerpBeurt(spelerA.getWorpNr(), " keuze: 3 Dezelfde is al ingevuld");
            System.out.println("");
            evaluatie1.toonScoreblok();
        }
    }

    void invoer_h() throws InterruptedException {
        if (Selecteer4Dezelfde2()) {
            maakWorpObject();
            evaluatie1.opschonenScoreNietIngevuld();
            toonWerpBeurt(spelerA.getWorpNr(), " keuze: 4 Dezelfde");
            System.out.println("");
            evaluatie1.toonScoreblok();
            toonMenu();
            controleVolgendeBeurt();
        } else {
            toonWerpBeurt(spelerA.getWorpNr(), " keuze: 4 Dezelfde is al ingevuld");
            System.out.println("");
            evaluatie1.toonScoreblok();
        }
    }

    void invoer_i() throws InterruptedException {
        if (SelecteerFullHouse3()) {
            maakWorpObject();
            evaluatie1.opschonenScoreNietIngevuld();
            toonWerpBeurt(spelerA.getWorpNr(), " keuze: Full House");
            System.out.println("");
            evaluatie1.toonScoreblok();
            toonMenu();
            controleVolgendeBeurt();
        } else {
            toonWerpBeurt(spelerA.getWorpNr(), " keuze: Full House is al ingevuld");
            System.out.println("");
            evaluatie1.toonScoreblok();
        }
    }

    void invoer_j() throws InterruptedException {
        if (SelecteerKlStraat4()) {
            maakWorpObject();
            evaluatie1.opschonenScoreNietIngevuld();
            toonWerpBeurt(spelerA.getWorpNr(), " keuze: Kl.Straat");
            System.out.println("");
            evaluatie1.toonScoreblok();
            toonMenu();
            controleVolgendeBeurt();
        } else {
            toonWerpBeurt(spelerA.getWorpNr(), " keuze: Kl.Straat is al ingevuld");
            System.out.println("");
            evaluatie1.toonScoreblok();
        }
    }

    void invoer_k() throws InterruptedException {
        if (SelecteerGrStraat5()) {
            maakWorpObject();
            evaluatie1.opschonenScoreNietIngevuld();
            toonWerpBeurt(spelerA.getWorpNr(), " keuze: Gr.Straat");
            System.out.println("");
            evaluatie1.toonScoreblok();
            toonMenu();
            controleVolgendeBeurt();
        } else {
            toonWerpBeurt(spelerA.getWorpNr(), " keuze: Gr.Straat is al ingevuld");
            System.out.println("");
            evaluatie1.toonScoreblok();
        }
    }

    void invoer_l() throws InterruptedException {
        if (SelecteerYahtzee6()) {
            maakWorpObject();
            evaluatie1.opschonenScoreNietIngevuld();
            toonWerpBeurt(spelerA.getWorpNr(), " keuze: Yahtzee");
            System.out.println("");
            evaluatie1.toonScoreblok();
            toonMenu();
            controleVolgendeBeurt();
        } else {
            toonWerpBeurt(spelerA.getWorpNr(), " keuze: Yahtzee is al ingevuld");
            System.out.println("");
            evaluatie1.toonScoreblok();
        }
    }

    void invoer_m() throws InterruptedException {
        if (SelecteerVrijeKeus7()) {
            maakWorpObject();
            evaluatie1.opschonenScoreNietIngevuld();
            toonWerpBeurt(spelerA.getWorpNr(), " keuze: Vrije Keus");
            System.out.println("");
            evaluatie1.toonScoreblok();
            toonMenu();
            controleVolgendeBeurt();
        } else {
            toonWerpBeurt(spelerA.getWorpNr(), " keuze: Vrije Keus is al ingevuld");
            System.out.println("");
            evaluatie1.toonScoreblok();
        }
    }

    void invoer_default() throws InterruptedException {
        toonWerpBeurt(spelerA.getWorpNr(), " keuze: verkeerd!");
        System.out.println("");
        evaluatie1.toonScoreblok();
    }

    boolean SelecteerEnen1() {
        boolean isGeselecteerd = false;
        if (!evaluatie1.getScoreCardUpperPlayer1Ingevuld(0)) {
            isGeselecteerd = true;
            evaluatie1.setScoreCardUpperPlayer1Ingevuld(0, isGeselecteerd);
            return isGeselecteerd;
        }
        return isGeselecteerd;
    }

    boolean SelecteerTweeen2() {
        boolean isGeselecteerd = false;
        if (!evaluatie1.getScoreCardUpperPlayer1Ingevuld(1)) {
            isGeselecteerd = true;
            evaluatie1.setScoreCardUpperPlayer1Ingevuld(1, isGeselecteerd);
            return isGeselecteerd;
        }
        return isGeselecteerd;
    }

    boolean SelecteerDrieen3() {
        boolean isGeselecteerd = false;
        if (!evaluatie1.getScoreCardUpperPlayer1Ingevuld(2)) {
            isGeselecteerd = true;
            evaluatie1.setScoreCardUpperPlayer1Ingevuld(2, isGeselecteerd);
            return isGeselecteerd;
        }
        return isGeselecteerd;
    }

    boolean SelecteerVieren4() {
        boolean isGeselecteerd = false;
        if (!evaluatie1.getScoreCardUpperPlayer1Ingevuld(3)) {
            isGeselecteerd = true;
            evaluatie1.setScoreCardUpperPlayer1Ingevuld(3, isGeselecteerd);
            return isGeselecteerd;
        }
        return isGeselecteerd;
    }

    boolean SelecteerVijven5() {
        boolean isGeselecteerd = false;
        if (!evaluatie1.getScoreCardUpperPlayer1Ingevuld(4)) {
            isGeselecteerd = true;
            evaluatie1.setScoreCardUpperPlayer1Ingevuld(4, isGeselecteerd);
            return isGeselecteerd;
        }
        return isGeselecteerd;
    }

    boolean SelecteerZessen6() {
        boolean isGeselecteerd = false;
        if (!evaluatie1.getScoreCardUpperPlayer1Ingevuld(5)) {
            isGeselecteerd = true;
            evaluatie1.setScoreCardUpperPlayer1Ingevuld(5, isGeselecteerd);
            return isGeselecteerd;
        }
        return isGeselecteerd;
    }

    boolean Selecteer3Dezelfde1() {
        boolean isGeselecteerd = false;
        if (!evaluatie1.getScoreCardLowerPlayer1Ingevuld(0)) {
            isGeselecteerd = true;
            evaluatie1.setScoreCardLowerPlayer1Ingevuld(0, isGeselecteerd);
            return isGeselecteerd;
        }
        return isGeselecteerd;
    }

    boolean Selecteer4Dezelfde2() {
        boolean isGeselecteerd = false;
        if (!evaluatie1.getScoreCardLowerPlayer1Ingevuld(1)) {
            isGeselecteerd = true;
            evaluatie1.setScoreCardLowerPlayer1Ingevuld(1, isGeselecteerd);
            return isGeselecteerd;
        }
        return isGeselecteerd;
    }

    boolean SelecteerFullHouse3() {
        boolean isGeselecteerd = false;
        if (!evaluatie1.getScoreCardLowerPlayer1Ingevuld(2)) {
            isGeselecteerd = true;
            evaluatie1.setScoreCardLowerPlayer1Ingevuld(2, isGeselecteerd);
            return isGeselecteerd;
        }
        return isGeselecteerd;
    }

    boolean SelecteerKlStraat4() {
        boolean isGeselecteerd = false;
        if (!evaluatie1.getScoreCardLowerPlayer1Ingevuld(3)) {
            isGeselecteerd = true;
            evaluatie1.setScoreCardLowerPlayer1Ingevuld(3, isGeselecteerd);
            return isGeselecteerd;
        }
        return isGeselecteerd;
    }

    boolean SelecteerGrStraat5() {
        boolean isGeselecteerd = false;
        if (!evaluatie1.getScoreCardLowerPlayer1Ingevuld(4)) {
            isGeselecteerd = true;
            evaluatie1.setScoreCardLowerPlayer1Ingevuld(4, isGeselecteerd);
            return isGeselecteerd;
        }
        return isGeselecteerd;
    }

    boolean SelecteerYahtzee6() {
        boolean isGeselecteerd = false;
        if (!evaluatie1.getScoreCardLowerPlayer1Ingevuld(5)) {
            isGeselecteerd = true;
            evaluatie1.setScoreCardLowerPlayer1Ingevuld(5, isGeselecteerd);
            return isGeselecteerd;
        }
        return isGeselecteerd;
    }

    boolean SelecteerVrijeKeus7() {
        boolean isGeselecteerd = false;
        if (!evaluatie1.getScoreCardLowerPlayer1Ingevuld(6)) {
            isGeselecteerd = true;
            evaluatie1.setScoreCardLowerPlayer1Ingevuld(6, isGeselecteerd);
            return isGeselecteerd;
        }
        return isGeselecteerd;
    }
}
