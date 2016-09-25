package com.sarp.dao.controllers;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.sarp.classes.BusinessDatoComplementario;
import com.sarp.classes.BusinessNumero;
import com.sarp.classes.BusinessTramite;
import com.sarp.dao.factory.DAOFactory;
import com.sarp.dao.model.DatosComplementario;
import com.sarp.dao.model.Numero;
import com.sarp.dao.model.Sector;
import com.sarp.dao.model.Tramite;
import com.sarp.dao.repository.DAONumero;
import com.sarp.dao.repository.DAOSector;
import com.sarp.dao.repository.DAOTramite;
import com.sun.corba.se.impl.orbutil.RepositoryIdFactory;

//import org.dao.repository.NumeroRepository;

public class DAONumeroController {
	
	public void crearNumero(BusinessNumero numero, Integer sector) throws Exception{
		DAOFactory factory = DAOFactory.getInstance();
		DAONumero numeroRepository = factory.getNumeroRepository();
		if(!numeroRepository.existsNumero(numero.getInternalId())){
			DAOSector sectorRepository = factory.getSectorRepository();		
			Sector s = sectorRepository.selectSector(sector);
			if(s != null){
				List<Tramite> tList = s.getTramites();
				boolean existe = false;
				for (Tramite t:tList){
					if (t.getCodigo() == numero.getCodigoTramite()){
						BusinessDatoComplementario dc = numero.getDatoComplementario();
						numeroRepository.insertNumero(t, numero.isEsSAE(), numero.getInternalId(), numero.getExternalId(), numero.getHora(), numero.getPrioridad(), numero.getEstado(), dc.getDocIdentidad(), dc.getNombreCompleto(), dc.getTipoDoc());
						existe = true;
					}
				}
				if (!existe) {
					throw new Exception("El sector no contiene al tramite con el codigo solicitado");
				}			
			}else{
				throw new Exception("No se encontro el sector solicitado");
			}
		}
		else{
			throw new Exception("Ya existe un n�mero con ese ID");
		}
	}

	public List<BusinessNumero> listarNumeros(){
		DAOFactory factory = DAOFactory.getInstance();
		DAONumero numeroRepository = factory.getNumeroRepository();
		
		List<Numero> list = numeroRepository.selectNumeros();
		List<BusinessNumero> ret = new LinkedList<BusinessNumero>();
		for (Numero n : list){
			DatosComplementario d = n.getDatosComplementario();
			BusinessDatoComplementario dc = new BusinessDatoComplementario(d.getDocIdentidad(), d.getNombreCompleto(), d.getTipoDoc());
			BusinessNumero numero = new BusinessNumero(n.getInternalId(),n.getTramite().getCodigo(),n.getExternalId(),n.getHora(),n.getEstado(),n.getEsSae(),n.getPrioridad(),dc);
			ret.add(numero);
		}
		return ret;
	}
	
	public BusinessNumero obtenerNumero(int id) throws Exception{
		DAOFactory factory = DAOFactory.getInstance();
		DAONumero numeroRepository = factory.getNumeroRepository();
		
		Numero n = numeroRepository.selectNumero(id);
		DatosComplementario d = n.getDatosComplementario();
		BusinessDatoComplementario dc = new BusinessDatoComplementario(d.getDocIdentidad(), d.getNombreCompleto(), d.getTipoDoc());
		BusinessNumero numero = new BusinessNumero(n.getInternalId(),n.getTramite().getCodigo(),n.getExternalId(),n.getHora(),n.getEstado(),n.getEsSae(),n.getPrioridad(),dc);
		return numero;
	}
	
	public void modificarNumero(BusinessNumero numero) throws Exception{
		DAOFactory factory = DAOFactory.getInstance();
		DAONumero numeroRepository = factory.getNumeroRepository();
		
		numeroRepository.updateNumero(numero.getInternalId(),numero.getEstado(),numero.getExternalId(),numero.getHora(),numero.getPrioridad(),numero.isEsSAE());
	}
	
	public void eliminarNumero(int codigo) throws Exception{
		DAOFactory factory = DAOFactory.getInstance();
		DAONumero numeroRepository = factory.getNumeroRepository();
		
		numeroRepository.deleteNumero(codigo);
	}

	public LinkedList<BusinessNumero> obtenerNumerosDelDia() {
		// Hay que hacer una consulta que trae todos los numeros que fueron reservados
		// por SAE para el dia en el cual se ejecuta.
		return null;
	}

}