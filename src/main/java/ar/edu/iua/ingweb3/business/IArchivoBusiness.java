package ar.edu.iua.ingweb3.business;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import ar.edu.iua.ingweb3.business.impl.util.fs.ArchivoFSNotFoundException;
import ar.edu.iua.ingweb3.model.exception.NotFoundException;

public interface IArchivoBusiness {
	
	public String saveToFS(MultipartFile mf) throws BusinessException;
	public Resource loadFromFS(String nombreArchivo) throws BusinessException, NotFoundException;
	public void deleteFromFS(String nombreArchivo) throws ArchivoFSNotFoundException, NotFoundException, BusinessException;

}
