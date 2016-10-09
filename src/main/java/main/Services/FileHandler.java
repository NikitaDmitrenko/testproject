package main.Services;


import main.Repositories.LinesRepository;
import main.model.Lines;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileHandler implements Runnable {

    private Semaphore semaphore;

    private LinesRepository linesRepository;

    public FileHandler(MultipartFile file, LinesRepository linesRepository) {
        this.file = file;
        this.linesRepository = linesRepository;
    }

    private MultipartFile file;

    @Override
    public void run() {
        try {
            semaphore.acquire();
            uploadFiles();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }finally {
            semaphore.release();
        }
    }

    public void uploadFiles() throws IOException, InterruptedException {
        if (this.file.getContentType().equals("text/plain")) {
            System.out.println(formatCyrrylicCharacters(this.file.getOriginalFilename()));
            File dir = new File(System.getProperty("catalina.home") + File.separator + "tmpFiles");
            if (!dir.exists())
                dir.mkdirs();
            String filePath = dir.getAbsolutePath() + File.separator + formatCyrrylicCharacters(this.file.getOriginalFilename());
            upload(this.file,new File(filePath));

            Thread.sleep(1000);
            List<String> allLinesOfText = parseTxtFiles(filePath);
            for (String text : allLinesOfText) {
                Lines lines = new Lines();
                lines.setText(text);
                this.linesRepository.save(lines);
            }
        }
    }

    public void upload(MultipartFile file, File serverFile) {
        try (BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile))) {
            byte[] bytes = file.getBytes();
            stream.write(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<String> parseTxtFiles(String filePath) throws IOException {
        try (Stream<String> lines = Files.lines(Paths.get(filePath), Charset.defaultCharset())) {
            return lines.collect(Collectors.toList());
        }
    }

    public String formatCyrrylicCharacters(String path) throws UnsupportedEncodingException {
        return new String(path.getBytes("ISO-8859-1"), "UTF-8");
    }

    public FileHandler(Semaphore semaphore, MultipartFile file) {
        this.semaphore = semaphore;
        this.file = file;
    }

    public Semaphore getSemaphore() {
        return semaphore;
    }

    public void setSemaphore(Semaphore semaphore) {
        this.semaphore = semaphore;
    }

    public LinesRepository getRepository() {
        return linesRepository;
    }

    public void setRepository(LinesRepository repository) {
        this.linesRepository = repository;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public FileHandler() {
    }

    @Override
    public String toString() {
        return "FileHandler{" +
                "semaphore=" + semaphore +
                ", linesRepository=" + linesRepository +
                ", file=" + file.getOriginalFilename() +
                '}';
    }
}
