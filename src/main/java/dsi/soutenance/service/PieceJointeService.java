package dsi.soutenance.service;

import dsi.soutenance.dto.PieceJointeDto;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface PieceJointeService extends AbstractService<PieceJointeDto>{
    List<PieceJointeDto> savePiecesJointes(List<PieceJointeDto> pieceJointeDtos, long demandeId);
}

