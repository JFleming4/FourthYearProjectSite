package app.controllers;

import app.models.FileAttachment;
import app.models.Student;
import app.models.repository.FileAttachmentRepository;
import app.models.repository.StudentRepository;
import app.storage.StorageFileNotFoundException;
import app.storage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.stream.Collectors;

import static app.models.FileAttachment.FileAttachmentType;

@Controller
public class FileController {
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private FileAttachmentRepository fileAttachmentRepository;

    private final StorageService storageService;

    @Autowired
    public FileController(StorageService storageService) {
        this.storageService = storageService;
    }

    @GetMapping("/files")
    public String listUploadedFiles(Model model) throws IOException {

        model.addAttribute("files", storageService.loadAll().map(
                path -> MvcUriComponentsBuilder.fromMethodName(FileController.class,
                        "serveFile", path.getFileName().toString()).build().toString())
                .collect(Collectors.toList()));

        return "uploadForm";
    }

    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

        Resource file = storageService.loadAsResource(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @PostMapping("/student/{id}/upload_proposal")
    public String uploadProposal(@PathVariable Long id,
                             @RequestParam("file") MultipartFile file,
                             RedirectAttributes redirectAttributes) {
        Student student = studentRepository.findOne(id);
        uploadFile(file, student.getProject().getProposal());

        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + file.getOriginalFilename() + "!");
        return "redirect:/student";
    }

    @PostMapping("/student/{id}/upload_draft")
    public String uploadDraft(@PathVariable Long id,
                             @RequestParam("file") MultipartFile file,
                             RedirectAttributes redirectAttributes) {
        Student student = studentRepository.findOne(id);
        uploadFile(file, student.getProject().getDraft());

        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + file.getOriginalFilename() + "!");
        return "redirect:/student";
    }

    @PostMapping("/student/{id}/upload_final_report")
    public String uploadReport(@PathVariable Long id,
                             @RequestParam("file") MultipartFile file,
                             RedirectAttributes redirectAttributes) {
        Student student = studentRepository.findOne(id);
        uploadFile(file, student.getProject().getReport());

        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + file.getOriginalFilename() + "!");
        return "redirect:/student";
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }

    private void uploadFile(MultipartFile uploadedFile, FileAttachment oldFile) {
        FileAttachment fileAttachment = new FileAttachment(
                uploadedFile.getOriginalFilename(),
                oldFile.getProjectAssetType(),
                oldFile.getProject());

        storageService.store(uploadedFile);
        fileAttachmentRepository.save(fileAttachment);
        if (oldFile != null) {
            fileAttachmentRepository.delete(oldFile.getId());
            storageService.delete(uploadedFile.getOriginalFilename());
        }
    }
}
