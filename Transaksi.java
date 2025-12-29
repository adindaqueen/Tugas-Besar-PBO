import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

// Mewakili satu transaksi penjualan di BiwwBeads
public class Transaksi {
    private String idTransaksi;
    private LocalDateTime tanggalTransaksi;
    private List<DetailTransaksi> daftarItem;
    private double totalHarga;

    public Transaksi(String idTransaksi) {
        this.idTransaksi = idTransaksi;
        this.tanggalTransaksi = LocalDateTime.now();
        this.daftarItem = new ArrayList<>();
        this.totalHarga = 0;
    }

    // Menambah item ke transaksi dan mengurangi stok produk
    public void tambahItem(Produk produk, int jumlah) throws Exception {
        produk.kurangiStok(jumlah); // bisa lempar exception kalau stok tidak cukup
        DetailTransaksi item = new DetailTransaksi(produk, jumlah);
        daftarItem.add(item);
        totalHarga += item.getSubtotal();
    }

    public String getIdTransaksi() { return idTransaksi; }
    public LocalDateTime getTanggalTransaksi() { return tanggalTransaksi; }
    public List<DetailTransaksi> getDaftarItem() { return daftarItem; }
    public double getTotalHarga() { return totalHarga; }

    @Override
    public String toString() {
        return "ID: " + idTransaksi + " | Tanggal: " + tanggalTransaksi + " | Total: " + totalHarga;
    }
}
