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
public class Pemesanan {
    
    private String database = "pbo_2310010172";
    private String username = "root";
    private String password = "";
    private String url = "jdbc:mysql://localhost/"+database;
    private Connection koneksiDB;
    
    public String var_nama, var_status, var_telp = null;
    public boolean validasi = false;
    
    public Pemesanan (){
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
                model.addColumn("ID_Pemesanan"); 
                model.addColumn("Tanggal pemesanan");
                model.addColumn("ID_Konsumen");
                model.addColumn("ID_Karyawan");
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
           String cekPrimary = "SELECT * FROM  anggota WHERE idAnggota='"+id+"'";
           Statement cekData = koneksiDB.createStatement(); 
           ResultSet data = cekData.executeQuery(cekPrimary);
           if (data.next()){
               
               String isi = "\nNama : "+data.getString("nama")+
                       "\nStatus :"+data.getString("status")+
                       "\nTelp :"+data.getString("telp");
               JOptionPane.showMessageDialog(null, "ID Anggota sudah terdaftar"+isi);
               
               this.validasi = true;
               this.var_nama = data.getString("Nama");
               this.var_status = data.getString("status");
               this.var_telp = data.getString("telp");
           }
           else{
               String sql = "INSERT INTO anggota VALUES"
                   + "('"+id+"', '"+nm+"', '"+sts+"', '"+telp+"')";
           
           Statement perintah = koneksiDB.createStatement();
           perintah.execute(sql);
           JOptionPane.showMessageDialog(null, "berhasil disimpan");
           
               this.validasi = false;
               this.var_nama = null;
               this.var_status = null;
               this.var_telp = null;
           }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        
    }
    
    public void simpanAnggotaPST(String id, String nm, String sts, String telp){
        try {
           String sql = "INSERT INTO anggota (IDAnggota, Nama, status, telp) value(?, ?, ?, ?)";
           String cekPrimary = "SELECT * FROM  anggota WHERE idAnggota='"+id+"'";
           Statement cekData = koneksiDB.createStatement(); 
           ResultSet data = cekData.executeQuery(cekPrimary);
           if (data.next()){
               
               String isi = "\nNama : "+data.getString("nama")+
                       "\nStatus :"+data.getString("status")+
                       "\nTelp :"+data.getString("telp");
               JOptionPane.showMessageDialog(null, "ID Anggota sudah terdaftar"+isi);
               
               this.validasi = true;
               this.var_nama = data.getString("Nama");
               this.var_status = data.getString("status");
               this.var_telp = data.getString("telp");
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
                this.var_nama = nm;
                this.var_status = sts;
                this.var_telp = telp;
           }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e.getMessage());
            System.out.print(e.getMessage());
        }
        
    }
    
    public void ubahAnggotaSTS(String id, String nm, String sts, String telp){
        try {
            String sqlUbah = "UPDATE anggota SET nama='" + nm + "', status='" + sts + "', telp='" + telp + "' WHERE idAnggota='" + id + "'";
            Statement ubah = koneksiDB.createStatement();
            ubah.execute(sqlUbah);
            JOptionPane.showMessageDialog(null, "Data berhasil diubah!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
    public void ubahAnggotaPST(String id, String nm, String sts, String telp){
        try {
            String sqlUbah = "UPDATE anggota SET nama=?, status=?, telp=? WHERE idAnggota=?";
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
    
    public void hapusAnggotaSTS(String id) {
        try {
            String sqlHapus = "DELETE FROM anggota WHERE idAnggota='" + id + "'";
            Statement hapus = koneksiDB.createStatement();
            hapus.execute(sqlHapus);
            JOptionPane.showMessageDialog(null, "Data berhasil dihapus!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
    public void hapusAnggotaPST(String id) {
        try {
            String sqlHapus = "DELETE FROM anggota WHERE idAnggota=?";
            PreparedStatement hapus = koneksiDB.prepareStatement(sqlHapus);
            hapus.setString(1, id);
            hapus.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data berhasil dihapus!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
}
