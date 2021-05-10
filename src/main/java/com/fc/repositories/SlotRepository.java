package com.fc.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fc.domain.Slot;

@Repository
public interface SlotRepository extends JpaRepository<Slot, Long>{
	public List<Slot> findBySalaIdAndUsuarioId(Long salaId, Long usuarioId);
	public List<Slot> findBySalaIdAndActividadId(Long salaId, Long actividadId);
}