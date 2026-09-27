public class Nasabah {
    private String nik;
    private String nama;
    private String noHp;

    public Nasabah(String nik, String nama, String noHp) {
        this.nik = nik;
        this.nama = nama;
        this.noHp = noHp;
    }

    public String getNama() {
        return nama;
    }
}