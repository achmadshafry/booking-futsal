package com.Galang.Galang10621.Model;

import com.google.firebase.database.Exclude;

public class Jual {
    private String noFak, tglJual, kode, jmlJual, hargaJual, totalJual, key;
    private int position;

    public Jual() {
    }

    public Jual(int position) {
        this.position = position;
    }

    public Jual(String noFak, String tglJual, String kode, String jmlJual, String hargaJual, String totalJual) {
        this.noFak = noFak;
        this.tglJual = tglJual;
        this.kode = kode;
        this.jmlJual = jmlJual;
        this.hargaJual = hargaJual;
        this.totalJual = totalJual;
    }

    public String getKode() {
        return kode;
    }

    public void setKode(String kode) {
        this.kode = kode;
    }

    public String getTglJual() {
        return tglJual;
    }

    public void setTglJual(String tglJual) {
        this.tglJual = tglJual;
    }

    public String getNoFak() {
        return noFak;
    }

    public void setNoFak(String noFak) {
        this.noFak = noFak;
    }

    public String getJmlJual() {
        return jmlJual;
    }

    public void setJmlJual(String jmlJual) {
        this.jmlJual = jmlJual;
    }

    public String getHargaJual() {
        return hargaJual;
    }

    public void setHargaJual(String hargaJual) {
        this.hargaJual = hargaJual;
    }

    public String getTotalJual() {
        return totalJual;
    }

    public void setTotalJual(String totalJual) {
        this.totalJual = totalJual;
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
