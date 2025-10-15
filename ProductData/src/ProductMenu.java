import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;


public class ProductMenu extends JFrame {
    public static void main(String[] args) {
        // buat object window
        ProductMenu menu = new ProductMenu();

        // atur ukuran window
        menu.setSize(700, 600);

        // letakkan window di tengah layar
        menu.setLocationRelativeTo(null);

        // isi window
        menu.setContentPane(menu.mainPanel);

        // ubah warna background
        menu.getContentPane().setBackground(Color.WHITE);

        // tampilkan window
        menu.setVisible(true);

        // agar program ikut berhenti saat window diclose
        menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    // index baris yang diklik
    private int selectedIndex = -1;

    private Database database;
    private JPanel mainPanel;
    private JTextField idField;
    private JTextField namaField;
    private JTextField hargaField;
    private JTable productTable;
    private JButton addUpdateButton;
    private JButton cancelButton;
    private JComboBox<String> kategoriComboBox;
    private JButton deleteButton;
    private JLabel titleLabel;
    private JLabel idLabel;
    private JLabel namaLabel;
    private JLabel hargaLabel;
    private JLabel kategoriLabel;

    //tambahkan
    private JTextField merkField;
    private JLabel merkLabel;
    private JLabel ketersediaan;
    private JComboBox<String> ProduksiComboBox;
    private JLabel produksi;
    private JRadioButton tidakTersediaRadioButton;
    private JRadioButton tersediaRadioButton;
    private ButtonGroup ketersediaanGroup;



    // constructor
    public ProductMenu() {
        // Inisialisasi ButtonGroup
        ketersediaanGroup = new ButtonGroup();

        // Masukkan radio button ke dalam ButtonGroup
        ketersediaanGroup.add(tersediaRadioButton);
        ketersediaanGroup.add(tidakTersediaRadioButton);
//        tersediaRadioButton.addActionListener(e -> filterTableByAvailability());
//        tidakTersediaRadioButton.addActionListener(e -> filterTableByAvailability());




        database = new Database();
        // isi tabel produk
        productTable.setModel(setTable());
        // ubah styling title
        titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD, 20f));
        // atur isi combo box
        String[] kategoriData = {"Elektronik", "Makanan", "Minuman", "Pakaian", "Alat Tulis"};
        kategoriComboBox.setModel(new DefaultComboBoxModel<>(kategoriData));

        // atur isi combo box negara
        String[] Produksi = {"Korea ", "Indonesia", "Jepang", "Swiss", "Switzerland", "USA", "Jerman", "Swedia"};
        ProduksiComboBox.setModel(new DefaultComboBoxModel<>(Produksi));

        // sembunyikan button delete jika belum ada index terpilih
        deleteButton.setVisible(false);

        // saat tombol add/update ditekan
        addUpdateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedIndex == -1) {
                    insertData();
                } else {
                    updateData();
                }
            }
        });

        // saat tombol delete ditekan
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int konfirmasi = JOptionPane.showConfirmDialog(null, "Yakin ingin menghapus ?", "Konfirmasi hapus", JOptionPane.YES_NO_OPTION);
                if(konfirmasi == JOptionPane.YES_OPTION) {
                    System.out.println("yakin ingin menghapus ?");
                    deleteData();
                }
                else{
                    System.out.println("batal menghapus ?");
                }
            }
        });

        // saat tombol cancel ditekan
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearForm();
                //id bisa kembali diisi
                idField.setEditable(true);

            }

        });


        productTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                // ubah selectedIndex menjadi baris tabel yang diklik
                selectedIndex = productTable.getSelectedRow();

                // simpan value textfield dan combo box
                String curId = productTable.getModel().getValueAt(selectedIndex, 1).toString();
                String curNama = productTable.getModel().getValueAt(selectedIndex, 2).toString();
                String curHarga = productTable.getModel().getValueAt(selectedIndex, 3).toString();
                String curKategori = productTable.getModel().getValueAt(selectedIndex, 4).toString();
                String curMerk = productTable.getModel().getValueAt(selectedIndex, 5).toString();
                String curProduksi = productTable.getModel().getValueAt(selectedIndex, 6).toString();
                String curKetersediaan = productTable.getValueAt(selectedIndex, 7).toString();

                // ubah isi textfield dan combo box
                idField.setText(curId);
                namaField.setText(curNama);
                hargaField.setText(curHarga);
                kategoriComboBox.setSelectedItem(curKategori);
                merkField.setText(curMerk);
                ProduksiComboBox.setSelectedItem(curProduksi);

                if (curKetersediaan.equalsIgnoreCase("Tersedia")){
                    tersediaRadioButton.setSelected(true);
                }
                else{
                    tidakTersediaRadioButton.setSelected(true);
                }


                // ubah button "Add" menjadi "Update"
                addUpdateButton.setText("Update");

                // tampilkan button delete
                deleteButton.setVisible(true);
                idField.setEditable(false);
            }
        });
    }

    public final DefaultTableModel setTable() {
        // tentukan kolom tabel
        Object[] cols = {"No", "ID Produk", "Nama", "Harga", "Kategori", "Merek","Negara Produksi", "Ketersediaan"};
        // buat objek tabel dengan kolom yang sudah dibuat
        DefaultTableModel tmp = new DefaultTableModel(null, cols);

        try {
            ResultSet resultSet = database.executeQuery("SELECT * FROM product");
            int i = 0;
            while(resultSet.next()) {
                Object[] row = new Object[8];
                row[0] = i + 1 ;
                row[1] = resultSet.getString("Id");
                row[2] = resultSet.getString("nama");
                row[3] = resultSet.getString("harga");
                row[4] = resultSet.getString("kategori");
                row[5] = resultSet.getString("merek");
                row[6] = resultSet.getString("produksi");
                row[7] = Objects.equals(resultSet.getString("ketersediaan"), "1") ? "Tersedia" : "Tidak Tersedia";
                tmp.addRow(row);
                i++;
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return tmp;
    }

    public void insertData() {
        try {
            // ambil value dari textfield dan combobox
            String id = idField.getText();
            String nama = namaField.getText();
            double harga = Double.parseDouble(hargaField.getText());
            String kategori = kategoriComboBox.getSelectedItem().toString();
            String merk = merkField.getText();
            String produksi = ProduksiComboBox.getSelectedItem().toString();
            String ketersediaan = tersediaRadioButton.isSelected() ? "1" : "0";
            boolean tersedia = tersediaRadioButton.isSelected();

            // Cek ID duplikat di database
            String sqlCheck = "SELECT COUNT(*) AS jumlah FROM product WHERE id = '" + id + "'";
            ResultSet rs = database.executeQuery(sqlCheck);

            if (rs.next() && rs.getInt(1) > 0) {
                JOptionPane.showMessageDialog(null, "ID sudah ada! Gunakan ID lain.", "Error", JOptionPane.ERROR_MESSAGE);
                return; // hentikan proses insert
            }
            //pesan eror untuk memasukan id produk
            if( id.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Masukan Id Produk", "Peringatan", JOptionPane.WARNING_MESSAGE);
                return;
            }

            //pesan eror untuk memasukan nam produk
            if( nama.isEmpty() ) {
                JOptionPane.showMessageDialog(null, "Masukan Nama Produk", "Peringatan", JOptionPane.WARNING_MESSAGE);
                return;
            }

            //pesan eror untuk memasukan kategori produk
            if( kategori.isEmpty() ) {
                JOptionPane.showMessageDialog(null, "Masukan Kategori Produk", "Peringatan", JOptionPane.WARNING_MESSAGE);
                return;
            }

            //pesan eror untuk memasukan merek produk
            if( merk.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Masukan Merek Produk", "Peringatan", JOptionPane.WARNING_MESSAGE);
                return;
            }

            //pesan eror untuk memasukan negara produksi produk
            if( produksi.isEmpty() ) {
                JOptionPane.showMessageDialog(null, "Masukan Negara Produksi", "Peringatan", JOptionPane.WARNING_MESSAGE);
                return;
            }

            //pesan eror untuk memasukan ketersediaan produk
            if (!tersediaRadioButton.isSelected() && !tidakTersediaRadioButton.isSelected()) {
                JOptionPane.showMessageDialog(null, "Pilih ketersediaan produk!", "Peringatan", JOptionPane.WARNING_MESSAGE);
                return;
            }

            //masukan kedalam database
            String sqlQuery = "INSERT INTO product Values('"+ id +"', '"+ nama +"', '"+ harga +"', '"+ kategori +"', '"+ merk +"', '"+ produksi +"', '"+ ketersediaan +"')";
            database.insertUpdateDeleteQuery(sqlQuery);

            // update tabel
            productTable.setModel(setTable());

            // bersihkan form
            clearForm();

            // feedback
            System.out.println("Insert Berhasil");
            JOptionPane.showMessageDialog(null, "Data Berhasil Ditambahkan");

        }
        //jika belum diisi atau harga bukan angka
        catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Harga Harus Berupa Angka !", "Error", JOptionPane.ERROR_MESSAGE);
        }

        //jika id ada yang sama
        catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Id sudah ada !", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void updateData() {
        try{

        // Ambil data baru dari form
        String id = idField.getText();
        String nama = namaField.getText();
        double harga = Double.parseDouble(hargaField.getText());
        String kategori = kategoriComboBox.getSelectedItem().toString();
        String merk = merkField.getText();
        String produksi = ProduksiComboBox.getSelectedItem().toString();
        boolean tersedia = tersediaRadioButton.isSelected();


        //pesan eror untuk memasukan nama produk
        if( nama.isEmpty() ) {
            JOptionPane.showMessageDialog(null, "Masukan Nama Produk", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        //pesan eror untuk memasukan kategori produk
        if( kategori.isEmpty() ) {
            JOptionPane.showMessageDialog( null, "Masukan Kategori Produk", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        //pesan eror untuk memasukan merek produk
        if( merk.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Masukan Merek Produk", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        //pesan eror untuk memasukan negara produksi produk
        if( produksi.isEmpty() ) {
            JOptionPane.showMessageDialog(null, "Masukan Negara Produksi", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        //pesan eror untuk memasukan ketersediaan produk
        if (!tersediaRadioButton.isSelected() && !tidakTersediaRadioButton.isSelected()) {
            JOptionPane.showMessageDialog(null, "Pilih ketersediaan produk!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        //pesan eror jika ketersediaan belum dipilih
        if (!tersediaRadioButton.isSelected() && !tidakTersediaRadioButton.isSelected()) {
            JOptionPane.showMessageDialog(null, "Pilih ketersediaan produk!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }


        // update tabel
        productTable.setModel(setTable());

        String sqlQuery = "UPDATE product SET " +
                "nama = '" + nama + "', " +
                "harga = '" + harga + "', " +
                "kategori = '" + kategori + "', " +
                "merek = '" + merk + "', " +
                "produksi = '" + produksi + "', " +
                "ketersediaan = '" + (tersedia ? "1" : "0") + "' " +
                "WHERE id = '" + id + "'";
        database.insertUpdateDeleteQuery(sqlQuery);


        // bersihkan form
        clearForm();

        // feedback
        System.out.println("Update Berhasil");
        JOptionPane.showMessageDialog(null, "Data berhasil diubah");
        }catch(NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Harga Harus Berupa Angka !", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void deleteData() {
        // Ambil ID langsung dari tabel yang diklik user
        String id = productTable.getValueAt(selectedIndex, 1).toString();

        // update tabel
        productTable.setModel(setTable());

        // hapus dari database
        String sqlQuery = "DELETE FROM product WHERE id = '" + id + "'";
        database.insertUpdateDeleteQuery(sqlQuery);

        // bersihkan form
        clearForm();

        // feedback
        System.out.println("Delete Berhasil");
        JOptionPane.showMessageDialog(null, "Data berhasil dihapus ");

    }

    public void clearForm() {
        // kosongkan semua texfield dan combo box
        idField.setText("");
        namaField.setText("");
        hargaField.setText("");
        kategoriComboBox.setSelectedIndex(0);
        merkField.setText("");
        ProduksiComboBox.setSelectedIndex(0);
        ketersediaanGroup.clearSelection();

        // ubah button "Update" menjadi "Add"
        addUpdateButton.setText("Add");

        // sembunyikan button delete
        deleteButton.setVisible(false);

        // ubah selectedIndex menjadi -1 (tidak ada baris yang dipilih)
        selectedIndex = -1;
    }

    public DefaultTableModel filterTableByAvailability() {
        boolean showTersedia = tersediaRadioButton.isSelected();
        boolean showTidakTersedia = tidakTersediaRadioButton.isSelected();

        // tentukan kolom tabel
        Object[] cols = {"No", "ID Produk", "Nama", "Harga", "Kategori", "Merek","Negara Produksi", "Ketersediaan"};
        // buat objek tabel dengan kolom yang sudah dibuat
        DefaultTableModel tmp = new DefaultTableModel(null, cols);

        try {
            ResultSet resultSet = database.executeQuery("SELECT * FROM product WHERE ketersediaan = true");
            int i = 0;
            while(resultSet.next()) {
                Object[] row = new Object[8];
                row[0] = i + 1 ;
                row[1] = resultSet.getString("Id");
                row[2] = resultSet.getString("nama");
                row[3] = resultSet.getString("harga");
                row[4] = resultSet.getString("kategori");
                row[5] = resultSet.getString("merek");
                row[6] = resultSet.getString("produksi");
                row[7] = Boolean.parseBoolean(resultSet.getString("ketersediaan")) ? "Tersedia" : "Tidak Tersedia";
                tmp.addRow(row);
                i++;
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return tmp;
    }
}