package dsi.soutenance.dto;

import dsi.soutenance.model.DemandeurMorale;
import dsi.soutenance.model.DemandeurPhysique;
import dsi.soutenance.model.PieceJointe;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import dsi.soutenance.model.Demande;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor // Ajoute un constructeur par défaut
@Builder
public class SoumissionDto {

    // Propriete demandeurMorale
    private Long id;
    private String ifu;
    private String nomResponsable;
    private String denomination;
    private String siege;
    private String mail;
    private String telephone1;
    private String telephone2;

    // Propriete demandeurPhysique

    private String nom;
    private String prenom;
    private String genre;
    private LocalDate dateNaisse;
    private String lieuResidence;
    private String typePiece;
    private String numPiece;
    private String telephoneP1;
    private String telephoneP2;
    private String mailP;

    // propiete de demande
    private String typeDemande;
    private String typeDemandeur;
    private String statut;
    private Long prix;
    private Long numeroDemande;
    private String codeDemande;
    private LocalDate dateDepot;
    private LocalDate dateValidation;
    private MultipartFile fichier;


    private String libelle;
    private String url;

    List<PieceJointeDto> pieceJointes = new ArrayList<>();
    public static DemandeurMorale toDemandeurMorale(SoumissionDto soumissionDto) {
        return DemandeurMorale.builder()
                .id(soumissionDto.getId())
                .ifu(soumissionDto.getIfu())
                .denomination(soumissionDto.getDenomination())
                .siege(soumissionDto.getSiege())
                .nomResponsable(soumissionDto.getNomResponsable())
                .mail(soumissionDto.getMail())
                .telephone1(soumissionDto.getTelephone1())
                .telephone2(soumissionDto.getTelephone2())
                .build();

    }


    public static DemandeurPhysique toDemandeurPhysique(SoumissionDto soumissionDto) {
        return DemandeurPhysique.builder()
                .id(soumissionDto.getId())
                .nom(soumissionDto.getNom())
                .prenom(soumissionDto.getPrenom())
                .genre(soumissionDto.getGenre())
                .dateNaisse(soumissionDto.getDateNaisse())
                .lieuResidence(soumissionDto.getLieuResidence())
                .telephoneP1(soumissionDto.getTelephoneP1())
                .telephoneP2(soumissionDto.getTelephoneP2())
                .mailP(soumissionDto.getMailP())
                .typePiece(soumissionDto.getTypePiece())
                .numPiece(soumissionDto.getNumPiece())
                .build();
    }



    public static List<PieceJointe> toPieceJointes(SoumissionDto soumissionDto) {
        return soumissionDto.pieceJointes.stream()
                .map(pieceJointeDto -> PieceJointe.builder()
                        .id(pieceJointeDto.getId())
                        .libelle(pieceJointeDto.getLibelle())
                        .url(pieceJointeDto.getUrl())
                        .build())
                .collect(Collectors.toList());
    }

    public static Demande toDemande(SoumissionDto soumissionDto) {
        return Demande.builder()
                .id(soumissionDto.getId())
                .typeDemande(soumissionDto.getTypeDemande())
                .typeDemandeur(soumissionDto.getTypeDemandeur())
                .prix(soumissionDto.getPrix())
                .numeroDemande(soumissionDto.getNumeroDemande())
                .codeDemande(soumissionDto.getCodeDemande())
                .statut(soumissionDto.getStatut())
                .dateDepot(soumissionDto.getDateDepot())
                .dateValidation(soumissionDto.getDateValidation())

                .build();
    }


}