import java.util.ArrayList;
import java.util.Arrays;

public class Evaluatie {
    private int[] worpenSet = new int[5];
    private int[] scoreCardUpper = new int[9];
    private int[] scoreCardUpperPlayer1 = new int[9];
    private String[] scoreCardUpperOms = new String[9];
    private boolean[] scoreCardUpperPlayer1Ingevuld = new boolean[9];
    private boolean[] scoreCardLowerPlayer1Ingevuld = new boolean[9];

    private int[] scoreCardLower = new int[9];
    private int[] scoreCardLowerPlayer1 = new int[9];
    private String[] scoreCardLowerOms = new String[10];

    public boolean getScoreCardUpperPlayer1Ingevuld(int k){
        return scoreCardUpperPlayer1Ingevuld[k];
    }
    public void setScoreCardUpperPlayer1Ingevuld(int k,boolean vullen){
         scoreCardUpperPlayer1Ingevuld[k] = vullen;
    }
    public boolean getScoreCardLowerPlayer1Ingevuld(int k){
        return scoreCardLowerPlayer1Ingevuld[k];
    }
    public void setScoreCardLowerPlayer1Ingevuld(int k,boolean vullen){
        scoreCardLowerPlayer1Ingevuld[k] = vullen;
    }


    Evaluatie() {
        scoreCardUpperOms[0] = "Enen    [a]  |";
        scoreCardUpper[0] = 0;
        scoreCardUpperOms[1] = "Tweeen  [b]  |";
        scoreCardUpper[1] = 0;
        scoreCardUpperOms[2] = "Drieen  [c]  |";
        scoreCardUpper[2] = 0;
        scoreCardUpperOms[3] = "Vieren  [d]  |";
        scoreCardUpper[3] = 0;
        scoreCardUpperOms[4] = "Vijven  [e]  |";
        scoreCardUpper[4] = 0;
        scoreCardUpperOms[5] = "Zessen  [f]  |";
        scoreCardUpper[5] = 0;
        scoreCardUpperOms[6] = "Totaal       |";
        scoreCardUpper[6] = 35;
        scoreCardUpperPlayer1Ingevuld[6] = true;
        scoreCardUpperOms[7] = "Bonus        |";
        scoreCardUpper[7] = 0;
        scoreCardUpperPlayer1Ingevuld[7] = true;
        scoreCardUpperOms[8] = "Totaal(1)    |";
        scoreCardUpper[8] = 0;
        scoreCardUpperPlayer1Ingevuld[8] = true;
        scoreCardLowerOms[0] = " | 3 Dezelfde  [g]  |";
        scoreCardLower[0] = 0;
        scoreCardLowerOms[1] = " | 4 Dezelfde  [h]  |";
        scoreCardLower[1] = 0;
        scoreCardLowerOms[2] = " | Full House  [i]  |";
        scoreCardLower[2] = 25;
        scoreCardLowerOms[3] = " | Kl. Straat  [j]  |";
        scoreCardLower[3] = 30;
        scoreCardLowerOms[4] = " | Gr. Straat  [k]  |";
        scoreCardLower[4] = 40;
        scoreCardLowerOms[5] = " | Yahtzee     [l]  |";
        scoreCardLower[5] = 50;
        scoreCardLowerOms[6] = " | Vrije Keus  [m]  |";
        scoreCardLower[6] = 0;
        scoreCardLowerOms[7] = " | Totaal(2)        |";
        scoreCardLower[7] = 100;
        scoreCardLowerPlayer1Ingevuld[7] = true;
        scoreCardLowerOms[8] = " | *** Totaal ***   |";
        scoreCardLower[8] = 0;
        scoreCardLowerPlayer1Ingevuld[8] = true;
    }
    void evalueer(int d1, int d2, int d3, int d4, int d5) {
    }
    void bepaalCombinaties(ArrayList<Dobbelsteen> dobbelstenen) {
        for (int k = 0; k < dobbelstenen.size(); k++) {
            worpenSet[k] = dobbelstenen.get(k).getWorp();
        }
        Arrays.sort(worpenSet);
        System.out.println(" ");
        bepaalScoreNietIngevuld(worpenSet);
    }

    int bepaalTotaal() {
        int totaal = 0;
        for (int k = 0; k < 6; k++) {
            if (scoreCardUpperPlayer1Ingevuld[k]) {
                totaal += scoreCardUpperPlayer1[k];
            }
        }
        scoreCardUpperPlayer1[6] = totaal;
        return totaal;
    }

    int bepaalBonus() {
        int bonus = 0;
        if (bepaalTotaal() >= 63) {
            bonus = 35;
            scoreCardUpperPlayer1[7] = bonus;
        }
        return bonus;
    }

    int bepaalTotaal1() {
        int totaal1;
        totaal1 = bepaalTotaal() + bepaalBonus();
        scoreCardUpperPlayer1[8] = totaal1;
        return totaal1;
    }

    int bepaalTotaal2() {
        int totaal2 = 0;
        for (int k = 0; k < 7; k++) {
            if (scoreCardLowerPlayer1Ingevuld[k]) {
                totaal2 += scoreCardLowerPlayer1[k];
            }
        }
        scoreCardLowerPlayer1[7] = totaal2;
        return totaal2;
    }

    int bepaalTotaalSter() {
        int totaalSter;
        totaalSter = bepaalTotaal1() + bepaalTotaal2();
        scoreCardLowerPlayer1[8] = totaalSter;
        return totaalSter;
    }

    void toonScoreblok() {
        bepaalTotaal();
        bepaalBonus();
        bepaalTotaal1();
        bepaalTotaal2();
        bepaalTotaalSter();
        System.out.println("                           | ScoreBlok        |   Player1   |");
        for (int k = 0, t = 1; k < scoreCardUpperPlayer1Ingevuld.length; k++, t++) {
            System.out.print(scoreCardUpperOms[k] + "   ");
            printScoreWaarde(scoreCardUpperPlayer1, k, scoreCardUpperPlayer1Ingevuld);
            System.out.print(scoreCardLowerOms[k] + "   ");
            printScoreWaarde(scoreCardLowerPlayer1, k, scoreCardLowerPlayer1Ingevuld);
            System.out.println(" | ");
        }
    }

    void printScoreWaarde(int scoreCardPlayer[], int k, boolean[] scoreCardPlayerIngevuld) {
        if (scoreCardPlayerIngevuld[k]) {
            System.out.printf("    %5d", scoreCardPlayer[k]);
        } else if (scoreCardPlayer[k] == 0) {
            System.out.printf(" <> %5d", scoreCardPlayer[k]);
        } else {
            System.out.printf("<-->%5d", scoreCardPlayer[k]);
        }
    }

    void toonScoreKeuzes() {
        for (int k = 0, t = 1; k < scoreCardUpperPlayer1Ingevuld.length; k++, t++) {
            System.out.print("(" + t + ")  " + scoreCardUpperPlayer1[k] + "  ");
            System.out.println(scoreCardUpperOms[k]);
        }
        for (int k = 0, t = 7; k < scoreCardLowerPlayer1Ingevuld.length; k++, t++) {
            System.out.print("(" + t + ")  " + scoreCardLowerPlayer1[k] + " ");
            System.out.println(scoreCardLowerOms[k]);
        }
    }

    boolean isAllesIngevuld() {
        boolean isAllesIngevuld = false;
        for (int k = 0; k < 6; k++) {
            if (!scoreCardUpperPlayer1Ingevuld[k]) {
                return isAllesIngevuld;
            }
        }
        for (int k = 0; k < 7; k++) {
            if (!scoreCardLowerPlayer1Ingevuld[k]) {
                return isAllesIngevuld;
            }
        }
        isAllesIngevuld = true;
        return isAllesIngevuld;
    }

    void opschonenScoreNietIngevuld() {
        for (int k = 0; k < scoreCardUpperPlayer1Ingevuld.length; k++) {
            if (!scoreCardUpperPlayer1Ingevuld[k]) {
                scoreCardUpperPlayer1[k] = 0;
            }
        }
        for (int k = 0; k < scoreCardLowerPlayer1Ingevuld.length; k++) {
            if (!scoreCardLowerPlayer1Ingevuld[k]) {
                scoreCardLowerPlayer1[k] = 0;
            }
        }
    }

    void bepaalScoreNietIngevuld(int[] worpenSet) {
        for (int k = 0; k < scoreCardUpperPlayer1Ingevuld.length; k++) {
            if (!scoreCardUpperPlayer1Ingevuld[k]) {
                switch (k) {
                    case 0:scoreCardUpperPlayer1[0] = bepaalScore1tm6(worpenSet,1);break;
                    case 1:scoreCardUpperPlayer1[1] = bepaalScore1tm6(worpenSet,2);break;
                    case 2:scoreCardUpperPlayer1[2] = bepaalScore1tm6(worpenSet,3);break;
                    case 3:scoreCardUpperPlayer1[3] = bepaalScore1tm6(worpenSet,4);break;
                    case 4:scoreCardUpperPlayer1[4] = bepaalScore1tm6(worpenSet,5);break;
                    case 5:scoreCardUpperPlayer1[5] = bepaalScore1tm6(worpenSet,6);break;
                }
            }
        }
        for (int k = 0; k < scoreCardLowerPlayer1Ingevuld.length; k++) {
            if (!scoreCardLowerPlayer1Ingevuld[k]) {
                switch (k) {
                    case 0:scoreCardLowerPlayer1[0] = bepaalScore1DrieGelijke(worpenSet);break;
                    case 1:scoreCardLowerPlayer1[1] = bepaalScore2VierGelijke(worpenSet);break;
                    case 2:scoreCardLowerPlayer1[2] = bepaalScore3FullHouse(worpenSet);break;
                    case 3:scoreCardLowerPlayer1[3] = bepaalScore4KleineStraat(worpenSet);break;
                    case 4:scoreCardLowerPlayer1[4] = bepaalScore5GroteStraat(worpenSet);break;
                    case 5:scoreCardLowerPlayer1[5] = bepaalScore6Yahtzee(worpenSet);break;
                    case 6:scoreCardLowerPlayer1[6] = bepaalScore7Kans(worpenSet);break;
                }
            }
        }
    }
    private int bepaalScore1tm6(int[] worpenSet, int i) {
        int aantal = 0;
        aantal = bepaalAantal(worpenSet, i);
        return aantal * i;
    }

    int bepaalAantal(int[] worpenSet, int kaartnummer) {
        int aantal = 0;
        for (int k = 0; k < worpenSet.length; k++) {
            if (worpenSet[k] == kaartnummer) {
                aantal++;
            }
        }
        return aantal;
    }

    int bepaalScore1DrieGelijke(int[] worpenSet) {
        if (worpenSet[0] == 0) return 0;
        if (isaDrieGelijke(worpenSet, 0, 1, 2) ||
            isaDrieGelijke(worpenSet, 1, 2, 3) ||
            isaDrieGelijke(worpenSet, 2, 3, 4)
           )
            return worpenSet[0] + worpenSet[1] + worpenSet[2] + worpenSet[3] + worpenSet[4];
        return 0;
    }

    private boolean isaDrieGelijke(int[] worpenSet, int i, int i2, int i3) {
        return worpenSet[i] == worpenSet[i2] && worpenSet[i2] == worpenSet[i3];
    }

    int bepaalScore2VierGelijke(int[] worpenSet) {
        if (worpenSet[0] == 0) return 0;
        if (isaVierGelijke(worpenSet, 0, 1, 2, 3) || isaVierGelijke(worpenSet, 1, 2, 3, 4) )
            return worpenSet[0] + worpenSet[1] + worpenSet[2] + worpenSet[3] + worpenSet[4];
        return 0;
    }

    private boolean isaVierGelijke(int[] worpenSet, int i, int i2, int i3, int i4) {
        return worpenSet[i] == worpenSet[i2] && worpenSet[i2] == worpenSet[i3] && worpenSet[i3] == worpenSet[i4];
    }

    int bepaalScore3FullHouse(int[] worpenSet) {
        if (worpenSet[0] == 0) return 0;
        if ( isaFullHouse(worpenSet, 1, 2) || isaFullHouse(worpenSet, 2, 3) )
            return 25;
        return 0;
    }

    private boolean isaFullHouse(int[] worpenSet, int i, int i2) {
        return worpenSet[0] == worpenSet[1] && worpenSet[i] == worpenSet[i2] && worpenSet[3] == worpenSet[4];
    }

    int bepaalScore4KleineStraat(int[] worpenSet) {
        int[] worpenSet2 = new int[5];
        if (worpenSet[0] == 0) return 0;
        int k2 = 0;
        k2 = getK2(worpenSet, worpenSet2, k2, 0, 1);
        k2 = getK2(worpenSet, worpenSet2, k2, 1, 2);
        k2 = getK2(worpenSet, worpenSet2, k2, 2, 3);
        k2 = getK2(worpenSet, worpenSet2, k2, 3, 4);
        worpenSet2[k2] = worpenSet[4];
        if (
             isaKleineStraat(worpenSet2, 0, 1, 1, 2, 2, 3, 3, 4) ||
             isaKleineStraat(worpenSet2, 0, 2, 1, 3, 2, 4, 3, 5) ||
             isaKleineStraat(worpenSet2, 0, 3, 1, 4, 2, 5, 3, 6) ||
             isaKleineStraat(worpenSet2, 1, 3, 2, 4, 3, 5, 4, 6)
        ) return 30;
        return 0;
    }

    private int getK2(int[] worpenSet, int[] worpenSet2, int k2, int i, int i2) {
        if (worpenSet[i] != worpenSet[i2]) {
            worpenSet2[k2] = worpenSet[i];
            k2++;
        }
        return k2;
    }

    private boolean isaKleineStraat(int[] worpenSet2, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
        return worpenSet2[i] == i2  && worpenSet2[i3] == i4 &&
               worpenSet2[i5] == i6 && worpenSet2[i7] == i8;
    }

    int bepaalScore5GroteStraat(int[] worpenSet) {
        if (worpenSet[0] == 0) return 0;
        if (
             isaGroteStraat(worpenSet, 1, 2, 3, 4, 5) ||
             isaGroteStraat(worpenSet, 2, 3, 4, 5, 6)
        ) return 40;
        return 0;
    }

    private boolean isaGroteStraat(int[] worpenSet, int i, int i2, int i3, int i4, int i5) {
        return worpenSet[0] == i && worpenSet[1] == i2 && worpenSet[2] == i3 &&
                                    worpenSet[3] == i4 && worpenSet[4] == i5;
    }

    int bepaalScore6Yahtzee(int[] worpenSet) {
        if (isaYahtzee(worpenSet)) return 50;
        return 0;
    }

    private boolean isaYahtzee(int[] worpenSet) {
        return worpenSet[0] != 0 && worpenSet[0] == worpenSet[1] && worpenSet[1] == worpenSet[2] &&
                                    worpenSet[2] == worpenSet[3] && worpenSet[3] == worpenSet[4];
    }

    int bepaalScore7Kans(int[] worpenSet) {
        return worpenSet[0] + worpenSet[1] + worpenSet[2] + worpenSet[3] + worpenSet[4];
    }
}

