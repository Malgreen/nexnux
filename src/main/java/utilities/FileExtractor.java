package main.java.utilities;


import com.github.junrar.exception.RarException;
import com.google.common.io.Files;

import java.io.*;
import java.util.zip.*;
import com.github.junrar.*;

public class FileExtractor {

    public void extractFile(String fileName, String outputPath) throws IOException, RarException {
        getFileType(fileName, outputPath);
    }

    private void getFileType(String fileName, String outputPath) throws IOException, RarException {
        String type = Files.getFileExtension(fileName);
        String realFileName = Files.getNameWithoutExtension(fileName);
        String realOutputPath = outputPath + "\\" + realFileName;
        System.out.println(type);
        if (type == null) { return; }
        switch (type){
            case "zip":
                extractZip(fileName, realOutputPath);
                break;
            case "rar":
                extractRar(fileName, realOutputPath);
                break;
            default:
                throw new IOException("Selected file is not a supported archive type");
        }
    }
    private void extractZip(String fileName, String outputPath) throws IOException {
        File outputFile = new File(outputPath);
        byte[] buffer = new byte[1024];
        ZipInputStream zis = new ZipInputStream(new FileInputStream(fileName));
        ZipEntry ze = zis.getNextEntry();
        while (ze != null){
            File newFile = newFile(outputFile, ze);
            if (ze.isDirectory()) {
                if (!newFile.isDirectory() && !newFile.mkdirs()) {
                    throw new IOException("Failed to create directory " + newFile);
                }
            } else {
                // fix for Windows-created archives
                File parent = newFile.getParentFile();
                if (!parent.isDirectory() && !parent.mkdirs()) {
                    throw new IOException("Failed to create directory " + parent);
                }

                // write file content
                FileOutputStream fos = new FileOutputStream(newFile);
                int len;
                while ((len = zis.read(buffer)) > 0) {
                    fos.write(buffer, 0, len);
                }
                fos.close();
            }
            ze = zis.getNextEntry();
        }
        zis.closeEntry();
        zis.close();

    }
    private void extractRar(String fileName, String outputPath) throws RarException, IOException {
        final File inputFile = new File(fileName);
        final File outputFolder = new File(outputPath);
        Junrar.extract(inputFile, outputFolder);

    }

    public static File newFile(File destinationDir, ZipEntry zipEntry) throws IOException {
        File destFile = new File(destinationDir, zipEntry.getName());

        String destDirPath = destinationDir.getCanonicalPath();
        String destFilePath = destFile.getCanonicalPath();

        if (!destFilePath.startsWith(destDirPath + File.separator)) {
            throw new IOException("Entry is outside of the target dir: " + zipEntry.getName());
        }

        return destFile;
    }
}
