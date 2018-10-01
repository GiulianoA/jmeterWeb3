package ar.edu.iua.ingweb3.business.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import ar.edu.iua.ingweb3.business.BusinessException;
import ar.edu.iua.ingweb3.business.IArchivoBusiness;
import ar.edu.iua.ingweb3.business.impl.util.fs.ArchivoFSNotFoundException;
import ar.edu.iua.ingweb3.business.impl.util.fs.ArchivoFSService;
import ar.edu.iua.ingweb3.model.exception.NotFoundException;

@Service
public class ArchivoBusiness implements IArchivoBusiness {

	@Autowired
	private ArchivoFSService archivoFSService;

	@Override
	public String saveToFS(MultipartFile mf) throws BusinessException {
		return archivoFSService.almacenarArchivo(mf);
	}

	@Override
	public Resource loadFromFS(String nombreArchivo) throws BusinessException, NotFoundException {
		try {
			return archivoFSService.cargarArchivo(nombreArchivo);

		} catch (ArchivoFSNotFoundException e) {
			throw new NotFoundException(e);
		}
	}

	@Override
	public void deleteFromFS(String nombreArchivo) throws NotFoundException{
		try {
			archivoFSService.eliminarArchivo(nombreArchivo);
		} catch (ArchivoFSNotFoundException e) {
			throw new NotFoundException(e);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
