package tn.iit.web;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import tn.iit.dto.DemandeImpressionDto;
import tn.iit.models.DemandeImpression;
import tn.iit.services.DemandeImpService;
import tn.iit.utils.FileUploadProperties;

@RestController
@RequestMapping("demandes")
@CrossOrigin
public class DemandeImpController {

	private final DemandeImpService demandeImpService;
	private final Path documentPath;

	@Autowired
	public DemandeImpController(final DemandeImpService demandeImpService,
			final FileUploadProperties fileUploadProperties) {
		super();
		this.demandeImpService = demandeImpService;
		this.documentPath = Paths.get(fileUploadProperties.getLocation()).toAbsolutePath().normalize();
	}

	@GetMapping
	public List<DemandeImpression> getAllDemandesImp() {
		return demandeImpService.getAllDemandesImp();
	}

	@GetMapping("{id}")
	public Optional<DemandeImpression> getDemandeById(@PathVariable long id) {
		return demandeImpService.getDemandeById(id);
	}

	@PostMapping
	public DemandeImpression saveDemandeImp(@RequestBody DemandeImpression demandeImp) {
		return demandeImpService.saveDemandeImp(demandeImp);
	}

	@RequestMapping(value = "document", method = RequestMethod.POST, consumes = { "multipart/form-data" })
	public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
		FileOutputStream fout = null;
		try {
			String extension = FilenameUtils.getExtension(file.getOriginalFilename());
			if (!extension.equals("pdf")) {
				return new ResponseEntity<String>("le document doit être sous form de pdf", HttpStatus.BAD_REQUEST);
			}
			String fileName = UUID.randomUUID().toString() + "." + extension;
			String fullpath = this.documentPath.toString() + "\\" + fileName;
			File convertFile = new File(fullpath);
			convertFile.createNewFile();
			fout = new FileOutputStream(convertFile);
			fout.write(file.getBytes());
			fout.close();
			return new ResponseEntity<String>(fileName, HttpStatus.CREATED);
		} catch (IOException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/enseignant/list/{id}")
	List<DemandeImpressionDto> getEnseignantDemandes(@PathVariable long id) {
		List<DemandeImpression> listDemandes = demandeImpService.getDemandeByEnsId(id);
		return listDemandes.stream().map(demande -> {
			return new DemandeImpressionDto(demande.getId(), demandeImpService.getEnseignant(demande.getIdEnseignant()),
					demandeImpService.getMatiere(demande.getIdMatiere()), demande.getNbCopie(),
					demande.getDateArrivage(), demande.getTempArrivage(), demande.getDescription(),
					demande.getFicherPdf(), demande.getEtatDemande());
		}).collect(Collectors.toList());
	}

	@GetMapping("/list")
	List<DemandeImpressionDto> getAllDemandes(@PathVariable long id) {
		List<DemandeImpression> listDemandes = demandeImpService.getAllDemandesImp();
		return listDemandes.stream().map(demande -> {
			return new DemandeImpressionDto(demande.getId(), demandeImpService.getEnseignant(demande.getIdEnseignant()),
					demandeImpService.getMatiere(demande.getIdMatiere()), demande.getNbCopie(),
					demande.getDateArrivage(), demande.getTempArrivage(), demande.getDescription(),
					demande.getFicherPdf(), demande.getEtatDemande());
		}).collect(Collectors.toList());
	}

	@RequestMapping(value = "/pdf/{fileName}", method = RequestMethod.GET, produces = "application/pdf")
	public ResponseEntity<InputStreamResource> download(@PathVariable("fileName") String fileName) throws IOException {
		String p = "Documents/" + fileName;
		ClassPathResource pdfFile = new ClassPathResource(p);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.parseMediaType("application/pdf"));
		headers.add("Access-Control-Allow-Origin", "*");
		headers.add("Access-Control-Allow-Methods", "GET, POST, PUT");
		headers.add("Access-Control-Allow-Headers", "Content-Type");
		headers.add("Content-Disposition", "filename=" + fileName);
		headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
		headers.add("Pragma", "no-cache");
		headers.add("Expires", "0");

		headers.setContentLength(pdfFile.contentLength());
		ResponseEntity<InputStreamResource> response = new ResponseEntity<InputStreamResource>(
				new InputStreamResource(pdfFile.getInputStream()), headers, HttpStatus.OK);
		return response;

	}
}
