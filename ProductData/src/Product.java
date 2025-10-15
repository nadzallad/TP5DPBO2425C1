public class Product {
    private String id;
    private String nama;
    private double harga;
    private String kategori;
    private String merk;
    private boolean tersedia;
    private String produksi;

    public Product(String id, String nama, double harga, String kategori, String merk, String produksi, boolean tersedia) {
        this.id = id;
        this.nama = nama;
        this.harga = harga;
        this.kategori = kategori;
        this.merk = merk;
        this.produksi = produksi;
        this.tersedia = tersedia;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public void setHarga(double harga) {
        this.harga = harga;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public String getId() {
        return this.id;
    }

    public String getNama() {
        return this.nama;
    }

    public double getHarga() {
        return this.harga;
    }

    public String getKategori() {
        return this.kategori;
    }

    public String getMerk() {
        return this.merk;
    }
    public void setMerk(String merk) {this.merk = merk;}

    public boolean isTersedia() {
        return tersedia;
    }

    public void setTersedia(boolean tersedia) {
        this.tersedia = tersedia;
    }

    public String getProduksi() {
        return this.produksi;
    }

    public void setProduksi(String produksi) {
        this.produksi = produksi;
    }
}