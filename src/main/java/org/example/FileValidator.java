package org.example;

import java.io.FileInputStream;

public class FileValidator implements InterfaceFileValidator{
    @Override
    public boolean isValid(String filePath) {
        try (FileInputStream fis = new FileInputStream(filePath)) {
            // Try to read the first few bytes of the file
            byte[] header = new byte[4];
            if (fis.read(header) != 4) {
                return false;
            }

            // Check for MP3 file signature - simple check for ID3v2 tag or MP3 sync word
            return (header[0] == 'I' && header[1] == 'D' && header[2] == '3') ||
                    ((header[0] & 0xFF) == 0xFF && (header[1] & 0xE0) == 0xE0);
        } catch (Exception e) {
            System.out.println("Error validating MP3 file " + filePath + ": " + e.getMessage());
            return false;
        }
    }
}
