package dsi.soutenance.dto;

import dsi.soutenance.model.DemandeurMorale;
import dsi.soutenance.model.DemandeurPhysique;
import dsi.soutenance.model.PieceJointe;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.boot.autoconfigure.info.ProjectInfoProperties;

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
    private String categorie;
    private String typeDemande;

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
                .id(soumissionDto.id)
                .ifu(soumissionDto.ifu)
                .denomination(soumissionDto.denomination)
                .siege(soumissionDto.siege)
                .nomResponsable(soumissionDto.nomResponsable)
                .mail(soumissionDto.mail)
                .telephone1(soumissionDto.telephone1)
                .telephone2(soumissionDto.telephone2)
                .build();

    }


    public static DemandeurPhysique toDemandeurPhysique(SoumissionDto soumissionDto) {
        return DemandeurPhysique.builder()
                .id(soumissionDto.id)
                .nom(soumissionDto.nom)
                .prenom(soumissionDto.prenom)
                .genre(soumissionDto.genre)
                .dateNaisse(soumissionDto.dateNaisse)
                .lieuResidence(soumissionDto.lieuResidence)
                .telephoneP1(soumissionDto.telephoneP1)
                .telephoneP2(soumissionDto.telephoneP2)
                .mailP(soumissionDto.mailP)
                .typePiece(soumissionDto.typePiece)
                .numPiece(soumissionDto.numPiece)
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
                .id(soumissionDto.id)
                .categorie(soumissionDto.categorie)
                .typeDemande(soumissionDto.typeDemande)
                .prix(soumissionDto.prix)
                .numeroDemande(soumissionDto.numeroDemande)
                .codeDemande(soumissionDto.codeDemande)
                .statut(soumissionDto.statut)
                .dateDepot(soumissionDto.dateDepot)
                .dateValidation(soumissionDto.dateValidation)
                .build();
    }


        // Getters et setters avec conversion de type si nécessaire
        public void setNumeroDemande(String numeroDemande) {
            this.numeroDemande = numeroDemande != null && !numeroDemande.isEmpty() ?
                    Long.parseLong(numeroDemande) : null;
        }

        public void setDateDepot(String dateDepot) {
            this.dateDepot = dateDepot != null && !dateDepot.isEmpty() ?
                    LocalDate.parse(dateDepot) : null;
        }


}