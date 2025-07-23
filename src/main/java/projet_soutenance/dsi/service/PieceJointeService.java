package projet_soutenance.dsi.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import projet_soutenance.dsi.DTO.PieceJointeDTO;
import projet_soutenance.dsi.mapper.PieceJointeMapper;
import projet_soutenance.dsi.model.Demande;
import projet_soutenance.dsi.model.PieceJointe;
import projet_soutenance.dsi.repositorie.DemandeRepository;
import projet_soutenance.dsi.repositorie.PieceJointeRepository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PieceJointeService {
    private final PieceJointeRepository pieceJointeRepository;
    private final DemandeRepository demandeRepository;
    private final PieceJointeMapper pieceJointeMapper;

    @Value("${app.upload.dir:${user.home}/uploads}")
    private String uploadDir;

    @Autowired
    public PieceJointeService(
            PieceJointeRepository pieceJointeRepository,
            DemandeRepository demandeRepository,
            PieceJointeMapper pieceJointeMapper) {
        this.pieceJointeRepository = pieceJointeRepository;
        this.demandeRepository = demandeRepository;
        this.pieceJointeMapper = pieceJointeMapper;
    }

    public List<PieceJointeDTO> getAllPieceJointes() {
        return pieceJointeRepository.findAll().stream()
                .filter(pieceJointe -> !pieceJointe.isDeleted())
                .map(pieceJointeMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<PieceJointeDTO> getPieceJointesByDemandeId(Long demandeId) {
        return pieceJointeRepository.findByDemandeIdAndDeletedFalse(demandeId).stream()
                .map(pieceJointeMapper::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<PieceJointeDTO> getPieceJointeById(Long id) {
        return pieceJointeRepository.findById(id)
                .filter(pieceJointe -> !pieceJointe.isDeleted())
                .map(pieceJointeMapper::toDTO);
    }

    @Transactional
    public PieceJointeDTO createPieceJointe(Long demandeId, String libelle, MultipartFile fichier) throws IOException {
        // Vérifier si la demande existe
        Optional<Demande> demandeOpt = demandeRepository.findById(demandeId);
        if (demandeOpt.isEmpty()) {
            throw new IllegalArgumentException("Demande avec ID " + demandeId + " n'existe pas");
        }
        Demande demande = demandeOpt.get();

        // Créer le répertoire d'upload si nécessaire
        File uploadDirectory = new File(uploadDir);
        if (!uploadDirectory.exists()) {
            uploadDirectory.mkdirs();
        }

        // Générer un nom de fichier unique pour éviter les écrasements
        String fileName = UUID.randomUUID().toString() + "_" + fichier.getOriginalFilename();
        Path filePath = Paths.get(uploadDir, fileName);

        // Enregistrer le fichier sur le système de fichiers
        Files.write(filePath, fichier.getBytes());

        // Créer l'entité PieceJointe
        PieceJointe pieceJointe = new PieceJointe();
        pieceJointe.setLibelle(libelle);
        pieceJointe.setUrl(filePath.toString());
        pieceJointe.setDemande(demande);
        pieceJointe.setDeleted(false);

        PieceJointe savedPieceJointe = pieceJointeRepository.save(pieceJointe);
        return pieceJointeMapper.toDTO(savedPieceJointe);
    }

    @Transactional
    public List<PieceJointeDTO> createMultiplePiecesJointes(Long demandeId, List<MultipartFile> fichiers, List<String> libelles) throws IOException {
        List<PieceJointeDTO> createdPiecesJointes = new ArrayList<>();

        for (int i = 0; i < fichiers.size(); i++) {
            MultipartFile fichier = fichiers.get(i);
            String libelle = (i < libelles.size()) ? libelles.get(i) : "Pièce " + (i + 1);

            PieceJointeDTO pieceJointeDTO = createPieceJointe(demandeId, libelle, fichier);
            createdPiecesJointes.add(pieceJointeDTO);
        }

        return createdPiecesJointes;
    }

    @Transactional
    public Optional<PieceJointeDTO> updatePieceJointe(Long id, String libelle, MultipartFile fichier) throws IOException {
        return pieceJointeRepository.findById(id)
                .filter(pieceJointe -> !pieceJointe.isDeleted())
                .map(pieceJointe -> {
                    if (libelle != null && !libelle.isEmpty()) {
                        pieceJointe.setLibelle(libelle);
                    }

                    // Si un nouveau fichier est fourni, mettre à jour le fichier
                    if (fichier != null && !fichier.isEmpty()) {
                        try {
                            // Supprimer l'ancien fichier si possible
                            try {
                                File oldFile = new File(pieceJointe.getUrl());
                                if (oldFile.exists()) {
                                    oldFile.delete();
                                }
                            } catch (Exception e) {
                                // Ignorer les erreurs de suppression
                            }

                            // Générer un nom de fichier unique pour éviter les écrasements
                            String fileName = UUID.randomUUID().toString() + "_" + fichier.getOriginalFilename();
                            Path filePath = Paths.get(uploadDir, fileName);

                            // Enregistrer le nouveau fichier
                            Files.write(filePath, fichier.getBytes());
                            pieceJointe.setUrl(filePath.toString());
                        } catch (IOException e) {
                            throw new RuntimeException("Erreur lors de la mise à jour du fichier", e);
                        }
                    }

                    return pieceJointeMapper.toDTO(pieceJointeRepository.save(pieceJointe));
                });
    }

    @Transactional
    public boolean deletePieceJointe(Long id) {
        return pieceJointeRepository.findById(id)
                .map(pieceJointe -> {
                    pieceJointe.setDeleted(true);
                    pieceJointeRepository.save(pieceJointe);
                    return true;
                })
                .orElse(false);
    }

    @org.springframework.transaction.annotation.Transactional
    public List<PieceJointeDTO> savePiecesJointes(Long demandeId, MultipartFile[] files) throws IOException {
        List<PieceJointeDTO> savedPiecesJointes = new ArrayList<>();

        if (files != null && files.length > 0) {
            for (MultipartFile file : files) {
                if (!file.isEmpty()) {
                    // Utiliser le nom du fichier original comme libellé
                    String libelle = file.getOriginalFilename();

                    // Utiliser la méthode createPieceJointe existante
                    PieceJointeDTO pieceJointeDTO = createPieceJointe(demandeId, libelle, file);
                    savedPiecesJointes.add(pieceJointeDTO);
                }
            }
        }

        return savedPiecesJointes;
    }

    public Optional<PieceJointeDTO> getPieceJointeByFileName(String fileName) {

        // Sinon, vous pouvez rechercher toutes les pièces jointes et filtrer
        List<PieceJointe> allPieces = pieceJointeRepository.findAll()
                .stream()
                .filter(pieceJointe -> !pieceJointe.isDeleted())
                .collect(Collectors.toList());

        for (PieceJointe piece : allPieces) {
            // Extraire le nom du fichier à partir de l'URL
            String filePathStr = piece.getUrl();
            Path filePath = Paths.get(filePathStr);
            String pieceFileName = filePath.getFileName().toString();

            // Comparer avec le nom de fichier recherché
            if (pieceFileName.equals(fileName) || filePathStr.endsWith(fileName)) {
                return Optional.of(pieceJointeMapper.toDTO(piece));
            }
        }

        return Optional.empty();
    }


    /**
     * Récupère une pièce jointe par son URL
     * @param url L'URL de la pièce jointe
     * @return Optional contenant la PieceJointeDTO si trouvée
     */
    public Optional<PieceJointeDTO> getPieceJointeByUrl(String url) {
        if (url == null || url.trim().isEmpty()) {
            //log.warn("URL fournie est null ou vide");
            return Optional.empty();
        }

        try {
            // Normaliser l'URL pour la comparaison
            String normalizedUrl = normalizeUrl(url);

            //log.debug("Recherche de pièce jointe avec URL: {}", normalizedUrl);

            // Rechercher toutes les pièces jointes non supprimées
            List<PieceJointe> allPieces = pieceJointeRepository.findAll()
                    .stream()
                    .filter(pieceJointe -> !pieceJointe.isDeleted())
                    .collect(Collectors.toList());

            for (PieceJointe piece : allPieces) {
                String pieceUrl = piece.getUrl();
                if (pieceUrl != null) {
                    String normalizedPieceUrl = normalizeUrl(pieceUrl);

                    // Comparaison exacte
                    if (normalizedPieceUrl.equals(normalizedUrl)) {
                        //log.debug("Pièce jointe trouvée avec URL exacte: {}", piece.getId());
                        return Optional.of(pieceJointeMapper.toDTO(piece));
                    }

                    // Comparaison par nom de fichier si l'URL exacte ne correspond pas
                    String pieceFileName = extractFileNameFromPath(normalizedPieceUrl);
                    String searchFileName = extractFileNameFromPath(normalizedUrl);

                    if (pieceFileName != null && searchFileName != null &&
                            pieceFileName.equals(searchFileName)) {
                        //log.debug("Pièce jointe trouvée par nom de fichier: {}", piece.getId());
                        return Optional.of(pieceJointeMapper.toDTO(piece));
                    }
                }
            }

            //log.warn("Aucune pièce jointe trouvée pour l'URL: {}", normalizedUrl);
            return Optional.empty();

        } catch (Exception e) {
            //log.error("Erreur lors de la recherche de pièce jointe par URL: {}", url, e);
            return Optional.empty();
        }
    }

    /**
     * Normalise une URL pour faciliter les comparaisons
     * @param url L'URL à normaliser
     * @return L'URL normalisée
     */
    private String normalizeUrl(String url) {
        if (url == null) {
            return "";
        }

        // Convertir les antislashes en slashes
        String normalized = url.replace("\\", "/");

        // Supprimer les espaces en début et fin
        normalized = normalized.trim();

        return normalized;
    }

    /**
     * Extrait le nom de fichier depuis un chemin
     * @param path Le chemin du fichier
     * @return Le nom du fichier ou null si non trouvé
     */
    private String extractFileNameFromPath(String path) {
        if (path == null || path.trim().isEmpty()) {
            return null;
        }

        try {
            // Utiliser Path pour extraire le nom de fichier
            Path filePath = Paths.get(path);
            return filePath.getFileName().toString();
        } catch (Exception e) {
            // Fallback manuel si Path.get() échoue
            String normalizedPath = path.replace("\\", "/");
            int lastSlash = normalizedPath.lastIndexOf("/");
            if (lastSlash >= 0 && lastSlash < normalizedPath.length() - 1) {
                return normalizedPath.substring(lastSlash + 1);
            }
            return normalizedPath; // Retourner le path complet si pas de séparateur trouvé
        }
    }

}










