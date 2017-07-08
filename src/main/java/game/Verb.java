package game;

/**
 * Created by Viktor on 07.07.17.
 */
public class Verb {
    private String inf = "";
    private String third = "";
    private String prat = "";
    private String P2 = "";

    public String getInf() {
        return inf;
    }

    public void setInf(String inf) {
        this.inf = inf;
    }

    public String getThird() {
        return third;
    }

    public void setThird(String third) {
        this.third = third;
    }

    public String getPrat() {
        return prat;
    }

    public void setPrat(String prat) {
        this.prat = prat;
    }

    public String getP2() {
        return P2;
    }

    public void setP2(String p2) {
        P2 = p2;
    }

    public Verb(String inf, String prat, String p2) {
        this.inf = inf.toLowerCase();
        this.prat = prat.toLowerCase();
        P2 = p2.toLowerCase();
        makeThirdForm();
    }

    public Verb(String inf, String third, String prat, String p2) {
        this.inf = inf.toLowerCase();
        this.third = third.toLowerCase();
        this.prat = prat.toLowerCase();
        this.P2 = p2.toLowerCase();
    }

    private void makeThirdForm(){
        third = inf.substring(0,inf.length()-1);

        if(third.charAt(third.length()-1) == 'e')
            if(third.charAt(third.length()-2) != 't'
                && third.charAt(third.length()-2) != 'd'){
                third = inf.substring(0, third.length() - 1);
        }
        third += "t";
    }

    public String[] getWordArr(){
        return new String[]{
                inf,
                third,
                prat,
                P2
        };
    }

    @Override
    public String toString() {
        return  inf + " -> " + third + " -> " + prat + " -> " + P2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Verb verb = (Verb) o;

        if (inf != null ? !inf.equals(verb.inf) : verb.inf != null) return false;
        if (third != null ? !third.equals(verb.third) : verb.third != null) return false;
        if (prat != null ? !prat.equals(verb.prat) : verb.prat != null) return false;
        return P2 != null ? P2.equals(verb.P2) : verb.P2 == null;
    }

    @Override
    public int hashCode() {
        int result = inf != null ? inf.hashCode() : 0;
        result = 31 * result + (third != null ? third.hashCode() : 0);
        result = 31 * result + (prat != null ? prat.hashCode() : 0);
        result = 31 * result + (P2 != null ? P2.hashCode() : 0);
        return result;
    }

    /**
     *
     * @param line - "%inf% %prat% %part2%"
     *             || "%inf% %third form% %prat% %part2%"
     *
     * @return
     */
    public static Verb parse(String line) {
        String[] words = line.split(" ");
        if (words.length == 4) {
            return new Verb(words[0], words[1], words[2], words[3]);
        } else if (words.length == 3) {
            return new Verb(words[0], words[1], words[2]);
        } else throw new IllegalArgumentException("Error parse String :" + line + " to Verb, lenght = " + words.length);
    }
}

