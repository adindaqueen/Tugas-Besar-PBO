import java.util.List;
import java.util.Scanner;
import java.time.format.DateTimeFormatter;

public class BiwwBeadsApp {

    // KONFIGURASI LOGIN SEDERHANA
    // Sistem hanya menggunakan satu akun tetap 
    private static final String DEFAULT_USERNAME = "admin";
    private static final String DEFAULT_PASSWORD = "admin123";
    private static final String DEFAULT_ROLE = "ADMIN";   

    private static final Scanner scanner = new Scanner(System.in);
    private static final DataProduk dataProduk = new DataProduk();

    // Menyimpan informasi user yang sedang login saat ini.
    private static User currentUser = null;

    
    // HEADER & MENU AWAL (SEBELUM LOGIN)
    public static void printHeader() {
        System.out.println("╔══════════════════════════════════════════════════╗");
        System.out.println("║            BIWWBEADS  MANAGEMENT SYSTEM          ║");
        System.out.println("║           Stok  -  Penjualan  -  Laporan         ║");
        System.out.println("╚══════════════════════════════════════════════════╝");
        System.out.println();
    }

    // Menu awal sebelum user masuk ke menu utama.
    private static void printAuthMenu() {
        System.out.println("========= MENU AWAL =========");
        System.out.println("1. Login");
        System.out.println("0. Keluar");
        System.out.print("Pilih menu: ");
    }

    // Menu utama yang muncul setelah user berhasil login.
    private static void printMenu() {
        System.out.println("\n========= MENU UTAMA =========");
        System.out.println("Login sebagai: " + currentUser.getUsername() 
                           + " (" + currentUser.getRole() + ")");
        System.out.println("1. Tambah Produk");
        System.out.println("2. Lihat Semua Produk");
        System.out.println("3. Update Stok Produk");
        System.out.println("4. Hapus Produk");
        System.out.println("5. Buat Transaksi Penjualan");
        System.out.println("0. Logout");
        System.out.print("Pilih menu: ");
    }

    // MAIN PROGRAM (ALUR UTAMA)
    public static void main(String[] args) {
        // Tampilkan header di awal aplikasi
        printHeader();

         // ====== CEK KONEKSI DATABASE DI AWAL ======
         System.out.println("Mengecek koneksi ke database...");
         KoneksiDatabase.checkConnection();  // akan menampilkan berhasil/gagal
         
         // Inisialisasi tabel produk
         KoneksiDatabase.initDatabase();   // memastikan tabel 'produk' ada
         System.out.println(); 


        // LOOP MENU AWAL (LOGIN)
        boolean running = true;
        while (running && currentUser == null) {
        printAuthMenu();
        int pilihAwal = readInt();
        System.out.println();

        switch (pilihAwal) {
            case 1:
                handleLogin();
                break;
            case 0:
                System.out.println("Terima kasih. Program selesai.");
                running = false;
                break;
            default:
                System.out.println("Menu tidak dikenali. Silakan pilih lagi.");
        }
        System.out.println();
    }

    if (currentUser == null) {
        scanner.close();
        return;
    }

        // LOOP MENU UTAMA SETELAH LOGIN BERHASIL
        int pilihan;
        do {
            printMenu();
            pilihan = readInt();  

            System.out.println();
            switch (pilihan) {
                case 1:
                    // Menambahkan produk baru ke sistem dan database
                    tambahProduk();
                    break;
                case 2:
                    // Menampilkan seluruh produk yang tersimpan di database
                    tampilkanProduk();
                    break;
                case 3:
                    // Mengubah (menambah/mengurangi) stok produk tertentu
                    updateStokProduk();
                    break;
                case 4:
                    // Menghapus produk berdasarkan kode produk
                    hapusProduk();
                    break;
                case 5:
                    // Membuat transaksi penjualan dan mengurangi stok
                    buatTransaksi();
                    break;
                case 0:
                    // Logout dari sistem 
                    System.out.println("Logout berhasil. Sampai jumpa, " 
                    + currentUser.getUsername() + "!");
                    currentUser = null;  // reset user yang login
                    break;
                default:
                    // Jika terdapat input menu yang tidak valid
                    System.out.println("Menu tidak dikenali. Silakan pilih lagi.");
            }
            System.out.println();
        } while (pilihan != 0);  // ulangi sampai user memilih Logout

        scanner.close();
    }

    // HELPER UNTUK INPUT ANGKA 
    private static int readInt() {
        while (true) {
            try {
                int value = Integer.parseInt(scanner.nextLine());
                return value;
            } catch (NumberFormatException e) {
                System.out.print("Input harus angka. Coba lagi: ");
            }
        }
    }

    // Membaca input double dari user
    private static double readDouble() {
        while (true) {
            try {
                double value = Double.parseDouble(scanner.nextLine());
                return value;
            } catch (NumberFormatException e) {
                System.out.print("Input harus angka (boleh desimal). Coba lagi: ");
            }
        }
    }

    // FUNGSI LOGIN SEDERHANA

    // Fungsi untuk melakukan login menggunakan kredensial tetap
    private static void handleLogin() {
        System.out.println("=== Login ===");
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        // Validasi username dan password dengan kredensial default
        if (DEFAULT_USERNAME.equals(username) && DEFAULT_PASSWORD.equals(password)) {
            // Jika benar, buat objek User yang merepresentasikan user yang login
            currentUser = new User(DEFAULT_USERNAME, DEFAULT_PASSWORD, DEFAULT_ROLE);
            System.out.println("Login berhasil. Selamat datang, " + currentUser.getUsername() + "!");
        } else {
            // Jika salah, tampilkan pesan 
            System.out.println("Username atau password salah.");
        }
    }

    // MENU FUNGSI PRODUK (CRUD + TRANSAKSI)

    // Menambahkan produk baru ke sistem.
    private static void tambahProduk() {
        System.out.println("=== Tambah Produk Baru ===");
        System.out.print("Kode Produk        : ");
        String kode = scanner.nextLine();
        System.out.print("Nama Produk        : ");
        String nama = scanner.nextLine();
        System.out.print("Kategori (1=Gelang, 2=Kalung): ");
        int kat = readInt();
        String kategori = (kat == 1) ? "Gelang" : "Kalung";
        System.out.print("Warna              : ");
        String warna = scanner.nextLine();
        System.out.print("Harga              : ");
        double harga = readDouble();
        System.out.print("Stok Awal          : ");
        int stok = readInt();

        // Membuat objek produk sesuai kategori
        Produk p;
        if ("Gelang".equals(kategori)) {
            p = new Gelang(kode, nama, warna, harga, stok);
        } else {
            p = new Kalung(kode, nama, warna, harga, stok);
        }

        // Simpan produk ke database
        dataProduk.insert(p);
    }

    // Menampilkan seluruh produk yang tersimpan di database.
    private static void tampilkanProduk() {
        System.out.println("=== Daftar Produk BiwwBeads ===");
        List<Produk> list = dataProduk.getAll();
        if (list.isEmpty()) {
            System.out.println("Belum ada produk.");
        } else {
            for (Produk p : list) {
                System.out.println("- " + p);
            }
        }
    }

    // Mengubah stok produk berdasarkan kode produk.
    private static void updateStokProduk() {
        System.out.println("=== Update Stok Produk ===");
        System.out.print("Masukkan kode produk: ");
        String kode = scanner.nextLine();

        Produk p = dataProduk.getByKode(kode);
        if (p == null) {
            System.out.println("Produk dengan kode tersebut tidak ditemukan.");
            return;
        }
        System.out.println("Data produk saat ini: " + p);
        System.out.print("Tambahkan stok berapa? (boleh negatif untuk mengurangi): ");
        int jumlah = readInt();

        try {
            // Jika jumlah >= 0, tambahkan stok
            if (jumlah >= 0) {
                p.tambahStok(jumlah);
            } else {
                // Jika jumlah < 0, kurangi stok 
                p.kurangiStok(Math.abs(jumlah));
            }
            // Simpan perubahan stok ke database
            dataProduk.update(p);
        } catch (Exception e) {
            System.out.println("Gagal mengubah stok: " + e.getMessage());
        }
    }

    // Menghapus produk dari sistem berdasarkan kode produk.
    private static void hapusProduk() {
        System.out.println("=== Hapus Produk ===");
        System.out.print("Masukkan kode produk: ");
        String kode = scanner.nextLine();

        // Cari produk terlebih dahulu untuk memastikan produk ada
        Produk p = dataProduk.getByKode(kode);
        if (p == null) {
            System.out.println("Produk tidak ditemukan.");
            return;
        }

        System.out.println("Anda akan menghapus: " + p);
        System.out.print("Yakin? (y/n): ");
        String konfirmasi = scanner.nextLine();
        if (konfirmasi.equalsIgnoreCase("y")) {
            // Hapus dari database
            dataProduk.delete(kode);
        } else {
            System.out.println("Penghapusan dibatalkan.");
        }
    }

    // Membuat transaksi penjualan.
    private static void buatTransaksi() {
        System.out.println("=== Buat Transaksi Penjualan ===");

        // Membuat ID transaksi unik berdasarkan timestamp
        String idTransaksi = "TRX-" + System.currentTimeMillis();
        Transaksi trx = new Transaksi(idTransaksi);

        // Loop untuk menambahkan beberapa item ke dalam satu transaksi
        while (true) {
            System.out.print("Masukkan kode produk (atau '0' untuk selesai): ");
            String kode = scanner.nextLine();
            if (kode.equals("0")) break;  

            // Ambil produk berdasarkan kode
            Produk p = dataProduk.getByKode(kode);
            if (p == null) {
                System.out.println("Produk tidak ditemukan.");
                continue;  
            }

            System.out.println("Produk: " + p);
            System.out.print("Jumlah beli: ");
            int jumlah = readInt();

            try {
                // Tambahkan item ke transaksi 
                trx.tambahItem(p, jumlah);

                // Setelah stok di objek diperbarui, simpan perubahan ke database
                dataProduk.update(p);
                System.out.println("Item ditambahkan ke transaksi.");
            } catch (Exception e) {
                System.out.println("Gagal menambah item: " + e.getMessage());
            }
        }

        // Jika tidak ada item di transaksi, anggap transaksi dibatalkan
        if (trx.getDaftarItem().isEmpty()) {
            System.out.println("Tidak ada item. Transaksi dibatalkan.");
        } else {
            // Tampilkan ringkasan transaksi
            System.out.println("\n=== Ringkasan Transaksi ===");
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
            System.out.println("ID      : " + trx.getIdTransaksi());
            System.out.println("Tanggal : " + trx.getTanggalTransaksi().format(fmt));
            for (DetailTransaksi item : trx.getDaftarItem()) {
                System.out.println("- " + item);
            }
            System.out.println("Total   : Rp " + trx.getTotalHarga());
        }
    }
}
