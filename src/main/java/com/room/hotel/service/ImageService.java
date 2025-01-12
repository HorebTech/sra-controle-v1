package com.room.hotel.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.UUID;

import org.springframework.stereotype.Service;

import net.coobird.thumbnailator.Thumbnails;

@Service
public class ImageService {

    private static final String UPLOAD_DIR = "uploads"; // Changez selon votre chemin

    public String saveImage(String imageBase64, String folderName) throws IOException {
        if (imageBase64 == null || !imageBase64.contains(",")) {
            throw new IllegalArgumentException("Données d'image invalides ou non fournies.");
        }

        // Séparer les métadonnées et les données de l'image
        String[] splitted = imageBase64.split(",");
        String metadata = splitted[0];
        String base64Data = splitted[1];

        // Vérifier le type MIME de l'image
        String imageType = metadata.split(";")[0].split(":")[1];
        if (!imageType.startsWith("image/")) {
            throw new IllegalArgumentException("Le type de fichier n'est pas une image valide.");
        }

        // Décoder les données Base64
        byte[] imageBytes = Base64.getDecoder().decode(base64Data);

        // Vérifier si l'image dépasse 500 KB, et la compresser si nécessaire
        if (imageBytes.length > 500 * 1024) {
            imageBytes = compressImage(imageBytes, 800, 800, 0.8);
        }

        // Générer un nom unique pour l'image
        String extension = imageType.split("/")[1];
        String nomImage = UUID.randomUUID() + "." + extension;

        // Déterminer le chemin et enregistrer l'image
        // Path path = Paths.get(UPLOAD_DIR, nomImage);
        // Files.createDirectories(path.getParent()); // Créer les répertoires si nécessaire
        // Files.write(path, imageBytes); // Écrire les données dans le fichier

        // return nomImage;

        // 6. Construire le chemin du dossier dynamique
        Path folderPath = Paths.get(UPLOAD_DIR, folderName);
        Files.createDirectories(folderPath); // Créer les dossiers si nécessaires

        // 7. Enregistrer l'image
        Path filePath = folderPath.resolve(nomImage);
        Files.write(filePath, imageBytes);

        // 8. Retourner le chemin relatif pour référence (par exemple : "pannes/image123.jpg")
        return folderName + "/" + nomImage;
    }

    private byte[] compressImage(byte[] imageBytes, int maxWidth, int maxHeight, double quality) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        Thumbnails.of(new ByteArrayInputStream(imageBytes))
                .size(maxWidth, maxHeight)
                .outputQuality(quality) // Compression JPEG
                .toOutputStream(outputStream);

        return outputStream.toByteArray();
    }
}
