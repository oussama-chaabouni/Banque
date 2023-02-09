package banque.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.azure.storage.blob.BlobClientBuilder;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class AzureBlobAdapter {
	/*
	@Autowired
    BlobClientBuilder client;
	
	public String upload(MultipartFile file, String prefixName) {
        if(file != null && file.getSize() > 0) {
            try {
            	System.out.print(file.getName());
                //implement your own file name logic.
            	
                String fileName = "CROTUN_"+ UUID.randomUUID().toString() +file.getOriginalFilename();
                client.blobName(fileName).buildClient().upload(file.getInputStream(),file.getSize());
                return fileName;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public byte[] getFile(String name) {
        try {
        	byte[] content = client.blobName(name).buildClient().downloadContent().toBytes();
            
            
            return content;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

 */

}
