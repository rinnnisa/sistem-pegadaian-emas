public class Emas {
    private String kodeBarang;
    private double berat; 
    private int karat;

    public Emas(String kodeBarang, double berat, int karat) {
        this.kodeBarang = kodeBarang;
        this.berat = berat;
        this.karat = karat;
    }

    public String getKodeBarang() {
        return kodeBarang;
    }

    public double getTaksiran() {
        return berat * 1000000 * ((double) karat / 24);
    }
}