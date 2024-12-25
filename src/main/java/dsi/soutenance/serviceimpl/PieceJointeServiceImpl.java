package dsi.soutenance.serviceimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import dsi.soutenance.model.Demande;
import dsi.soutenance.repositorie.DemandeRepository;
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
    @Autowired
    private DemandeRepository demandeRepository;
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
//
    @Override
    public List<PieceJointeDto> savePiecesJointes(List<PieceJointeDto> pieceJointeDtos, long demandeId) {
        List<PieceJointe> savedPieces = pieceJointeDtos.stream()
                .map(pieceJointeDto -> {
                    PieceJointe pieceJointe = PieceJointeDto.toEntity(pieceJointeDto);
                    pieceJointe.setDemande(demandeRepository.findById(demandeId)
                            .orElseThrow(() -> new RuntimeException("Demande not found")));
                    return pieceJointeRepository.save(pieceJointe);
                })
                .collect(Collectors.toList());

        // Convertir et retourner les entités sauvegardées en DTOs
        return savedPieces.stream()
                .map(PieceJointeDto::fromEntity)
                .collect(Collectors.toList());
    }

//    @Override
//    public List<PieceJointeDto> savePiecesJointes(List<PieceJointeDto> pieceJointeDtos, long demandeId) {
//        Demande demande = demandeRepository.findById(demandeId)
//                .orElseThrow(() -> new RuntimeException("Demande not found"));
//
//        List<PieceJointe> pieceJointes = new ArrayList<>();
//
//        for (PieceJointeDto dto : pieceJointeDtos) {
//            PieceJointe pieceJointe = PieceJointeDto.toEntity(dto);
//            pieceJointe.setDemande(demande);
//            pieceJointes.add(pieceJointeRepository.save(pieceJointe));
//        }
//
//        return pieceJointes.stream()
//                .map(PieceJointeDto::fromEntity)
//                .collect(Collectors.toList());
//    }





}
