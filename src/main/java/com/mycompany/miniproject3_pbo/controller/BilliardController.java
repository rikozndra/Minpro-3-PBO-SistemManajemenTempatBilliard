/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.miniproject3_pbo.controller;

import com.mycompany.miniproject3_pbo.model.MejaBilliard;
import com.mycompany.miniproject3_pbo.model.MejaReguler;
import com.mycompany.miniproject3_pbo.model.MejaVIP;
import com.mycompany.miniproject3_pbo.model.Transaksi;
import java.util.ArrayList;

/**
 *
 * @author user
 */
public class BilliardController {

    private ArrayList<MejaBilliard> daftarMeja;
    private ArrayList<Transaksi> daftarTransaksi;

    private int idTransaksi = 1;

    public BilliardController() {

        daftarMeja = new ArrayList<>();
        daftarTransaksi = new ArrayList<>();

        // Dummy data awal
        daftarMeja.add(new MejaReguler(1));
    }

    // CREATE
    public boolean tambahMeja(int nomorMeja, String jenisMeja) {

        if (nomorMeja <= 0) {
            return false;
        }

        if (cariMeja(nomorMeja) != null) {
            return false;
        }

        MejaBilliard meja = buatMeja(
                nomorMeja,
                jenisMeja
        );

        if (meja == null) {
            return false;
        }

        daftarMeja.add(meja);

        return true;
    }

    // READ
    public void tampilkanMeja() {

        if (daftarMeja.isEmpty()) {
            System.out.println("Belum ada data meja.");
            return;
        }

        System.out.println("\n========== DATA MEJA ==========");

        for (MejaBilliard meja : daftarMeja) {

            System.out.println(
                    "Nomor Meja : "
                    + meja.getNomorMeja()
            );

            System.out.println(
                    "Jenis Meja : "
                    + meja.getJenisMeja()
            );

            System.out.println(
                    "Harga/Jam  : Rp"
                    + meja.getHargaPerJam()
            );

            System.out.println(
                    "Status     : "
                    + meja.getStatus()
            );

            System.out.println("-------------------------------");
        }
    }

    // UPDATE
    public boolean ubahMeja(
            int nomorMeja,
            String jenisBaru) {

        MejaBilliard mejaLama = cariMeja(nomorMeja);

        if (mejaLama == null) {
            return false;
        }

        if (!mejaLama.getStatus().equals("Tersedia")) {
            return false;
        }

        MejaBilliard mejaBaru = buatMeja(
                nomorMeja,
                jenisBaru
        );

        if (mejaBaru == null) {
            return false;
        }

        int index = daftarMeja.indexOf(mejaLama);

        daftarMeja.set(index, mejaBaru);

        return true;
    }

    // DELETE
    public boolean hapusMeja(int nomorMeja) {

        MejaBilliard meja = cariMeja(nomorMeja);

        if (meja == null) {
            return false;
        }

        if (!meja.getStatus().equals("Tersedia")) {
            return false;
        }

        daftarMeja.remove(meja);

        return true;
    }

    // CEK KETERSEDIAAN MEJA
    public boolean mejaBisaDibuka(int nomorMeja) {

        MejaBilliard meja = cariMeja(nomorMeja);

        if (meja == null) {
            return false;
        }

        return meja.dapatDisewa();
    }

    // BUKA MEJA
    public boolean bukaMeja(
            int nomorMeja,
            String namaPelanggan,
            int durasiJam) {

        MejaBilliard meja = cariMeja(nomorMeja);

        if (meja == null) {
            return false;
        }

        if (!meja.dapatDisewa()) {
            return false;
        }

        if (namaPelanggan == null
                || namaPelanggan.trim().isEmpty()) {

            return false;
        }

        if (durasiJam <= 0) {
            return false;
        }

        double totalBayar;

        // VIP mendapatkan diskon 10%
        if (meja.getJenisMeja()
                .equalsIgnoreCase("VIP")) {

            totalBayar = meja.hitungBiaya(
                    durasiJam,
                    10
            );

        } else {

            totalBayar = meja.hitungBiaya(
                    durasiJam
            );
        }

        Transaksi transaksi = new Transaksi(
                idTransaksi,
                nomorMeja,
                namaPelanggan,
                durasiJam,
                totalBayar
        );

        daftarTransaksi.add(transaksi);

        idTransaksi++;

        meja.setStatus("Digunakan");

        return true;
    }

    // TAMBAH WAKTU
    public boolean tambahWaktu(
            int nomorMeja,
            int tambahanJam) {

        if (tambahanJam <= 0) {
            return false;
        }

        MejaBilliard meja = cariMeja(nomorMeja);

        if (meja == null) {
            return false;
        }

        if (!meja.getStatus().equals("Digunakan")) {
            return false;
        }

        Transaksi transaksi =
                cariTransaksiAktif(nomorMeja);

        if (transaksi == null) {
            return false;
        }

        int durasiBaru =
                transaksi.getDurasiJam()
                + tambahanJam;

        double totalBaru;

        // Hitung ulang total sesuai jenis meja
        if (meja.getJenisMeja()
                .equalsIgnoreCase("VIP")) {

            totalBaru = meja.hitungBiaya(
                    durasiBaru,
                    10
            );

        } else {

            totalBaru = meja.hitungBiaya(
                    durasiBaru
            );
        }

        transaksi.setDurasiJam(durasiBaru);
        transaksi.setTotalBayar(totalBaru);

        return true;
    }

    // TUTUP MEJA
    public Transaksi tutupMeja(int nomorMeja) {

        MejaBilliard meja = cariMeja(nomorMeja);

        if (meja == null) {
            return null;
        }

        if (!meja.getStatus().equals("Digunakan")) {
            return null;
        }

        Transaksi transaksi =
                cariTransaksiAktif(nomorMeja);

        if (transaksi == null) {
            return null;
        }

        meja.setStatus("Tersedia");

        // Transaksi tetap disimpan sebagai riwayat
        transaksi.setStatusTransaksi("Selesai");

        return transaksi;
    }

    // RIWAYAT TRANSAKSI
    public void tampilkanRiwayatTransaksi() {

        if (daftarTransaksi.isEmpty()) {
            System.out.println(
                    "Belum ada riwayat transaksi."
            );
            return;
        }

        System.out.println(
                "\n======= RIWAYAT TRANSAKSI ======="
        );

        for (Transaksi transaksi : daftarTransaksi) {

            System.out.println(
                    "ID Transaksi : "
                    + transaksi.getIdTransaksi()
            );

            System.out.println(
                    "Nomor Meja   : "
                    + transaksi.getNomorMeja()
            );

            System.out.println(
                    "Pelanggan    : "
                    + transaksi.getNamaPelanggan()
            );

            System.out.println(
                    "Durasi       : "
                    + transaksi.getDurasiJam()
                    + " jam"
            );

            System.out.println(
                    "Total Bayar  : Rp"
                    + transaksi.getTotalBayar()
            );

            System.out.println(
                    "Status       : "
                    + transaksi.getStatusTransaksi()
            );

            System.out.println(
                    "--------------------------------"
            );
        }
    }

    // MEMBUAT OBJEK MEJA
    private MejaBilliard buatMeja(
            int nomorMeja,
            String jenisMeja) {

        if (jenisMeja == null) {
            return null;
        }

        if (jenisMeja.equalsIgnoreCase("Reguler")) {
            return new MejaReguler(nomorMeja);
        }

        if (jenisMeja.equalsIgnoreCase("VIP")) {
            return new MejaVIP(nomorMeja);
        }

        return null;
    }

    // MENCARI MEJA
    private MejaBilliard cariMeja(int nomorMeja) {

        for (MejaBilliard meja : daftarMeja) {

            if (meja.getNomorMeja() == nomorMeja) {
                return meja;
            }
        }

        return null;
    }

    // MENCARI TRANSAKSI AKTIF
    private Transaksi cariTransaksiAktif(
            int nomorMeja) {

        for (Transaksi transaksi : daftarTransaksi) {

            if (transaksi.getNomorMeja() == nomorMeja
                    && transaksi
                            .getStatusTransaksi()
                            .equals("Aktif")) {

                return transaksi;
            }
        }

        return null;
    }
}