package com.example.apotek23.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Pembelian {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "obat_id", nullable = true)
    private Obat obat;

    @Column(name = "nama_obat") // Pastikan ada
    private String namaObat;

    private int jumlah;
    private LocalDateTime tanggal;

    public Pembelian() {
        this.tanggal = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public Obat getObat() {
        return obat;
    }

    public void setObat(Obat obat) {
        this.obat = obat;
    }

    public String getNamaObat() {
        return namaObat;
    }

    public void setNamaObat(String namaObat) {
        this.namaObat = namaObat;
    }

    public int getJumlah() {
        return jumlah;
    }

    public void setJumlah(int jumlah) {
        this.jumlah = jumlah;
    }

    public LocalDateTime getTanggal() {
        return tanggal;
    }

    public void setTanggal(LocalDateTime tanggal) {
        this.tanggal = tanggal;
    }
}
