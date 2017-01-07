package ramendj;




import java.awt.List;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;


import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;



@SuppressWarnings("unused")
@RestController
public class DataController {
	
	
	
		
	
	    @RequestMapping("/audio/getallbyname")
	    @ResponseBody
		 public    ResponseEntity<ArrayList<String>> getAllByName(String name) {
		    try {
		    	
		    	ArrayList<String> fileNameList = getAllName(name);
		    	
		    	if (fileNameList.isEmpty())
		    	{
		    	
		    		 return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ArrayList<String>());
		    	}
		    	else
		    	{

		    		 return  ResponseEntity.status(HttpStatus.OK).body(fileNameList);
		    	}
		    }
		    catch (Exception ex) {
		    	ex.printStackTrace();
		    	return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ArrayList<String>());
		    }
		   
		  }
		  
		 
	  
	    
	    
	 @RequestMapping("/audio/getbyname")
	  @ResponseBody
	  public ResponseEntity<String> get(String name) {
	    try {
	    	
	    	String filePath = getFile(name);
	    	
	    	if (filePath==null)
	    	{
	    	
	    		 return ResponseEntity.status(HttpStatus.NOT_FOUND).body("notfound");
	    	}
	    	else
	    	{

	    		 return ResponseEntity.status(HttpStatus.FOUND).body(filePath); 
	    	}
	    }
	    catch (Exception ex) {
	    	ex.printStackTrace();
	      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("badrequest");
	    }
	   
	  }
	  
	 @RequestMapping(value="/download", method=RequestMethod.GET)
		public ResponseEntity<Resource> donwloadFile() {
			System.out.println("\n********** Download MP3 File : ************\n");
			
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.parseMediaType("audio/mpeg"));
			headers.setContentDispositionFormData("remix", "remix.mp3");
			File file = new File("C:/audio/yeu.mp3");
					
			
			FileSystemResource fileSystemResource = new FileSystemResource(file);
			
			return new ResponseEntity<>(fileSystemResource, headers, HttpStatus.OK);
		}
	    
	   
	    
	 private String getFile(String name){
		 File folder = new File("C:/audio");
	    	File[] listOfFiles = folder.listFiles();
	    	File returnFile;
	    	for(int i = 0; i<listOfFiles.length;i++)
	    	{
	    		System.out.println("dang xe ten" + listOfFiles[i]);
	    		//if(listOfFiles[i].getName().toLowerCase().contains(name.toLowerCase())==true)
	    		if(listOfFiles[i].getName().compareToIgnoreCase(name+".mp3")==0)
	    		{
	    			
	    			return listOfFiles[i].getAbsolutePath();
	    		}
	    	}
	    		return null;
	 }
	 
	 
	 
	 
	 private ArrayList<String> getAllName(String name){
		 File folder = new File("C:/audio");
	    	File[] listOfFiles = folder.listFiles();
	    	ArrayList<String> returnList = new ArrayList<String>();
	    	for(int i = 0; i<listOfFiles.length;i++)
	    	{
	    		System.out.println("dang xe ten" + listOfFiles[i]);
	    		if(listOfFiles[i].getName().toLowerCase().contains(name.toLowerCase())==true)
	    		{
	    			
	    			returnList.add(listOfFiles[i].getName().substring(0,listOfFiles[i].getName().length()-4));
	    		}
	    	}
	    		return returnList;
	 }

	 @RequestMapping(value = "/upload", method = RequestMethod.POST)
		public ResponseEntity<String> postUpPost(@RequestParam("files[]") ArrayList<MultipartFile> file) {
			int i = 0;
			File uploadFile = new File("C:/audio/upload_audio.mp3");
			for (MultipartFile f : file) {
				if (!f.isEmpty()) {
					
					String[] tmp = f.getOriginalFilename().split("\\.");
					String ext = tmp[tmp.length - 1];
					
					
					try {
						f.transferTo(uploadFile);
					} catch (IllegalStateException e) {
						System.out.println("Uploaded file loi 2.. ");
						e.printStackTrace();
						continue;
					} catch (IOException e) {
						System.out.println("Uploaded file loi.. ");
						e.printStackTrace();
						continue;
					}
					System.out.println("Uploaded file.. ");
					i++;
				}
			} 
			
			return  ResponseEntity.status(HttpStatus.OK).body("okay");
		}
	
}
