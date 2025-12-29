// Subclass produk untuk kategori Kalung
public class Kalung extends Produk {

    public Kalung(String kodeProduk, String namaProduk,
                  String warna, double harga, int stok) {
        super(kodeProduk, namaProduk, "Kalung", warna, harga, stok);
    }
}
