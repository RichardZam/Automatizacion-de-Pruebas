package com.example.pruebasautomatizadasspring.controllers;

import com.example.pruebasautomatizadasspring.models.Producto;
import com.example.pruebasautomatizadasspring.services.ProductoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {
    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping
    public List<Producto> listarProductos() {
        return productoService.listarProductos();
    }

    @PostMapping
    public Producto agregarProducto(@RequestParam String nombre, @RequestParam double precio) {
        return productoService.agregarProducto(nombre, precio);
    }

    @GetMapping("/{id}")
    public Optional<Producto> obtenerProductoPorId(@PathVariable Long id) {
        return productoService.obtenerProductoPorId(id);
    }
}
