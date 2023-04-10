package hr.algebra.webshop.utils;

import hr.algebra.webshop.model.Category;
import hr.algebra.webshop.model.Merch;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.Objects;

import static hr.algebra.webshop.utils.FileUtils.getFileExtension;

public class ImageUtils {
    public static void imageConvertToBase64ForMerch(Merch newMerch, MultipartFile imageBase64) throws IOException {
        byte[] imageData = imageBase64.getBytes();
        String fileExtension = getFileExtension(Objects.requireNonNull(imageBase64.getOriginalFilename()));
        String base64Image = Base64.getEncoder().encodeToString(imageData);
        newMerch.setFrontImageBase64("data:image/" + fileExtension + ";base64," + base64Image);
    }

    public static void imageConvertToBase64ForCategory(Category newCategory, MultipartFile imageBase64) throws IOException {
        byte[] imageData = imageBase64.getBytes();
        String fileExtension = getFileExtension(Objects.requireNonNull(imageBase64.getOriginalFilename()));
        String base64Image = Base64.getEncoder().encodeToString(imageData);
        newCategory.setFrontImageBase64("data:image/" + fileExtension + ";base64," + base64Image);
    }
}
