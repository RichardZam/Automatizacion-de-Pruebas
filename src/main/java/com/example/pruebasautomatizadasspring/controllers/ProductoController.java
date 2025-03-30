package com.example.pruebasautomatizadasspring.controllers;

import com.example.pruebasautomatizadasspring.models.Producto;
import com.example.pruebasautomatizadasspring.services.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController


@RequestMapping("/api/productos")
public class ProductoController {

    private final ProductoService productoService;

    @Autowired
    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping
    public Flux<Producto> listarProductos() {
        return productoService.findAll();
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Producto>> obtenerProductoPorId(@PathVariable String id) {
        return productoService.findById(id)
                .map(producto -> ResponseEntity.ok(producto))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Producto> crearProducto(@RequestBody Producto producto) {
        return productoService.save(producto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> eliminarProducto(@PathVariable String id) {
        return productoService.deleteById(id);
    }
}