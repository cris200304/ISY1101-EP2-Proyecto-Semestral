package com.citt.controller;

import com.citt.exceptions.DespachoNotFoundException;
import com.citt.persistence.entity.Despacho;
import com.citt.persistence.services.DespachoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("api/v1/despachos")
@Tag(name = "Despacho", description = "Controlador para gestionar despachos")
public class DespachoController {

    @Autowired
    private DespachoService despachoService;

    @Operation(
            summary = "Crear un nuevo despacho",
            description = "Crea un nuevo despacho en el sistema"
    )
    @PostMapping
    public ResponseEntity<Despacho> crearDespacho(
            @Valid @RequestBody Despacho despacho) {

        // Evitar que llegue un ID desde el frontend
        despacho.setIdDespacho(null);

        // Guardar primero en la base de datos
        Despacho nuevoDespacho = despachoService.saveDespacho(despacho);

        // Crear la URI usando el ID generado por la BD
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{idDespacho}")
                .buildAndExpand(nuevoDespacho.getIdDespacho())
                .toUri();

        return ResponseEntity.created(location).body(nuevoDespacho);
    }

    @Operation(
            summary = "Actualizar un despacho existente",
            description = "Actualiza la información de un despacho"
    )
    @PutMapping("/{idDespacho}")
    public ResponseEntity<Despacho> actualizarDespacho(
            @PathVariable Long idDespacho,
            @Valid @RequestBody Despacho despacho)
            throws DespachoNotFoundException {

        Despacho despachoActualizado =
                despachoService.updateDespacho(idDespacho, despacho);

        return ResponseEntity.ok(despachoActualizado);
    }

    @Operation(
            summary = "Obtener todos los despachos",
            description = "Devuelve una lista de todos los despachos registrados"
    )
    @GetMapping
    public ResponseEntity<List<Despacho>> getAllDespachos() {
        return ResponseEntity.ok(despachoService.findAllDespachos());
    }

    @Operation(
            summary = "Obtener un despacho por ID",
            description = "Devuelve un despacho específico según su ID"
    )
    @GetMapping("/{idDespacho}")
    public ResponseEntity<Despacho> obtenerDespacho(
            @PathVariable Long idDespacho)
            throws DespachoNotFoundException {

        return ResponseEntity.ok(
                despachoService.findById(idDespacho)
        );
    }

    @Operation(
            summary = "Eliminar un despacho",
            description = "Elimina un despacho según su ID"
    )
    @DeleteMapping("/{idDespacho}")
    public ResponseEntity<Void> eliminarDespacho(
            @PathVariable Long idDespacho)
            throws DespachoNotFoundException {

        despachoService.deleteDespacho(idDespacho);

        return ResponseEntity.noContent().build();
    }
}