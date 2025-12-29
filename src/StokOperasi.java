package src;
//Interface operasi stok produk
public interface StokOperasi {
    void tambahStok(int jumlah);
    void kurangiStok(int jumlah) throws Exception;
}
