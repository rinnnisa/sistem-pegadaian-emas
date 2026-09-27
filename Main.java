public class Main {
    public static void main(String[] args) {
        Pegadaian cabangUtama = new Pegadaian();
        
        Nasabah budi = new Nasabah("320123456789", "Budi Santoso", "08123456789");
        Emas cincinBudi = new Emas("EM-001", 5.0, 24); 
        
        Nasabah siti = new Nasabah("320987654321", "Siti Aminah", "08987654321");
        Emas kalungSiti = new Emas("EM-002", 10.0, 22); 

        System.out.println("=== SIMULASI PENERIMAAN GADAI ===");
        
        // Simulasi Ditolak
        cabangUtama.terimaGadai(siti, kalungSiti, 5); 
        
        // Simulasi Diterima (Budi pakai tenor 3 Bulan) -> Otomatis kena bunga 1.15%
        TransaksiGadai transaksiBudi = cabangUtama.terimaGadai(budi, cincinBudi, 3); 

        System.out.println("=== SIMULASI PEMBAYARAN ANGSURAN BUDI ===");
        if (transaksiBudi != null) {
            transaksiBudi.bayarAngsuran(); // Angsuran 1 
            transaksiBudi.bayarAngsuran(); // Angsuran 2 
            transaksiBudi.bayarAngsuran(); // Angsuran 3 (Otomatis Lunas)
        }
    }
}