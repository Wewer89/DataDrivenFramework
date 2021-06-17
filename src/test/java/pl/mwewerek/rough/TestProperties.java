package pl.mwewerek.rough;

public class TestProperties {


    static int indexOfFirstSemicolon;
    static String subNapis;

    public static String wytnij(String napis) {
        // Tutaj wpisz rozwiązanie

        if (!napis.contains(";")) {
            return napis.toUpperCase();
        } else {
            indexOfFirstSemicolon = napis.indexOf(";");
            subNapis = napis.substring(indexOfFirstSemicolon + 1);
            if (!subNapis.contains(";")) {
                return subNapis.toUpperCase();
            } else {
                String[] words = napis.split(";");
                return words[1].toUpperCase();
            }
        }

    }

    public static void main(String[] args) {
        String wynik1 = wytnij("aaaa;bbbb;cccc");
        System.out.println("Wynik1: " + wynik1);

        String wynik2 = wytnij("aaaa;bbbb");
        System.out.println("Wynik2: " + wynik2);

        String wynik3 = wytnij("aaaa_bbbb_cccc");
        System.out.println("Wynik3: " + wynik3);
    }
}

