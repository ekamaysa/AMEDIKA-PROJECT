package com.example.apotek23.controller;

import com.example.apotek23.model.Obat;
import com.example.apotek23.repository.ObatRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/obat")
public class ObatController {

    private final ObatRepository obatRepository;

    public ObatController(ObatRepository obatRepository) {
        this.obatRepository = obatRepository;
    }

    // Halaman daftar obat + pencarian
    @GetMapping
    public String listObat(@RequestParam(required = false) String keyword, Model model) {
        List<Obat> obatList;
        if (keyword != null && !keyword.isEmpty()) {
            obatList = obatRepository.findByNamaObatContainingIgnoreCase(keyword);
        } else {
            obatList = obatRepository.findAll();
        }
        model.addAttribute("obatList", obatList);
        model.addAttribute("keyword", keyword);
        return "obat/list";
    }

    // Tambah obat baru (form kosong)
    @GetMapping("/new")
    public String newObat(Model model) {
        model.addAttribute("obat", new Obat());
        return "obat/form";
    }

    // Simpan obat baru atau update
    @PostMapping("/save")
    public String saveObat(@ModelAttribute Obat obat) {
        obatRepository.save(obat);
        return "redirect:/obat";
    }

    // Edit obat (form diisi)
    @GetMapping("/edit/{id}")
    public String editObat(@PathVariable Long id, Model model) {
        Obat obat = obatRepository.findById(id).orElse(new Obat());
        model.addAttribute("obat", obat);
        return "obat/form";
    }

    // Hapus obat
    @GetMapping("/delete/{id}")
    public String deleteObat(@PathVariable Long id) {
        obatRepository.deleteById(id);
        return "redirect:/obat";
    }
}
