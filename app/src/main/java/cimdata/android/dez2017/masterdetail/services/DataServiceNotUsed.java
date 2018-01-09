package cimdata.android.dez2017.masterdetail.services;

public class DataServiceNotUsed {

    private static final String[] data;

    // wird vor Konstruktor ausgeführt
    static {

        data = new String[]{
                "Putzen",
                "Spülen",
                "Aufräumen",
        };
    }

    public static String[] fetchData() {
        return data;
    }

}
