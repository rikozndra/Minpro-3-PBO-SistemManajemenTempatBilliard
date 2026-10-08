/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.miniproject3_pbo.model;

/**
 *
 * @author user
 */
public abstract class MejaBilliard implements DapatDisewa {

    private int nomorMeja;
    private double hargaPerJam;
    private String status;

    public MejaBilliard(int nomorMeja, double hargaPerJam) {
        this.nomorMeja = nomorMeja;
        this.hargaPerJam = hargaPerJam;
        this.status = "Tersedia";
    }

    public int getNomorMeja() {
        return nomorMeja;
    }

    public double getHargaPerJam() {
        return hargaPerJam;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // IMPLEMENTASI INTERFACE
    @Override
    public double getHargaSewa() {
        return hargaPerJam;
    }

    @Override
    public boolean dapatDisewa() {
        return status.equals("Tersedia");
    }

    // ABSTRACT METHOD
    public abstract String getJenisMeja();

    // OVERLOADING 1
    public double hitungBiaya(int durasiJam) {
        return hargaPerJam * durasiJam;
    }

    // OVERLOADING 2
    public double hitungBiaya(int durasiJam, double diskonPersen) {

        double totalAwal = hitungBiaya(durasiJam);

        if (diskonPersen < 0 || diskonPersen > 100) {
            return totalAwal;
        }

        double potongan =
                totalAwal * diskonPersen / 100;

        return totalAwal - potongan;
    }
}
