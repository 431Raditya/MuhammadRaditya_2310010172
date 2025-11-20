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
public class crud {
    
    private String database = "pbo_2310010172";
    private String username = "root";
    private String password = "";
    private String url = "jdbc:mysql://localhost/"+database;
    private Connection koneksiDB;
    
    public String var_jnsproduk, var_Ukuran, var_Harga = null;
    public boolean validasi = false;
    
    public crud (){
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
                model.addColumn("ID_Produk"); 
                model.addColumn("jnsproduk");
                model.addColumn("Ukuran");
                model.addColumn("Harga");
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
    
    public void simpanProdukSTS(String id, String nm, String sts, String telp){
        try {
           String cekPrimary = "SELECT * FROM  produk WHERE id_Produk='"+id+"'";
           Statement cekData = koneksiDB.createStatement(); 
           ResultSet data = cekData.executeQuery(cekPrimary);
           if (data.next()){
               
               String isi = "\njnsproduk : "+data.getString("jnsproduk")+
                       "\nUkuran :"+data.getString("Ukuran")+
                       "\nHarga :"+data.getString("Harga");
               JOptionPane.showMessageDialog(null, "ID_Produk sudah terdaftar"+isi);
               
               this.validasi = true;
               this.var_jnsproduk = data.getString("jnsProduk");
               this.var_Ukuran = data.getString("Ukuran");
               this.var_Harga = data.getString("Harga");
           }
           else{
               String sql = "INSERT INTO produk VALUES"
                   + "('"+id+"', '"+nm+"', '"+sts+"', '"+telp+"')";
           
           Statement perintah = koneksiDB.createStatement();
           perintah.execute(sql);
           JOptionPane.showMessageDialog(null, "berhasil disimpan");
           
               this.validasi = false;
               this.var_jnsproduk = null;
               this.var_Ukuran = null;
               this.var_Harga = null;
           }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        
    }
    
    public void simpanProdukPST(String id, String nm, String sts, String telp){
        try {
           String sql = "INSERT INTO produk (ID_Produk, jnsproduk, Ukuran, Harga) value(?, ?, ?, ?)";
           String cekPrimary = "SELECT * FROM  produk WHERE ID_Produk='"+id+"'";
           Statement cekData = koneksiDB.createStatement(); 
           ResultSet data = cekData.executeQuery(cekPrimary);
           if (data.next()){
               
               String isi = "\njnsproduk : "+data.getString("jnsproduk")+
                       "\nUkuran :"+data.getString("Ukuran")+
                       "\nHarga :"+data.getString("Harga");
               JOptionPane.showMessageDialog(null, "ID_Produk sudah terdaftar"+isi);
               
               this.validasi = true;
               this.var_jnsproduk = data.getString("jnsproduk");
               this.var_Ukuran = data.getString("Ukuran");
               this.var_Harga = data.getString("Harga");
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
                this.var_jnsproduk = nm;
                this.var_Ukuran = sts;
                this.var_Harga = telp;
           }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e.getMessage());
            System.out.print(e.getMessage());
        }
        
    }
    
    public void ubahProdukSTS(String id, String nm, String sts, String telp){
        try {
            String sqlUbah = "UPDATE produk SET jnsproduk='" + nm + "', Ukuran='" + sts + "', Harga='" + telp + "' WHERE idproduk='" + id + "'";
            Statement ubah = koneksiDB.createStatement();
            ubah.execute(sqlUbah);
            JOptionPane.showMessageDialog(null, "Data berhasil diubah!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
    public void ubahProdukPST(String id, String nm, String sts, String telp){
        try {
            String sqlUbah = "UPDATE produk SET nama=?, status=?, telp=? WHERE idproduk=?";
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
    
    public void hapusProdukSTS(String id) {
        try {
            String sqlHapus = "DELETE FROM produk WHERE id_Produk='" + id + "'";
            Statement hapus = koneksiDB.createStatement();
            hapus.execute(sqlHapus);
            JOptionPane.showMessageDialog(null, "Data berhasil dihapus!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
    public void hapusProdukPST(String id) {
        try {
            String sqlHapus = "DELETE FROM produk WHERE id_Produk=?";
            PreparedStatement hapus = koneksiDB.prepareStatement(sqlHapus);
            hapus.setString(1, id);
            hapus.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data berhasil dihapus!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
}
