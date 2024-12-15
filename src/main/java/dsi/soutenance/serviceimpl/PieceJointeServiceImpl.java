package dsi.soutenance.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dsi.soutenance.dto.PieceJointeDto;
import dsi.soutenance.model.PieceJointe;
import dsi.soutenance.repositorie.PieceJointeRepository;
import dsi.soutenance.service.PieceJointeService;

@Service
public class PieceJointeServiceImpl implements PieceJointeService {
   
     @Autowired
    private PieceJointeRepository pieceJointeRepository;

    @Override
    public Long save(PieceJointeDto pieceJointeDto) {
        PieceJointe pieceJointe = PieceJointeDto.toEntity(pieceJointeDto);
        return pieceJointeRepository.save(pieceJointe).getId();
    }

    @Override
    public void saveAll(List<PieceJointeDto> pieceJointeDtos) {
        pieceJointeDtos.forEach(
                pieceJointeDto -> {
                    PieceJointe pieceJointe = PieceJointeDto.toEntity(pieceJointeDto);
                    pieceJointeRepository.save(pieceJointe);
                }
        );
    }

    @Override
    public List<PieceJointeDto> getAll() {
        return pieceJointeRepository.findAll().stream().map(PieceJointeDto::fromEntity).toList();
    }


    @Override
    public PieceJointeDto getById(Long id) {
        return pieceJointeRepository.findById(id).map(PieceJointeDto::fromEntity).orElse(null);
    }

    @Override
    public void deleteById(Long id) {
        pieceJointeRepository.deleteById(id);
    }

    @Override
    public void deleteAll(List<PieceJointeDto> pieceJointeDtos) {
        pieceJointeDtos.forEach(pieceJointeDto -> {
            PieceJointe pieceJointe = PieceJointeDto.toEntity(pieceJointeDto);
            pieceJointeRepository.delete(pieceJointe);
        });
    }

}
