package com.Galang.Galang10621.Model;

import com.google.firebase.database.Exclude;

public class Obat {

    private String kode, nama, satuan, jumlah, expired, harga, gambar, key;
    private int position;

    public Obat() {

    }

    public Obat(int position) {
        this.position = position;
    }

    public Obat(String kode, String nama, String satuan, String jumlah, String expired, String harga, String gambar) {
        this.kode = kode;
        this.nama = nama;
        this.satuan = satuan;
        this.jumlah = jumlah;
        this.expired = expired;
        this.harga = harga;
        this.gambar = gambar;
    }

    public String getKode() {
        return kode;
    }

    public void setKode(String kode) {
        this.kode = kode;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getSatuan() {
        return satuan;
    }

    public void setSatuan(String satuan) {
        this.satuan = satuan;
    }

    public String getJumlah() {
        return jumlah;
    }

    public void setJumlah(String jumlah) {
        this.jumlah = jumlah;
    }

    public String getExpired() {
        return expired;
    }

    public void setExpired(String expired) {
        this.expired = expired;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }

    @Exclude
    public String getKey() {
        return key;
    }

    @Exclude
    public void setKey(String key) {
        this.key = key;
    }
}
