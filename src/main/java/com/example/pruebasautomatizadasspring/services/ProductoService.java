package com.example.pruebasautomatizadasspring.services;

import com.example.pruebasautomatizadasspring.models.Producto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {
    private final List<Producto> productos = new ArrayList<>();
    private Long idCounter = 1L;

    // Constructor que semilla algunos productos
    public ProductoService() {
        // Agregar productos de ejemplo
        productos.add(new Producto(idCounter++, "Monitor", 300.0));
        productos.add(new Producto(idCounter++, "Teclado", 50.0));
    }

    public List<Producto> listarProductos() {
        return productos;
    }

    public Producto agregarProducto(String nombre, double precio) {
        Producto producto = new Producto(idCounter++, nombre, precio);
        productos.add(producto);
        return producto;
    }

    public Optional<Producto> obtenerProductoPorId(Long id) {
        return productos.stream().filter(p -> p.getId().equals(id)).findFirst();
    }

}
