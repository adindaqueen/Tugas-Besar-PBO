package src;
// Menyimpan satu baris item dalam transaksi penjualan

public class DetailTransaksi {
    private Produk produk;
    private int jumlahBeli;
    private double subtotal;

    public DetailTransaksi(Produk produk, int jumlahBeli) {
        this.produk = produk;
        this.jumlahBeli = jumlahBeli;
        this.subtotal = produk.getHarga() * jumlahBeli;
    }

    public Produk getProduk() { return produk; }
    public int getJumlahBeli() { return jumlahBeli; }
    public double getSubtotal() { return subtotal; }

    @Override
    public String toString() {
        return produk.getNamaProduk() + " x" + jumlahBeli + " = " + subtotal;
    }
}