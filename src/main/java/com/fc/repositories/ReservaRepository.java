package com.fc.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fc.domain.Reserva;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long>{
    public List<Reserva> findBySlotIdAndUsuarioId(Long slotId,Long usuarioId);
}