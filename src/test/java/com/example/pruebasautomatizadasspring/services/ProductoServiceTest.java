package com.example.pruebasautomatizadasspring.services;

import com.example.pruebasautomatizadasspring.models.Producto;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ProductoServiceTest {
    private final ProductoService productoService = new ProductoService();

    @Test
    void testAgregarProducto() {
        Producto producto = productoService.agregarProducto("Laptop", 1500.0);
        assertNotNull(producto.getId());
        assertEquals("Laptop", producto.getNombre());
        assertEquals(1500.0, producto.getPrecio());
    }

    @Test
    void testListarProductos() {
        productoService.agregarProducto("Mouse", 50.0);
        List<Producto> productos = productoService.listarProductos();
        assertFalse(productos.isEmpty());
    }

    @Test
    void testObtenerProductoPorId() {
        Producto producto = productoService.agregarProducto("Teclado", 100.0);
        Optional<Producto> encontrado = productoService.obtenerProductoPorId(producto.getId());
        assertTrue(encontrado.isPresent());
    }
}
