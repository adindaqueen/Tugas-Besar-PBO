# Tugas Besar Pemrograman Berorientasi Objek
# BiwwBeads Management System

BiwwBeads Management System adalah aplikasi berbasis Java Console (CLI) yang dirancang untuk mengelola inventaris stok, penjualan, dan laporan produk manik-manik (beads). Aplikasi ini mendukung integrasi database MySQL untuk penyimpanan data produk yang persisten.

## Fitur Utama

* **Sistem Login**: Keamanan dasar menggunakan kredensial admin tetap untuk mengakses menu utama.
* **Manajemen Produk (CRUD)**:
    * **Tambah Produk**: Menambahkan produk baru (Gelang/Kalung) ke dalam database.
    * **Lihat Produk**: Menampilkan daftar seluruh produk yang tersedia.
    * **Update Stok**: Menambah atau mengurangi stok produk secara manual melalui menu utama.
    * **Hapus Produk**: Menghapus data produk dari sistem berdasarkan kode produk.
* **Transaksi Penjualan**: Membuat transaksi penjualan yang secara otomatis menghitung total harga dan mengurangi stok produk yang terjual.
* **Inisialisasi Database Otomatis**: Mengecek koneksi dan membuat tabel produk secara otomatis jika belum tersedia saat aplikasi dijalankan.

## Struktur Proyek

* `BiwwBeadsApp.java`: Kelas utama yang menangani alur program dan antarmuka pengguna.
* `KoneksiDatabase.java`: Mengelola koneksi ke database MySQL menggunakan JDBC serta inisialisasi tabel.
* `DataProduk.java`: Data Access Object (DAO) yang berisi logika SQL untuk operasi CRUD produk.
* `Produk.java`: Kelas abstrak sebagai induk untuk semua jenis produk di BiwwBeads.
* `Gelang.java` & `Kalung.java`: Subclass dari `Produk` yang merepresentasikan kategori spesifik.
* `Transaksi.java` & `DetailTransaksi.java`: Mengelola logika transaksi penjualan dan rincian item di dalamnya.
* `StokOperasi.java`: Interface yang mendefinisikan kontrak operasi penambahan dan pengurangan stok.
* `User.java`: Menyimpan informasi sesi pengguna (username, password, dan role).

## Prasyarat Sistem

1.  **Java Development Kit (JDK)** versi 8 atau yang lebih baru.
2.  **MySQL Server**.
3.  **MySQL Connector/J** (proyek ini dikonfigurasi menggunakan versi 9.5.0).

## Konfigurasi Database

1.  Pastikan MySQL Server Anda aktif.
2.  Buat database baru dengan nama `biwwbeads_db`.
3.  Konfigurasi default database:
    * **URL**: `jdbc:mysql://localhost:3306/biwwbeads_db`
    * **User**: `root`
    * **Password**: (kosong)
    * **Driver**: `com.mysql.cj.jdbc.Driver`

## Cara Menjalankan

1.  Buka proyek di VS Code atau IDE Java lainnya.
2.  Pastikan library `mysql-connector-j-9.5.0.jar` sudah terdaftar dalam `referencedLibraries` atau *classpath* proyek.
3.  Jalankan file `BiwwBeadsApp.java`.
4.  Gunakan kredensial berikut untuk login:
    * **Username**: `admin`
    * **Password**: `admin123`

## Penggunaan

Setelah login berhasil, pilih menu dengan memasukkan angka:
1.  **Tambah Produk**: Masukkan kode, nama, kategori, warna, harga, dan stok awal.
2.  **Lihat Semua Produk**: Menampilkan daftar produk yang tersimpan di database.
3.  **Update Stok Produk**: Masukkan kode produk dan jumlah stok yang ingin diubah (gunakan angka negatif untuk pengurangan).
4.  **Hapus Produk**: Masukkan kode produk untuk menghapus data secara permanen.
5.  **Buat Transaksi Penjualan**: Masukkan kode produk dan jumlah beli. Masukkan `0` untuk menyelesaikan transaksi dan melihat ringkasan.

## Identitas
**Nama** : Adinda Queen Salsabilla
**NIM**  : 2411522008
**Kelas**: A-Pemrograman Berorientasi Objek
