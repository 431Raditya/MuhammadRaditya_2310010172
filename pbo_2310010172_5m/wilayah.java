/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pbo_2310010172;

import java.sql.DriverManager;
import java.sql.Driver;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author HP
 */
public class wilayah {
    
    private String database = "pbo_2310010172";
    private String username = "root";
    private String password = "";
    private String url = "jdbc:mysql://localhost/"+database;
    private Connection koneksiDB;
    
    public String var_penyimpanan, var_pengumpulan, var_revitalisasi = null;
    public boolean validasi = false;
    
    public wilayah (){
        try {
            Driver driverkoneksi = new com.mysql.cj.jdbc.Driver();
            DriverManager.registerDriver(driverkoneksi);
            koneksiDB = DriverManager.getConnection(url,username,password);
            System.out.println("Berhasil koneksi");
        } catch (Exception e){
            JOptionPane.showMessageDialog(null, " Terjadi error:\n"+e.getMessage());
        }
    }
    
    public void loadData(JTable tabel, String sql){
        try {
                Statement perintah=koneksiDB.createStatement();
                ResultSet ds=perintah.executeQuery(sql);
                ResultSetMetaData data = ds.getMetaData();
                int kolom = data.getColumnCount();
                DefaultTableModel model=new DefaultTableModel();
                
//                for(int i = 1; i < kolom; i++){
//                    model.addColumn(data.getColumnName(i));
//                }
                model.addColumn("Wilayah"); 
                model.addColumn("penyimpanan");
                model.addColumn("pengumpulan");
                model.addColumn("revitalisasi");
                model.getDataVector().clear();
                model.fireTableDataChanged();
                while (ds.next()){
                    Object[] baris = new Object[kolom];
                    for(int j = 1; j<= kolom; j++){
                        baris[j-1] = ds.getObject(j);
                    
                    }
                    model.addRow(baris);
                    tabel.setModel(model);
                }
            } catch (Exception e) {
            }
    }
    
    public void simpanAnggotaSTS(String id, String nm, String sts, String telp){
        try {
           String cekPrimary = "SELECT * FROM  wilayah WHERE id_wilayah='"+id+"'";
           Statement cekData = koneksiDB.createStatement(); 
           ResultSet data = cekData.executeQuery(cekPrimary);
           if (data.next()){
               
               String isi = "\nwilayah penyimpanan : "+data.getString("wilayah penyimpanan")+
                       "\ntanggal pengumpulan :"+data.getString("tanggal pengumpulan")+
                       "\ntanggal revitalisasi :"+data.getString("tanggal revitalisasi");
               JOptionPane.showMessageDialog(null, "ID Wilayah sudah terdaftar"+isi);
               
               this.validasi = true;
               this.var_penyimpanan = data.getString("penyimpanan");
               this.var_pengumpulan = data.getString("pengumpulan");
               this.var_revitalisasi = data.getString("revitalisasi");
           }
           else{
               String sql = "INSERT INTO wilayah VALUES"
                   + "('"+id+"', '"+nm+"', '"+sts+"', '"+telp+"')";
           
           Statement perintah = koneksiDB.createStatement();
           perintah.execute(sql);
           JOptionPane.showMessageDialog(null, "berhasil disimpan");
           
               this.validasi = false;
               this.var_penyimpanan = null;
               this.var_pengumpulan = null;
               this.var_revitalisasi = null;
           }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        
    }
    
    public void simpanwilayahPST(String id, String nm, String sts, String telp){
        try {
           String sql = "INSERT INTO wilayah (ID_Wilayah, wilayah penyimpanan, tanggal pengumpulan, tanggal revitalisasi) value(?, ?, ?, ?)";
           String cekPrimary = "SELECT * FROM  wilayah WHERE id_wilayah='"+id+"'";
           Statement cekData = koneksiDB.createStatement(); 
           ResultSet data = cekData.executeQuery(cekPrimary);
           if (data.next()){
               
               String isi = "\nwilayah penyimpanan : "+data.getString("wilayah penyimpanan")+
                       "\ntanggal pengumpulan :"+data.getString("tanggal pengumpulan")+
                       "\ntanggal revitalisasi :"+data.getString("tanggal revitalisasi");
               JOptionPane.showMessageDialog(null, "ID wilayah sudah terdaftar"+isi);
               
               this.validasi = true;
               this.var_penyimpanan = data.getString("penyimpanan");
               this.var_pengumpulan = data.getString("pengumpulan");
               this.var_revitalisasi = data.getString("revitalisasi");
           }
           else {
           PreparedStatement perintah = koneksiDB.prepareStatement(sql);
           perintah.setString(1, id);
           perintah.setString(2, nm);
           perintah.setString(3, sts);
           perintah.setString(4, telp);
           perintah.executeUpdate();
           JOptionPane.showMessageDialog(null, "Berhasil disimpan");
           
                this.validasi = true;
                this.var_penyimpanan = nm;
                this.var_pengumpulan = sts;
                this.var_revitalisasi = telp;
           }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e.getMessage());
            System.out.print(e.getMessage());
        }
        
    }
    
    public void ubahwilayahSTS(String id, String nm, String sts, String telp){
        try {
            String sqlUbah = "UPDATE wilayah SET penyimpanan='" + nm + "', pengumpulan='" + sts + "', revitalisasi='" + telp + "' WHERE id_wilayah='" + id + "'";
            Statement ubah = koneksiDB.createStatement();
            ubah.execute(sqlUbah);
            JOptionPane.showMessageDialog(null, "Data berhasil diubah!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
    public void ubahwilayahPST(String id, String nm, String sts, String telp){
        try {
            String sqlUbah = "UPDATE wilayah SET penyimpanan=?, pengumpulan=?, revitalisasi=? WHERE id_wilayah=?";
            PreparedStatement ubah = koneksiDB.prepareStatement(sqlUbah);
            ubah.setString(1, nm);
            ubah.setString(2, sts);
            ubah.setString(3, telp);
            ubah.setString(4, id);
            ubah.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data berhasil diubah!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
    public void hapuswilayahSTS(String id) {
        try {
            String sqlHapus = "DELETE FROM wilayah WHERE id_wilayah='" + id + "'";
            Statement hapus = koneksiDB.createStatement();
            hapus.execute(sqlHapus);
            JOptionPane.showMessageDialog(null, "Data berhasil dihapus!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
    public void hapuswilayahPST(String id) {
        try {
            String sqlHapus = "DELETE FROM wilayah WHERE id_wilayah=?";
            PreparedStatement hapus = koneksiDB.prepareStatement(sqlHapus);
            hapus.setString(1, id);
            hapus.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data berhasil dihapus!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
}
