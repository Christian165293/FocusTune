package org.example;

import java.io.FileInputStream;

//validates that a file is a valid MP3 file
//the validation is done by checking the headers bytes.
public class FileValidator implements InterfaceFileValidator {
    //Check if MP3 is valid
    @Override
    public boolean isValid(String filePath) {
        try (FileInputStream fis = new FileInputStream(filePath)) {
            // Try to read the first few bytes of the file
            byte[] header = new byte[4];
            if (fis.read(header) != 4) {
                return false;
            }

            // Check for MP3 file signature
            // Simple check for ID3v2 tag or MP3 sync word
            return (header[0] == 'I' && header[1] == 'D' && header[2] == '3') ||//Many MP3s start with the metadata ID3v2 tag (ID3)
                    ((header[0] & 0xFF) == 0xFF && (header[1] & 0xE0) == 0xE0); //Raw MP3 frames often start with the bit pattern (0xFF 0xE0)
        } catch (Exception e) {
            System.out.println("Error validating MP3 file " + filePath + ": " + e.getMessage());
            return false;
        }
    }
}
