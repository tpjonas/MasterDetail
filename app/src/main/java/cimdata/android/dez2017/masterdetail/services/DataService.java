package cimdata.android.dez2017.masterdetail.services;

public class DataService {

    private static final String[] data;

    // wird vor Konstruktor ausgeführt
    static {

        data = new String[]{
                "data1",
                "data2",
                "data3",
        };
    }

    public static String[] fetchData() {
        return data;
    }

}
