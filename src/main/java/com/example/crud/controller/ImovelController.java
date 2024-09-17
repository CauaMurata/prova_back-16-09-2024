package com.example.crud.controller;

import com.example.crud.domain.Imovel;
import com.example.crud.dtos.ImovelDto;
import com.example.crud.service.ImovelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/imovel")
public class ImovelController {

    @Autowired
    ImovelService imovelService;

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping
    public ResponseEntity<Imovel> create(@RequestBody ImovelDto imovelDTO) {
        Imovel newImovel = imovelService.createImovel(imovelDTO);
        return ResponseEntity.ok().body(newImovel);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PutMapping("/{id}")
    public ResponseEntity<Imovel> update(@PathVariable Long id, @RequestBody ImovelDto imovelDTO) {
        Imovel updateImovel = imovelService.updateImovel(id, imovelDTO);
        return ResponseEntity.ok().body(updateImovel);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping
    public ResponseEntity<List<Imovel>> findAll() {
        List<Imovel> imoveis = imovelService.findAll();
        return ResponseEntity.ok().body(imoveis);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("/{id}")
    public ResponseEntity<Imovel> findById(@PathVariable Long id) {
        Imovel imovel = imovelService.findById(id);
        return ResponseEntity.ok().body(imovel);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        imovelService.deleteImovel(id);
        return ResponseEntity.noContent().build();
    }
}
