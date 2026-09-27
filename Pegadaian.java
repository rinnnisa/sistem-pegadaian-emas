public class Pegadaian {

    public TransaksiGadai terimaGadai(Nasabah nasabah, Emas emas, int lamaAngsuran) {
        
        if (lamaAngsuran != 3 && lamaAngsuran != 6 && lamaAngsuran != 12) {
            System.out.println("TRANSAKSI DITOLAK: Pilihan angsuran untuk " + nasabah.getNama() + " tidak valid. Hanya tersedia tenor 3, 6, atau 12 bulan.\n");
            return null;
        }

        double nilaiTaksiran = emas.getTaksiran();
        double nominalPinjaman = nilaiTaksiran * 0.85;

        TransaksiGadai transaksiBaru = new TransaksiGadai(nasabah, emas, nilaiTaksiran, nominalPinjaman, lamaAngsuran);
        
        System.out.println("TRANSAKSI BERHASIL: Pengajuan gadai dari " + nasabah.getNama() + " diterima.");
        transaksiBaru.cetakStruk();
        
        return transaksiBaru;
    }
}