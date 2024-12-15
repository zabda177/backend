package dsi.soutenance.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.UUID;

    @Service
    public class FileStorageService {

        @Value("${file.storage.location}")
        private String storageLocation;

        public String storeFile(MultipartFile file, String typeDossier, String idType) throws IOException {
            validateFile(file);

            // Générer un nom de fichier unique
            String fileName = UUID.randomUUID().toString() + "_" + sanitizeFileName(Objects.requireNonNull(file.getOriginalFilename()));

            // Déterminer le chemin cible
            Path targetLocation = getFilePath(fileName, typeDossier, idType);

            // Sauvegarder le fichier
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return fileName;
        }

        public Path loadFile(String fileName, String typeDossier, String idType) throws IOException {
            return getFilePath(fileName, typeDossier, idType);
        }

        private Path getFilePath(String fileName, String typeDossier, String idType) throws IOException {
            // Construire le chemin du sous-dossier
            Path subfolderPath = Paths.get(storageLocation, typeDossier, idType);

            // Créer les répertoires si nécessaire
            if (!Files.exists(subfolderPath)) {
                Files.createDirectories(subfolderPath);
            }

            // Retourner le chemin complet du fichier
            return subfolderPath.resolve(fileName).normalize();
        }

        private void validateFile(MultipartFile file) throws IOException {

            if (file == null) {
                throw new IOException("Aucun fichier n'a été fourni.");
            }

            if (file.isEmpty()) {
                throw new IOException("Impossible de sauvegarder un fichier vide.");
            }
        }

        private String sanitizeFileName(String fileName) {
            // Nettoyer le nom du fichier pour éviter les caractères non valides
            return fileName.replaceAll("[^a-zA-Z0-9\\._-]", "_");
        }
    }


