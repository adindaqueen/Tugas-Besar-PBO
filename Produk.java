// Kelas abstrak sebagai induk semua produk BiwwBeads
public abstract class Produk implements StokOperasi {
    protected String kodeProduk;
    protected String namaProduk;
    protected String kategori; // Gelang / Kalung
    protected String warna;
    protected double harga;
    protected int stok;

    public Produk(String kodeProduk, String namaProduk, String kategori, String warna, double harga, int stok) {
        this.kodeProduk = kodeProduk;
        this.namaProduk = namaProduk;
        this.kategori = kategori;
        this.warna = warna;
        this.harga = harga;
        this.stok = stok;
    }

    // Getter
    public String getKodeProduk() { return kodeProduk; }
    public String getNamaProduk() { return namaProduk; }
    public String getKategori() { return kategori; }
    public String getWarna() { return warna; }
    public double getHarga() { return harga; }
    public int getStok() { return stok; }

    @Override
    public void tambahStok(int jumlah) {
        stok += jumlah;
    }

    @Override
    public void kurangiStok(int jumlah) throws Exception {
        if (jumlah > stok) {
            throw new Exception("Stok tidak mencukupi untuk produk: " + namaProduk);
        }
        stok -= jumlah;
    }

    @Override
    public String toString() {
        return kodeProduk + " - " + namaProduk + " [" + kategori + "] " + "Warna: " + warna + ", Harga: " + harga + ", Stok: " + stok;
    }
}
