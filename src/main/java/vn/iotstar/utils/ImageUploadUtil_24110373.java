package vn.iotstar.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.Part;

public class ImageUploadUtil_24110373 {

    private static final String UPLOAD_SUBPATH = "/uploads/covers";

    public static String saveCoverImage(Part filePart, ServletContext context) throws IOException {
        if (filePart == null || filePart.getSize() <= 0) {
            return null;
        }

        String submittedName = filePart.getSubmittedFileName();
        if (submittedName == null || submittedName.isBlank()) {
            return null;
        }

        String contentType = filePart.getContentType();
        boolean looksLikePng = submittedName.toLowerCase().endsWith(".png") || "image/png".equals(contentType);
        if (!looksLikePng) {
            throw new IllegalArgumentException("Chỉ chấp nhận file ảnh định dạng PNG (.png).");
        }

        String realUploadDir = context.getRealPath(UPLOAD_SUBPATH);
        File dir = new File(realUploadDir);
        if (!dir.exists() && !dir.mkdirs()) {
            throw new IOException("Không thể tạo thư mục lưu ảnh: " + realUploadDir);
        }

        String fileName = UUID.randomUUID().toString().replace("-", "") + ".png";
        File target = new File(dir, fileName);

        try (InputStream in = filePart.getInputStream()) {
            Files.copy(in, target.toPath(), StandardCopyOption.REPLACE_EXISTING);
        }

        return UPLOAD_SUBPATH + "/" + fileName;
    }
}
