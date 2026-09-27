import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID; 

public class TransaksiGadai {
    private String idTransaksi; 
    private Nasabah nasabah;
    private Emas emas;
    private double hargaAwal;
    private double nominalPinjaman;
    private int lamaAngsuran;
    private int sisaAngsuran;
    private double persentaseBunga; // Menyimpan besaran persentase bunga
    private double cicilanPerBulan;
    private double sisaTagihan; 
    private boolean isLunas;
    
    private LocalDate tanggalTransaksi; 
    private LocalDate tanggalJatuhTempo; 
    private int bulanPembayaranKe;

    public TransaksiGadai(Nasabah nasabah, Emas emas, double hargaAwal, double nominalPinjaman, int lamaAngsuran) {
        this.idTransaksi = "TRX-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        
        this.nasabah = nasabah;
        this.emas = emas;
        this.hargaAwal = hargaAwal;
        this.nominalPinjaman = nominalPinjaman;
        this.lamaAngsuran = lamaAngsuran;
        this.sisaAngsuran = lamaAngsuran;
        this.isLunas = false;
        this.bulanPembayaranKe = 1;
        
        this.tanggalTransaksi = LocalDate.now(); 
        this.tanggalJatuhTempo = this.tanggalTransaksi.plusMonths(lamaAngsuran); 

        // Logika penentuan persentase bunga berdasarkan tenor
        if (lamaAngsuran == 3) {
            this.persentaseBunga = 1.15;
        } else if (lamaAngsuran == 6) {
            this.persentaseBunga = 1.25;
        } else if (lamaAngsuran == 12) {
            this.persentaseBunga = 1.40;
        }

        // Hitung total bunga dan cicilan per bulan
        double totalBunga = ((this.persentaseBunga / 100) * lamaAngsuran) * nominalPinjaman;
        double totalPinjaman = nominalPinjaman + totalBunga;
        this.cicilanPerBulan = totalPinjaman / lamaAngsuran;
        
        this.sisaTagihan = totalPinjaman; 
    }

    public void cetakStruk() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");
        System.out.println("\n--- STRUK TRANSAKSI GADAI ---");
        System.out.println("ID Transaksi        : " + idTransaksi); 
        System.out.println("Tanggal Transaksi   : " + tanggalTransaksi.format(formatter));
        System.out.println("Tanggal Jatuh Tempo : " + tanggalJatuhTempo.format(formatter));
        System.out.println("Nama Nasabah        : " + nasabah.getNama());
        System.out.println("Kode Emas           : " + emas.getKodeBarang());
        
        System.out.println("Harga Awal (Taksiran): Rp " + String.format("%,.0f", hargaAwal));
        System.out.println("Pinjaman Cair (85%) : Rp " + String.format("%,.0f", nominalPinjaman));
        
        System.out.println("Bunga per Bulan     : " + persentaseBunga + "%"); // Tampilkan besaran bunga
        System.out.println("Total Tagihan       : Rp " + String.format("%,.0f", sisaTagihan));
        System.out.println("Tenor Angsuran      : " + lamaAngsuran + " Bulan");
        System.out.println("Cicilan / Bulan     : Rp " + String.format("%,.0f", cicilanPerBulan));
        System.out.println("-----------------------------\n");
    }

    public void bayarAngsuran() {
        if (isLunas) {
            System.out.println("Gagal: Transaksi gadai milik " + nasabah.getNama() + " sudah LUNAS!");
            return;
        }

        String idPembayaran = "PAY-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        LocalDate tanggalBayar = tanggalTransaksi.plusMonths(bulanPembayaranKe); 
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");

        System.out.println("======================================");
        System.out.println("Pembayaran Angsuran Bulan Ke-" + bulanPembayaranKe + " Berhasil!");
        System.out.println("ID Pembayaran           : " + idPembayaran); 
        System.out.println("Untuk ID Transaksi      : " + idTransaksi);
        System.out.println("Tanggal Bayar           : " + tanggalBayar.format(formatter)); 
        
        if (bulanPembayaranKe < lamaAngsuran) {
            LocalDate jatuhTempoBulanDepan = tanggalTransaksi.plusMonths(bulanPembayaranKe + 1);
            System.out.println("Jatuh Tempo Berikutnya  : " + jatuhTempoBulanDepan.format(formatter));
        }

        System.out.println("Nasabah                 : " + nasabah.getNama());
        System.out.println("Sisa Tagihan Sebelumnya : Rp " + String.format("%,.0f", sisaTagihan));
        System.out.println("Nominal Dibayar         : Rp " + String.format("%,.0f", cicilanPerBulan));
        
        sisaTagihan -= cicilanPerBulan;
        sisaAngsuran--;
        bulanPembayaranKe++; 
        
        if (sisaTagihan < 1) sisaTagihan = 0; 
        
        System.out.println("Sisa Tagihan Sekarang   : Rp " + String.format("%,.0f", sisaTagihan));
        System.out.println("Sisa Tenor              : " + sisaAngsuran + " bulan");
        System.out.println("======================================");

        if (sisaAngsuran == 0) {
            isLunas = true;
            System.out.println(">>> SELAMAT! Pinjaman Gadai telah LUNAS. Barang emas bisa diambil.\n");
        }
    }
}