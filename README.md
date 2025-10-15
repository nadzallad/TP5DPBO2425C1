
//Janji 

Saya Nadzalla Diva Asmara Sutedja dengan Nim 2408095 mengerjakan TP5 dalam mata kuliah Desain dan Pemrograman Berorientasi Objek untuk keberkahan-Nya maka saya tidak akan melakukan kecurangan seperti yang telah di spesifikasikan

//Desain Program

Merupakan pencatatan data dari sebuah toko elektronik yang didalamnya menampilkan atribut Id, Nama, Harga, Kategori, Merek, Negara Produksi, serta status ketersediaan barang dimana atribut - atribut tersebut nantinya dapat dapat melakukan aksi CRUD( Create, Read, Update, Delete) yang terhubung pada database sehingga apabila aksi-aksi tersebut dilakukan akan terjadi perubahan di database dan akan tersimpan sehingga penggunaan ArrayList sudah tidak diperlukan.  Pada saat menambahkan data baru, data harus diisi secara lengkap terlebih dahulu. Apabila data belum diisi dengan lengkap maka akan muncul peringatan untuk mengisi keseluruhan data atau peringatan mengenai format data seharusnya (peringatan harga berupa angka). Begitu juga pada saat melakukan update atau perubahan, data harus diisi lengkap atau perubahan tidak akan bisa dilakukan. Pada saat melakukan update semua atribut yang ada dapat diubah kecuali id dari data tersebut karena digunakan untuk membedakan dengan produk lainnya dan mencegah adanya inkonsistensi data. Id juga dibuat agar tidak ada id  baru yang sama pada saat insert atau  update data.

//Alur Program
 
1. Terdapat  atribut No(dibuat otomatis bukan input), Id, Nama, Harga, Kategori, Merek, Negara Produksi, serta status ketersediaan. User dapat melakukan aksi CRUD pada data toko elektronik tersebut.
2. User dapat menambahkan data dengan mengisi lengkap atribut dan tentunya dengan format yang benar. Jika input yang dimasukan tidak benar atau tidak lengkap akan ada error handling dan data tidak akan berhasil dimasukan.
3. Setelah data dimasukan dan tampil dalam tabel produk maka user dapat melihat data - data yang sebelumnya dimasukan dengan melakukan running program atau melihat pada database.
4. Setelah memiliki data user dapat melakukan perubahan pada data yang sudah dimasukan. Perubahan tersebut dapat dilakukan di seluruh atribut yang ada kecuali nomor karena sudah otomatis dibuat dan id produknya.
5. Jika data yang dimasukan sudah tidak sesuai dengan kebutuhan atau data sudah tidak dibutuhkan di toko elektronik, user dapat menghapus data tersebut dengan melakukan klik pada data yang ingin dihapus kemudian melakukan klik pada tombol delete. Sebelum data dihapus program akan meminta konfirmasi mengenai keyakinan apa benar user ingin menghapus data tersebut. Setelah melakukan konfirmasi data akan benar benar terhapus dan sudah tidak bisa dilihat lagi di dalam tabel.

// Dokumentasi 
1. Error handling insert dan pengecekan id tidak boleh sama 

https://github.com/user-attachments/assets/6983910a-5778-424c-b864-2c231c9767e0


2. Berhasil menambahkan data ke dalam database 


https://github.com/user-attachments/assets/0407131e-bfa9-4084-b384-e9dfe6f23b07


3. Error handling update dan pada saat update id tidak dapat diubah
   


https://github.com/user-attachments/assets/71070abe-3663-4008-ba22-57413f8e6aab


4. Cancel jika tadi melakukan update agar bisa menambah data
   

https://github.com/user-attachments/assets/252cfc34-1ed0-4e9e-ba79-ac4fdf81d344


5. Menghapus data di database

https://github.com/user-attachments/assets/8f7112ac-3fd8-4d14-a69f-355fa044ffcf

