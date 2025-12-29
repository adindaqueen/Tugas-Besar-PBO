import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// Kelas DataProduk berfungsi sebagai Data Access Object (DAO)
public class DataProduk {

    // CREATE (menambahkan data produk baru ke dalam tabel 'produk')
    public void insert(Produk p) {
        String sql = "INSERT INTO produk (kode_produk, nama_produk, kategori, warna, harga, stok) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = KoneksiDatabase.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, p.getKodeProduk());
            stmt.setString(2, p.getNamaProduk());
            stmt.setString(3, p.getKategori());
            stmt.setString(4, p.getWarna());
            stmt.setDouble(5, p.getHarga());
            stmt.setInt(6, p.getStok());

            stmt.executeUpdate();
            System.out.println("Produk berhasil ditambahkan ke database.");
        } catch (SQLException e) {
            System.out.println("Gagal menambahkan produk: " + e.getMessage());
        }
    }

    // UPDATE (mengubah data produk yang sudah ada berdasarkan kode_produk)
    public void update(Produk p) {
        String sql = "UPDATE produk SET nama_produk = ?, kategori = ?, warna = ?, harga = ?, stok = ? " +
                     "WHERE kode_produk = ?";

        try (Connection conn = KoneksiDatabase.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, p.getNamaProduk());
            stmt.setString(2, p.getKategori());
            stmt.setString(3, p.getWarna());
            stmt.setDouble(4, p.getHarga());
            stmt.setInt(5, p.getStok());
            stmt.setString(6, p.getKodeProduk());

            stmt.executeUpdate();
            System.out.println("Produk berhasil diupdate.");
        } catch (SQLException e) {
            System.out.println("Gagal mengupdate produk: " + e.getMessage());
        }
    }

    // DELETE (menghapus satu baris data produk berdasarkan kode_produk)
    public void delete(String kodeProduk) {
        String sql = "DELETE FROM produk WHERE kode_produk = ?";

        try (Connection conn = KoneksiDatabase.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, kodeProduk);
            stmt.executeUpdate();
            System.out.println("Produk berhasil dihapus.");
        } catch (SQLException e) {
            System.out.println("Gagal menghapus produk: " + e.getMessage());
        }
    }

    // READ (mengambil seluruh data produk dari tabel 'produk')
    public List<Produk> getAll() {
        List<Produk> list = new ArrayList<>();
        String sql = "SELECT * FROM produk";

        try (Connection conn = KoneksiDatabase.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String kode = rs.getString("kode_produk");
                String nama = rs.getString("nama_produk");
                String kategori = rs.getString("kategori");
                String warna = rs.getString("warna");
                double harga = rs.getDouble("harga");
                int stok = rs.getInt("stok");

                Produk p;
                if ("Gelang".equalsIgnoreCase(kategori)) {
                    p = new Gelang(kode, nama, warna, harga, stok);
                } else {
                    p = new Kalung(kode, nama, warna, harga, stok);
                }

                list.add(p);
            }

        } catch (SQLException e) {
            System.out.println("Gagal mengambil data produk: " + e.getMessage());
        }

        return list;
    }

    // READ (mengambil satu produk berdasarkan kode_produk)
    public Produk getByKode(String kodeProduk) {
        String sql = "SELECT * FROM produk WHERE kode_produk = ?";

        try (Connection conn = KoneksiDatabase.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, kodeProduk);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String kode = rs.getString("kode_produk");
                    String nama = rs.getString("nama_produk");
                    String kategori = rs.getString("kategori");
                    String warna = rs.getString("warna");
                    double harga = rs.getDouble("harga");
                    int stok = rs.getInt("stok");

                    if ("Gelang".equalsIgnoreCase(kategori)) {
                        return new Gelang(kode, nama, warna, harga, stok);
                    } else {
                        return new Kalung(kode, nama, warna, harga, stok);
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Gagal mengambil produk: " + e.getMessage());
        }

        // Jika tidak ditemukan, kembalikan null
        return null;
    }
}
