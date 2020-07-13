class Worp {
    private int[] worp = new int[5];

    Worp() {
        worp[0] = 0;
        worp[1] = 0;
        worp[2] = 0;
        worp[3] = 0;
        worp[4] = 0;
    }

    Worp(int worp1, int worp2, int worp3, int worp4, int worp5) {
        this.worp[0] = worp1;
        this.worp[1] = worp2;
        this.worp[2] = worp3;
        this.worp[3] = worp4;
        this.worp[4] = worp5;
    }

    public void getWorp() { // geeft de worp uitslag
        String s = "";
        for (int k = 0; k < worp.length; k++) {
            s = s + worp[k] + ", ";
        }
        System.out.println("worp: " + s);
    }
}
