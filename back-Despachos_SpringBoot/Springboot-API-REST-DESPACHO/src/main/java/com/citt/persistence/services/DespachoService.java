package com.citt.persistence.services;

import com.citt.exceptions.DespachoNotFoundException;
import com.citt.persistence.entity.Despacho;

import java.util.List;

public interface DespachoService {

    // Obtener todos los despachos
    List<Despacho> findAllDespachos();

    // Guardar un nuevo despacho
    Despacho saveDespacho(Despacho despacho);

    // Actualizar un despacho existente
    Despacho updateDespacho(Long idDespacho, Despacho despacho)
            throws DespachoNotFoundException;

    // Eliminar un despacho
    void deleteDespacho(Long idDespacho)
            throws DespachoNotFoundException;

    // Buscar un despacho por ID
    Despacho findById(Long idDespacho)
            throws DespachoNotFoundException;
}