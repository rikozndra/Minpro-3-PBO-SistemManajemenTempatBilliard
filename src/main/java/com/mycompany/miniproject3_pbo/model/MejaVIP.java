/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.miniproject3_pbo.model;

/**
 *
 * @author user
 */
public class MejaVIP extends MejaBilliard {

    public MejaVIP(int nomorMeja) {
        super(nomorMeja, 60000);
    }

    @Override
    public String getJenisMeja() {
        return "VIP";
    }
}