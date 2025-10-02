package com.example.apotek23.controller;

import com.example.apotek23.model.Obat;
import com.example.apotek23.model.Pembelian;
import com.example.apotek23.repository.ObatRepository;
import com.example.apotek23.repository.PembelianRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes; 

import java.time.LocalDateTime;
import java.util.List;


@Controller
@RequestMapping("/pembelian")
public class PembelianController {

    private final PembelianRepository pembelianRepository;
    private final ObatRepository obatRepository;

    public PembelianController(PembelianRepository pembelianRepository, ObatRepository obatRepository) {
        this.pembelianRepository = pembelianRepository;
        this.obatRepository = obatRepository;
    }

    // 🔹 Menampilkan halaman riwayat pembelian
    @GetMapping
    public String listPembelian(Model model) {
        List<Pembelian> pembelianList = pembelianRepository.findAll();
        model.addAttribute("pembelianList", pembelianList);
        return "pembelian/list";
    }

    // 🔹 Menyimpan transaksi pembelian
    @PostMapping("/save")
public String savePembelian(@RequestParam("obat.id") Long obatId,
                            @RequestParam("jumlah") int jumlah,
                            RedirectAttributes redirectAttributes) {

    Obat obat = obatRepository.findById(obatId).orElse(null);
    if (obat == null) {
        redirectAttributes.addFlashAttribute("error", "Obat tidak ditemukan.");
        return "redirect:/obat";
    }

    if (jumlah <= 0) {
        redirectAttributes.addFlashAttribute("error", "Jumlah pembelian tidak valid.");
        return "redirect:/obat";
    }

    if (obat.getStok() < jumlah) {
        redirectAttributes.addFlashAttribute("error", "Stok obat tidak mencukupi.");
        return "redirect:/obat";
    }

    // Kurangi stok
    obat.setStok(obat.getStok() - jumlah);
    obatRepository.save(obat);

    // Simpan pembelian
    Pembelian pembelian = new Pembelian();
    pembelian.setObat(obat); // relasi tetap disimpan untuk histori, optional
    pembelian.setNamaObat(obat.getNamaObat()); // nama fix disalin
    pembelian.setJumlah(jumlah);
    pembelian.setTanggal(LocalDateTime.now());

    pembelianRepository.save(pembelian);

    return "redirect:/pembelian";

    }
}

