package com.example.pruebasautomatizadasspring.controllers;

import com.example.pruebasautomatizadasspring.PruebasAutomatizadasSpringApplication;
import com.example.pruebasautomatizadasspring.models.Producto;
import com.example.pruebasautomatizadasspring.services.ProductoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ProductoController.class)
@Import({PruebasAutomatizadasSpringApplication.class, ProductoControllerTest.TestConfig.class})
class ProductoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductoService productoService; // Inyectado desde TestConfig

    @Configuration
    static class TestConfig {
        @Bean
        public ProductoService productoService() {
            // Creamos el mock de ProductoService
            return Mockito.mock(ProductoService.class);
        }
    }

    @Test
    void testListarProductos() throws Exception {
        when(productoService.listarProductos()).thenReturn(List.of(new Producto(1L, "Monitor", 300.0)));

        mockMvc.perform(get("/api/productos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("Monitor"));
    }

    @Test
    void testObtenerProductoPorId() throws Exception {
        when(productoService.obtenerProductoPorId(anyLong())).thenReturn(Optional.of(new Producto(1L, "Monitor", 300.0)));

        mockMvc.perform(get("/api/productos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Monitor"));
    }
}
