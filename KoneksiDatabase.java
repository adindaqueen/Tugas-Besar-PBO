import java.sql.*;

public class KoneksiDatabase {
    private static final String URL = "jdbc:mysql://localhost:3306/biwwbeads_db";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Driver MySQL tidak ditemukan: " + e.getMessage());
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static boolean checkConnection() {
    try (Connection conn = getConnection()) {
        System.out.println("Koneksi Berhasil!");
        return true;
    } catch (SQLException e) {
        System.out.println("Koneksi Gagal: " + e.getMessage());
        return false;
    }
}

    //Menambahkan tabel jika belum ada
    public static void initDatabase() {
    String sql = """
        CREATE TABLE IF NOT EXISTS produk (
            kode_produk VARCHAR(20) PRIMARY KEY,
            nama_produk VARCHAR(100) NOT NULL,
            kategori VARCHAR(20) NOT NULL,
            warna VARCHAR(50),
            harga DOUBLE NOT NULL,
            stok INT NOT NULL
        )
        """;

    try (Connection conn = getConnection();
         Statement stmt = conn.createStatement()) {

        stmt.executeUpdate(sql);
        System.out.println("Tabel 'produk' siap digunakan.");
    } catch (SQLException e) {
        System.out.println("Gagal membuat/memastikan tabel: " + e.getMessage());
    }
}

}

