package ar.edu.iua.ingweb3.business.impl.util.fs;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ArchivoFSService {

	private final Path localizacionAlmacenamiento;

	@Autowired
	public ArchivoFSService(ArchivoFSProperties prop) {

		this.localizacionAlmacenamiento = Paths.get(prop.getDirectorioAlmacenamiento()).toAbsolutePath().normalize();
		try {
			Files.createDirectories(this.localizacionAlmacenamiento);
		} catch (IOException e) {
			throw new ArchivoFSException(
					"No se ha podido crear el directorio para almacenar archivos: " + this.localizacionAlmacenamiento,
					e);
		}
	}

	public String almacenarArchivo(MultipartFile file) throws ArchivoFSException {
		String nombreArchivo = StringUtils.cleanPath(file.getOriginalFilename());
		Path targetLocation = this.localizacionAlmacenamiento.resolve(nombreArchivo);
		try {
			Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			throw new ArchivoFSException("No se pudo almacenar el archivo " + nombreArchivo, e);
		}
		return nombreArchivo;
	}
	
	public Resource cargarArchivo(String nombreArchivo) {
		Path path=this.localizacionAlmacenamiento.resolve(nombreArchivo).normalize();
		try {
			Resource resource = new UrlResource(path.toUri());
			if(resource.exists()) {
				return resource;
			} else {
				throw new ArchivoFSNotFoundException("Archivo no encontrado: "+nombreArchivo);
			}
		} catch (MalformedURLException e) {
			throw new ArchivoFSNotFoundException("Archivo no encontrado: "+nombreArchivo);
		}
	}
	
	public void eliminarArchivo(String nombreArchivo) throws ArchivoFSNotFoundException {
		Path path=this.localizacionAlmacenamiento.resolve(nombreArchivo).normalize();
		try {
			Resource resource = new UrlResource(path.toUri());
			if(resource.exists()) {
				resource.getFile().delete();
				/*
				Logger log =  LoggerFactory.getLogger(ArchivoFSService.class);
				log.info("File deleted: " + deleted);
				*/	
			} else {
				throw new ArchivoFSNotFoundException("Archivo no encontrado: "+nombreArchivo);
			}
		} catch (MalformedURLException e) {
			throw new ArchivoFSNotFoundException("Archivo no encontrado: "+nombreArchivo);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
