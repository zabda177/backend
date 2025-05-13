package projet_soutenance.dsi.DTO;

import projet_soutenance.dsi.model.Demande;
import projet_soutenance.dsi.model.PersonneMorale;
import projet_soutenance.dsi.model.PersonnePhysique;

import java.time.LocalDate;
import java.util.List;

public class SoumissionDTO {

    private String nom;
    private String prenom;
    private String genre;
    private LocalDate dateNaissance;
    private String nationalite;
    private String villeResidance;
    private String telephone1PersonnePhysique;
    private String telephone2PersonnePhysique;
    private String mailPersonnePhysique;
    private String typePiece;
    private String numPiece;

    private String nomResponsable;
    private String prenomResponsable;
    private String denomination;
    private String siege;
    private String ifu;
    private String mailPersonneMorale;
    private String telephone1PersonneMorale;
    private String telephone2PersonneMorale;

    private String typeDemande;
    private String typeDemandeur;
    private String statut;
    private Long prix;
    private Long numeroDemande;
    private String codeDemande;
    private LocalDate dateDepot;
    private LocalDate dateValidation;
    private String motifRejet;
    private LocalDate  dateRejet;

    private List<DemandeDTO> Ddemande;

   public  static PersonnePhysique toPersonndPhysique (SoumissionDTO soumissionDTO) {
       return PersonnePhysique.builder()
               .nom(soumissionDTO.nom)
               .prenom(soumissionDTO.prenom)
               .genre(soumissionDTO.genre)
               .nationalite(soumissionDTO.nationalite)
               .dateNaissance(soumissionDTO.dateNaissance)
               .typePiece(soumissionDTO.typePiece)
               .numPiece(soumissionDTO.numPiece)
               .mailPersonnePhysique(soumissionDTO.mailPersonnePhysique)
               .telephone1PersonnePhysique(soumissionDTO.telephone1PersonnePhysique)
               .telephone2PersonnePhysique(soumissionDTO.telephone2PersonnePhysique)
               .villeResidance(soumissionDTO.villeResidance)
               .build();
   }

   public static PersonneMorale toPersonneMorale (SoumissionDTO soumissionDTO) {
       return  PersonneMorale.builder()
               .nomResponsable(soumissionDTO.nomResponsable)
               .prenomResponsable(soumissionDTO.prenomResponsable)
               .denomination(soumissionDTO.denomination)
               .siege(soumissionDTO.siege)
               .ifu(soumissionDTO.ifu)
               .mailPersonneMorale(soumissionDTO.mailPersonnePhysique)
               .telephone1PersonneMorale(soumissionDTO.telephone1PersonneMorale)
               .telephone2PersonneMorale(soumissionDTO.telephone2PersonneMorale)
               .build();
   }

   public static Demande toDemande (SoumissionDTO soumissionDTO) {
       return  Demande.builder()
               .typeDemande(soumissionDTO.typeDemande)
               .typeDemandeur(soumissionDTO.typeDemandeur)
               .statut(soumissionDTO.statut)
               .numeroDemande(soumissionDTO.numeroDemande)
               .codeDemande(soumissionDTO.codeDemande)
               .prix(soumissionDTO.prix)
               .dateDepot(soumissionDTO.dateDepot)
               .dateValidation(soumissionDTO.dateValidation)
               .dateRejet(soumissionDTO.dateRejet)
               .motifRejet(soumissionDTO.motifRejet)
               .build();
   }

}
