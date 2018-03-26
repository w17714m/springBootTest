package testImage;

import java.io.File;
import java.io.FileOutputStream;

import javax.swing.JOptionPane;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import OnBarcode.Barcode.BarcodeScanner.BarcodeScanner;
import OnBarcode.Barcode.BarcodeScanner.BarcodeType;

@Controller
@RequestMapping("/api/")
public class controller {

	@CrossOrigin("http://localhost:8080/")
	@PostMapping("/test/")
	public ResponseEntity<String> getDataCC(){
		
		String []  barcodes = BarcodeScanner.Scan("agregar url local", BarcodeType.PDF417);
		
		if(barcodes.length>0) {
			System.out.println("longitud" + barcodes[0].toString());
		}else {
			System.out.println("No devolvio nada" );
		}
		
		return new ResponseEntity<String>(barcodes[0].toString(),HttpStatus.OK);
	}
	@CrossOrigin("http://localhost:8080/")
	@PostMapping("/archivo/")
	public ResponseEntity<String> getDataFile(@RequestParam("archivo") MultipartFile file){
		try {
			File ft = saveFile(file);
			if(ft.exists()) {
				System.out.println("EL ARCHIVO EXISTE");
			}else
			{
				System.out.println("NO EXISTE");
			}
			
			System.out.println("LA RUTA ABSOLUTA ES: -->"+ft.getAbsolutePath());
			String []  barcodes = BarcodeScanner.Scan(ft.getAbsolutePath(), BarcodeType.PDF417);
			
			if(barcodes.length>0) {
				System.out.println("longitud" + barcodes[0].toString());
			}else {
				System.out.println("No devolvio nada" );
			}
			
			return new ResponseEntity<String>(barcodes[0].toString(),HttpStatus.OK);
			
			
		} catch (Exception e) {
			System.out.println("Error no identificado" + e.getStackTrace());
			return null;
		}
	}
	
	
	private File saveFile(MultipartFile mpf) throws Exception {
	    File convFile = new File( mpf.getOriginalFilename());
	    convFile.createNewFile();
	    FileOutputStream fos = new FileOutputStream(convFile); 
	    fos.write(mpf.getBytes());
	    fos.close(); 
	    return convFile;
	}
	
}
