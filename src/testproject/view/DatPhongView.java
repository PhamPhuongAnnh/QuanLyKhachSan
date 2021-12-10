/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package testproject.view;

import com.opencsv.exceptions.CsvValidationException;
import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import testproject.model.DatPhong;
import testproject.model.DocGhiFile;
import testproject.model.KhachHang;
import testproject.model.Phong;

/**
 *
 * @author phuon
 */
public class DatPhongView extends javax.swing.JFrame {

    /**
     * Creates new form DatPhongView
     */
    private DefaultTableModel tableModel;
    private int id = 0;
    private static final String curentDir = System.getProperty("user.dir");
    private static final String separator = File.separator;
    private static final String PATH_FILE_CSV_DATPHONG = curentDir + separator + "data" + separator + "DatPhong.csv";
    private static final String PATH_FILE_CSV_khachHang = curentDir + separator + "data" + separator + "KhachHang.csv";
    private static final String PATH_FILE_CSV_Phong = curentDir + separator + "data" + separator + "Phong.csv";

    private DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
    private DocGhiFile dc = new DocGhiFile();
    private List<DatPhong> listDatPhong = new ArrayList<>();
    private List<KhachHang> listKH = new ArrayList<>();
    private List<Phong> listPhong = new ArrayList<>();

    private File fKH = new File(PATH_FILE_CSV_khachHang);
    private File fDP = new File(PATH_FILE_CSV_DATPHONG);
    private File fP = new File(PATH_FILE_CSV_Phong);

    public DatPhongView() {
        
        initComponents();
        this.setLocationRelativeTo(null);
        tableModel = (DefaultTableModel) tblDatPhong.getModel();

        if (fDP.exists()) {
            try {
                listDatPhong = dc.docFileDatPhong();
                if (listDatPhong.size() == 0) {
                    fDP.delete();
                    DatPhongView dp = new DatPhongView();
                    dp.setVisible(true);
                } else {
                    String ma = listDatPhong.get(listDatPhong.size() - 1).getMaDatPhong();
                    id = Integer.parseInt(ma.substring(2)) + 1;
                    hienThiDatPhong(listDatPhong);
                    resetForm();
                }
            } catch (CsvValidationException ex) {
                Logger.getLogger(DatPhongView.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ParseException ex) {
                Logger.getLogger(DatPhongView.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (fKH.exists()) {
            try {
                listKH = dc.docFileKhachHang();
            } catch (CsvValidationException ex) {
                Logger.getLogger(DatPhongView.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (fP.exists()) {
            try {
                listPhong = dc.docFilePhong();
            } catch (CsvValidationException ex) {
                Logger.getLogger(DatPhongView.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void hienThiDatPhong(List<DatPhong> listDatPhong) {
        tableModel.setRowCount(0);
        for (DatPhong item : listDatPhong) {
            String maDatPhong = item.getMaDatPhong();
            String maphong = item.getMaPhong();
            String maKhachHang = item.getMaKhachHang();
            Date ngayDat = item.getNgayDat();
            String ngayDat1 = df.format(ngayDat);
            Date ngayTra = item.getNgayTra();
            String ngayTra1 = df.format(ngayTra);
            double tongTien = item.getTongTien();
            tableModel.addRow(new Object[]{maDatPhong, maphong, maKhachHang, ngayDat1, ngayTra1, tongTien});
        }
    }

    public void chuyenTrangThai(List<Phong> DanhSachDatPhong) {
        for (Phong item : DanhSachDatPhong) {
            if (item.getMaPhong().equals(txtMaPhong.getText())) {
                item.setTrangThai("Da Dat");
            }
        }
        fP.delete();
        dc.ghiFilePhong(listPhong);
    }

    public boolean kiemTraMaPhong() {
        boolean flag = false;
        for (Phong item : listPhong) {
            if (item.getMaPhong().equals(txtMaPhong.getText())) {
                flag = true;
            }
        }
        if (flag == false) {
            return false;
        } else {
            return true;
        }
    }

    public boolean kiemTraMaKhachHang() {
        boolean flag = false;
        for (KhachHang item : listKH) {
            if (item.getMaKhachHang().equals(txtMaKhachHang.getText())) {
                flag = true;
                System.out.println(item.getMaKhachHang());
            }
        }
        if (flag == false) {
            return false;
        } else {
            return true;
        }
    }

    public boolean kiemTraTrangThai() {
        for (Phong item : listPhong) {
            if (item.getTrangThai().equals("Con Trong")) {
                return true;
            }
        }
        return false;
    }

    public double tinhTongTien(double thoiGian, double giaPhong) {
        double tongTien = 0;
        if (thoiGian < 24) {
            if (thoiGian > 0 && thoiGian <= 2) {
                tongTien = giaPhong * 15 / 100;
            } else if (thoiGian > 2 && thoiGian <= 5) {
                tongTien = giaPhong * 25 / 100;
            } else if (thoiGian > 5) {
                tongTien = giaPhong * thoiGian * 10 / 100;
            }
        } else if (thoiGian >= 24) {
            int a = (int) (thoiGian / 24);
            double b = thoiGian - (double) 24 * a;
            System.out.println(a);
            if (b > 0 && b <= 2) {
                tongTien = giaPhong * 15 / 100 + giaPhong * a;
            } else if (b > 2 && b <= 5) {
                tongTien = giaPhong * 25 / 100 + giaPhong * a;
            } else if (b > 5) {
                tongTien = giaPhong * 10 / 100 * b + giaPhong * a;
            } else if (b == 0) {
                tongTien = giaPhong * a;
            }
        }
        System.out.println(tongTien);
        return tongTien;
    }

    public void resetForm() {
        txtMaKhachHang.setText("");
        txtMaPhong.setText("");
        Date date = java.util.Calendar.getInstance().getTime();
        txtNgayDat.setDate(date);
        txtNgayTra.setDate(date);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtMaPhong = new javax.swing.JTextField();
        txtMaKhachHang = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtNgayDat = new com.toedter.calendar.JDateChooser();
        txtNgayTra = new com.toedter.calendar.JDateChooser();
        txtTimKiem = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        boxSapXep = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDatPhong = new javax.swing.JTable();
        btnThemThongTin = new javax.swing.JButton();
        btnChinhSua = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnTimKiem = new javax.swing.JButton();
        btnSapXep = new javax.swing.JButton();
        btbPhong = new javax.swing.JButton();
        btnKhachHang = new javax.swing.JButton();
        btnThanhToan = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel2.setBackground(new java.awt.Color(190, 220, 227));

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Nhập thông đặt phòng");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel2.setText("Mã Phòng:");

        txtMaPhong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMaPhongActionPerformed(evt);
            }
        });

        txtMaKhachHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMaKhachHangActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel3.setText("Mã Khách hàng:");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel4.setText("Ngày đặt: ");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel5.setText("Ngày trả:");

        txtNgayDat.setDate(new java.util.Date(1637914032000L));
        txtNgayDat.setDateFormatString("dd-MM-yyyy HH:mm:ss");

        txtNgayTra.setDate(new java.util.Date(1637914032000L));
        txtNgayTra.setDateFormatString("dd-MM-yyyy HH:mm:ss");
        txtNgayTra.setDoubleBuffered(false);

        txtTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTimKiemActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel6.setText("Chức năng:");

        boxSapXep.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        boxSapXep.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Ma Dat Phong", "Ma Phong", "Ma KH", "NgayDat", "NgayTra", "Tong Tien" }));

        tblDatPhong.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã đặt phòng", "Mã phòng", "Mã khách hàng", "Ngày đặt", "Ngày trả", "Tổng tiền"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Double.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tblDatPhong.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDatPhongMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblDatPhong);
        if (tblDatPhong.getColumnModel().getColumnCount() > 0) {
            tblDatPhong.getColumnModel().getColumn(0).setMinWidth(100);
            tblDatPhong.getColumnModel().getColumn(0).setMaxWidth(100);
            tblDatPhong.getColumnModel().getColumn(1).setMinWidth(100);
            tblDatPhong.getColumnModel().getColumn(1).setMaxWidth(100);
            tblDatPhong.getColumnModel().getColumn(2).setMinWidth(100);
            tblDatPhong.getColumnModel().getColumn(2).setMaxWidth(100);
            tblDatPhong.getColumnModel().getColumn(3).setMinWidth(200);
            tblDatPhong.getColumnModel().getColumn(3).setMaxWidth(200);
            tblDatPhong.getColumnModel().getColumn(4).setMinWidth(200);
            tblDatPhong.getColumnModel().getColumn(4).setMaxWidth(200);
        }

        btnThemThongTin.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btnThemThongTin.setText("Thêm thông tin");
        btnThemThongTin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemThongTinActionPerformed(evt);
            }
        });

        btnChinhSua.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btnChinhSua.setText("Chỉnh sửa");
        btnChinhSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChinhSuaActionPerformed(evt);
            }
        });

        btnXoa.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btnXoa.setText("Xóa");
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });

        btnTimKiem.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btnTimKiem.setText("Tìm kiếm");
        btnTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimKiemActionPerformed(evt);
            }
        });

        btnSapXep.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btnSapXep.setText("Sắp xếp");
        btnSapXep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSapXepActionPerformed(evt);
            }
        });

        btbPhong.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btbPhong.setText("Phòng");
        btbPhong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btbPhongActionPerformed(evt);
            }
        });

        btnKhachHang.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btnKhachHang.setText("KH");
        btnKhachHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKhachHangActionPerformed(evt);
            }
        });

        btnThanhToan.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btnThanhToan.setText("Thanh Toán");
        btnThanhToan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThanhToanActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane1)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtMaKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtMaPhong, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(40, 40, 40)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addGap(18, 18, 18)
                                .addComponent(txtNgayTra, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(boxSapXep, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnThanhToan, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jLabel4)
                                    .addGap(18, 18, 18)
                                    .addComponent(txtNgayDat, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(98, 98, 98)
                        .addComponent(btnSapXep)
                        .addGap(36, 36, 36)
                        .addComponent(btnThemThongTin)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 42, Short.MAX_VALUE)
                        .addComponent(btnChinhSua)
                        .addGap(39, 39, 39)
                        .addComponent(btnXoa)
                        .addGap(28, 28, 28)
                        .addComponent(btnTimKiem)
                        .addGap(18, 18, 18)
                        .addComponent(btbPhong)))
                .addGap(26, 26, 26)
                .addComponent(btnKhachHang)
                .addGap(190, 190, 190))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2)
                        .addComponent(txtMaPhong, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtNgayDat, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txtMaKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtNgayTra, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(boxSapXep, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnThanhToan, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(46, 46, 46)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThemThongTin)
                    .addComponent(btnChinhSua)
                    .addComponent(btnXoa)
                    .addComponent(btnTimKiem)
                    .addComponent(btnSapXep)
                    .addComponent(btbPhong, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(50, 50, 50)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 422, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtMaPhongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMaPhongActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMaPhongActionPerformed

    private void txtMaKhachHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMaKhachHangActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMaKhachHangActionPerformed

    private void txtTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTimKiemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTimKiemActionPerformed

    private void btnThemThongTinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemThongTinActionPerformed
        StringBuilder sb = new StringBuilder();
        if (txtMaKhachHang.getText().equals("")) {
            sb.append("Mã khách hàng không được để trống\n");
        }
        if (txtMaPhong.getText().equals("")) {
            sb.append("Mã phòng không được để trống\n");
        }
        if (sb.length() > 0) {
            JOptionPane.showMessageDialog(rootPane,
                    sb.toString(), "Backup problem", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (kiemTraMaKhachHang() == false) {
            JOptionPane.showMessageDialog(rootPane,
                    "Thông tin Khách hàng không tồn tại!!! Hãy nhập thông tin khách hàng ở bên thông tin khách hàng", "Backup problem", JOptionPane.WARNING_MESSAGE);
            txtMaKhachHang.requestFocus();
            return;
        }
        if (kiemTraMaPhong() == false) {
            JOptionPane.showMessageDialog(rootPane,
                    "Thông tin Phòng không tồn tại!!! Mời nhập lại", "Backup problem", JOptionPane.WARNING_MESSAGE);
            txtMaPhong.requestFocus();
            return;
        }
        if (kiemTraTrangThai() == false) {
            JOptionPane.showMessageDialog(rootPane,
                    "Phòng đã được đặt Mời nhập lại", "Backup problem", JOptionPane.WARNING_MESSAGE);
            txtMaPhong.requestFocus();
            return;
        }

        int max = 0;
        for (DatPhong item : listDatPhong) {
            if (Integer.parseInt(item.getMaDatPhong().substring(2)) > max) {
                max = Integer.parseInt(item.getMaDatPhong().substring(2));
                id = max + 1;
            }
        }
        String MaDatPhong = "DP" + id++;
        String MaPhong = txtMaPhong.getText();
        chuyenTrangThai(listPhong);
        String maKhachHang = txtMaKhachHang.getText();
        Date NgayDat = txtNgayDat.getDate();
        String ngayDat = df.format(NgayDat);
        Date NgayTra = txtNgayTra.getDate();
        String ngayTra = df.format(NgayTra);
        //////////lam tien
        long a = NgayTra.getTime() - NgayDat.getTime();
        double phut = a / (60 * 1000) % 60;
        double gio = a / (60 * 60 * 1000);
        double thoiGian = phut / 60 + gio;

        double giaPhong = 0;
        for (Phong item : listPhong) {
            if (item.getMaPhong().equals(txtMaPhong.getText())) {
                giaPhong = item.getGiaPhong();
                System.out.println(giaPhong);
            }
        }
//        System.out.println(giaPhong);
        double TongTIen = tinhTongTien(thoiGian, giaPhong);
        ////////////////////////
        DatPhong datPhong = new DatPhong(MaDatPhong, MaPhong, maKhachHang, NgayDat, NgayTra, TongTIen);
        listDatPhong.add(datPhong);
        tableModel.addRow(new Object[]{MaDatPhong, MaPhong, maKhachHang, ngayDat, ngayTra, TongTIen});
        fDP.delete();
        dc.ghiFileDatPhong(listDatPhong);
        resetForm();
    }//GEN-LAST:event_btnThemThongTinActionPerformed

    private void btnChinhSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChinhSuaActionPerformed
        int choice = JOptionPane.showConfirmDialog(rootPane, "Bạn có muốn chỉnh sửa thông tin không?", "Hỏi", JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.NO_OPTION) {
            return;
        }
        int selectedRow = tblDatPhong.getSelectedRow();
        if (selectedRow >= 0) {
            String maDatPhong = (String) tblDatPhong.getValueAt(selectedRow, 0);
            for (DatPhong item : listDatPhong) {
                if (maDatPhong.equals(item.getMaDatPhong())) {
                    item.setMaPhong(txtMaPhong.getText());
                    item.setMaKhachHang(txtMaKhachHang.getText());
                    item.setNgayDat(txtNgayDat.getDate());
                    item.setNgayTra(txtNgayTra.getDate());
                    break;
                }

            }

            fDP.delete();
            dc.ghiFileDatPhong(listDatPhong);
            hienThiDatPhong(listDatPhong);
            resetForm();
        }
    }//GEN-LAST:event_btnChinhSuaActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        int choice = JOptionPane.showConfirmDialog(rootPane, "Bạn có muốn xóa  thông tin phòng  không?", "Hỏi", JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.NO_OPTION) {
            return;
        }
        int selectedRow = tblDatPhong.getSelectedRow();
        if (selectedRow >= 0) {
            String maDatPhong = (String) tblDatPhong.getValueAt(selectedRow, 0);
            for (DatPhong item : listDatPhong) {
                if (maDatPhong.equals(item.getMaDatPhong())) {
                    listDatPhong.remove(item);
                    break;
                }
            }
            String maPhong = (String) tblDatPhong.getValueAt(selectedRow, 1);
            for (Phong item : listPhong) {
                if (item.getMaPhong().equals(maPhong)) {
                    item.setTrangThai("Con Trong");
                    break;
                }
            }
            fP.delete();
            JOptionPane.showMessageDialog(rootPane, "Xóa thành công");
            dc.ghiFilePhong(listPhong);
            fDP.delete();
            dc.ghiFileDatPhong(listDatPhong);
            hienThiDatPhong(listDatPhong);
            resetForm();
        }
    }//GEN-LAST:event_btnXoaActionPerformed

    private void btnTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimKiemActionPerformed
        String textFinding = txtTimKiem.getText();
        tableModel.setRowCount(0);
        boolean flagFinding = false;
        for (DatPhong item : listDatPhong) {
            if (item.getMaDatPhong().equals(textFinding)) {
                String maDatPhong = item.getMaDatPhong();
                String maPhong = item.getMaPhong();
                String maKhachHang = item.getMaKhachHang();
                Date ngayDat = item.getNgayDat();
                Date ngayTra = item.getNgayTra();
                double thanhToan = item.getTongTien();
                flagFinding = true;

                tableModel.addRow(new Object[]{maDatPhong, maPhong, maKhachHang, ngayDat, ngayTra, thanhToan});
            } else if (item.getMaPhong().equals(textFinding)) {
                String maDatPhong = item.getMaDatPhong();
                String maPhong = item.getMaPhong();
                String maKhachHang = item.getMaKhachHang();
                Date ngayDat = item.getNgayDat();
                Date ngayTra = item.getNgayTra();
                double thanhToan = item.getTongTien();
                flagFinding = true;
                tableModel.addRow(new Object[]{maDatPhong, maPhong, maKhachHang, ngayDat, ngayTra, thanhToan});
            } else if (item.getMaKhachHang().equals(textFinding)) {
                String maDatPhong = item.getMaDatPhong();
                String maPhong = item.getMaPhong();
                String maKhachHang = item.getMaKhachHang();
                Date ngayDat = item.getNgayDat();
                Date ngayTra = item.getNgayTra();
                double thanhToan = item.getTongTien();
                flagFinding = true;
                tableModel.addRow(new Object[]{maDatPhong, maPhong, maKhachHang, ngayDat, ngayTra, thanhToan});
            }
        }
        if (flagFinding == false) {
            JOptionPane.showMessageDialog(rootPane,
                    "Thông tin dạt phòng không tồn tại", "Backup problem", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_btnTimKiemActionPerformed

    private void btnSapXepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSapXepActionPerformed
        if (boxSapXep.getSelectedItem().equals("Ma Phong")) {
            Collections.sort(listDatPhong, new Comparator<DatPhong>() {
                @Override
                public int compare(DatPhong o1, DatPhong o2) {
                    int item1 = Integer.parseInt(o1.getMaPhong().substring(2));
                    int item2 = Integer.parseInt(o2.getMaPhong().substring(2));
                    return item1 > item2 ? 1 : -1;
                }

            });
        } else if (boxSapXep.getSelectedItem().equals("Ma KH")) {
            Collections.sort(listDatPhong, new Comparator<DatPhong>() {
                @Override
                public int compare(DatPhong o1, DatPhong o2) {
                    int item1 = Integer.parseInt(o1.getMaKhachHang().substring(2));
                    int item2 = Integer.parseInt(o2.getMaKhachHang().substring(2));
                    return item1 > item2 ? 1 : -1;
                }
            });
        } else if (boxSapXep.getSelectedItem().equals("Tong Tien")) {
            Collections.sort(listDatPhong, new Comparator<DatPhong>() {
                @Override
                public int compare(DatPhong o1, DatPhong o2) {
                    return o1.getTongTien() > o2.getTongTien() ? 1 : -1;
                }
            });
        } else if (boxSapXep.getSelectedItem().equals("Ma Dat Phong")) {
            Collections.sort(listDatPhong, new Comparator<DatPhong>() {
                @Override
                public int compare(DatPhong o1, DatPhong o2) {
                    int item1 = Integer.parseInt(o1.getMaDatPhong().substring(2));
                    int item2 = Integer.parseInt(o2.getMaDatPhong().substring(2));
                    return item1 > item2 ? 1 : -1;
                }
            });
        }
        hienThiDatPhong(listDatPhong);
    }//GEN-LAST:event_btnSapXepActionPerformed

    private void tblDatPhongMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDatPhongMouseClicked
        int selectedRow = tblDatPhong.getSelectedRow();
        if (selectedRow >= 0) {
            String maDatPhong = (String) tblDatPhong.getValueAt(selectedRow, 0);
            for (DatPhong item : listDatPhong) {
                if (item.getMaDatPhong().equals(maDatPhong)) {
                    txtMaKhachHang.setText(item.getMaKhachHang());
                    txtMaPhong.setText(item.getMaPhong());
                    txtNgayDat.setDate(item.getNgayDat());
                    txtNgayTra.setDate(item.getNgayTra());
                    break;
                }
            }
        }
    }//GEN-LAST:event_tblDatPhongMouseClicked

    private void btbPhongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btbPhongActionPerformed
        NhapThongTinPhong.main();
    }//GEN-LAST:event_btbPhongActionPerformed

    private void btnKhachHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKhachHangActionPerformed
        NhapThongTinKhachHang.main();

    }//GEN-LAST:event_btnKhachHangActionPerformed

    private void btnThanhToanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThanhToanActionPerformed
        int selectedRow = tblDatPhong.getSelectedRow();
        if (selectedRow >= 0) {
            String maDatPhong = (String) tblDatPhong.getValueAt(selectedRow, 0);
            String maPhong = (String) tblDatPhong.getValueAt(selectedRow, 1);
            for (DatPhong item : listDatPhong) {
                if (maDatPhong.equals(item.getMaDatPhong())) {
                    listDatPhong.remove(item);
                    JOptionPane.showConfirmDialog(null, "Đã thanh toán thành công\nXin cảm ơn", "Thông báo", JOptionPane.CLOSED_OPTION);
                    for (Phong item1 : listPhong) {
                        if (item1.getMaPhong().equals(maPhong)) {
                            item1.setTrangThai("Con Trong");
                        }
                    }
                    break;
                }
            }
            fP.delete();
            dc.ghiFilePhong(listPhong);
            fDP.delete();
            dc.ghiFileDatPhong(listDatPhong);
            hienThiDatPhong(listDatPhong);
        }

    }//GEN-LAST:event_btnThanhToanActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main() {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(DatPhongView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DatPhongView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DatPhongView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DatPhongView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DatPhongView().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> boxSapXep;
    private javax.swing.JButton btbPhong;
    private javax.swing.JButton btnChinhSua;
    private javax.swing.JButton btnKhachHang;
    private javax.swing.JButton btnSapXep;
    private javax.swing.JButton btnThanhToan;
    private javax.swing.JButton btnThemThongTin;
    private javax.swing.JButton btnTimKiem;
    private javax.swing.JButton btnXoa;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblDatPhong;
    private javax.swing.JTextField txtMaKhachHang;
    private javax.swing.JTextField txtMaPhong;
    private com.toedter.calendar.JDateChooser txtNgayDat;
    private com.toedter.calendar.JDateChooser txtNgayTra;
    private javax.swing.JTextField txtTimKiem;
    // End of variables declaration//GEN-END:variables
}
