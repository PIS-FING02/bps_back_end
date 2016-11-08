package com.sarp.managers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;



import com.sarp.classes.BusinessNodeGAFU;
import com.sarp.classes.BusinessSector;
import com.sarp.classes.BusinessSectorRol;
import com.sarp.controllers.AdminActionsController;
import com.sarp.dao.controllers.DAOSectorController;
import com.sarp.dao.factory.DAOServiceFactory;
import com.sarp.facade.GAFUFacade;
import com.sarp.factory.Factory;
import com.sarp.json.modeler.JSONSector;

import uy.gub.bps.apph.wsgafuservice.v001.AreaFuncional;
import uy.gub.bps.apph.wsgafuservice.v001.ErrorNegocio;
import uy.gub.bps.apph.wsgafuservice.v001.ResultObtenerAreasFuncionalesUsuario;



public class GAFUManager {
	
	private  BusinessNodeGAFU arbol;
	private  static GAFUManager instancia;
	    
	private GAFUManager() {
			GAFUFacade gf = GAFUFacade.getInstance();
			this.arbol =  gf.crearArbolGAFU();
	    }

	public void actualizarArbolGAFU() throws Exception{
		Factory fac = Factory.getInstance();
		AdminActionsController adminServiceCtrl = fac.getAdminActionsController();
		DAOServiceFactory factory = DAOServiceFactory.getInstance();
		DAOSectorController sectorDAOCtrl = factory.getDAOSectorController();
		
		GAFUFacade gf = GAFUFacade.getInstance();
		BusinessNodeGAFU nuevo = gf.crearArbolGAFU();
		List<BusinessSector> nuevosSectores = arbolToList(nuevo);
		List<BusinessSector> BDSectores = sectorDAOCtrl.listarSectores();
		List<BusinessSector> aInsertar = arbolToList(nuevo);
		aInsertar.removeAll(BDSectores);
		actualizarSectores(aInsertar, adminServiceCtrl);
		List<BusinessSector> aBorrar = sectorDAOCtrl.listarSectores();
		aBorrar.removeAll(nuevosSectores);
		borrarSectores(aBorrar, adminServiceCtrl);
		this.arbol = nuevo;
	}
	

	private void actualizarSectores(List<BusinessSector> sectores, AdminActionsController ctrl) throws Exception {
		Iterator<BusinessSector> iterator = sectores.iterator();
		while (iterator.hasNext()) {
			ctrl.altaModificacionSector(iterator.next());
		}
	}
	
	private void borrarSectores(List<BusinessSector> sectores, AdminActionsController ctrl) throws Exception {
		Iterator<BusinessSector> iterator = sectores.iterator();
		while (iterator.hasNext()) {
			ctrl.bajaLogicaSector(iterator.next().getSectorId());
		}
	}

	public List<BusinessSector> arbolToList(BusinessNodeGAFU node){
		ArrayList<BusinessNodeGAFU> hijos = node.getHijos();
		Iterator<BusinessNodeGAFU> iterator = hijos.iterator();
		List<BusinessSector> ret = new ArrayList<BusinessSector>();
		BusinessSector sector = new BusinessSector(node.getCodigo(), node.getNombre(), node.obtenerCamino(), node.getHijos() == null || node.getHijos().size() == 0);
		ret.add(sector);
		while (iterator.hasNext()) {
			ret.addAll(arbolToList(iterator.next()));
		}
		return ret;
	}
	
	public static GAFUManager getInstance(){
			if (instancia == null){
				instancia = new GAFUManager();
				return instancia;
			}else{
				return instancia;
			}
		}
		
	public  void imprimirArbol(String appender) {
			imprimirSubArbol(this.arbol," ");
		}
	
	public  void imprimirSubArbol(BusinessNodeGAFU node, String appender) {
		   System.out.print(appender+appender+node.getCodigo());
		   System.out.println(" "+node.getNombre());
		   ArrayList<BusinessNodeGAFU> hijos = node.getHijos();
		   Iterator<BusinessNodeGAFU> iterator = hijos.iterator();
		   while (iterator.hasNext()) {
			    imprimirSubArbol(iterator.next(), appender + appender);
		   }
	}

	public BusinessNodeGAFU BusquedaNodo(String codigo){
		return this.BusquedaNodoAuxiliar(this.arbol, codigo);
	}
	
	private BusinessNodeGAFU BusquedaNodoAuxiliar(BusinessNodeGAFU node, String codigo){
		if(node.getCodigo().equals(codigo)){ 
			return node; 
		}
		else { 
			BusinessNodeGAFU res; 
			List<BusinessNodeGAFU> hijos = node.getHijos();
			Iterator<BusinessNodeGAFU> it = hijos.iterator();
			while (it.hasNext()) {
				res=BusquedaNodoAuxiliar(it.next(),codigo);
				if(res != null){ 
					return res; 
				} 
			} 
			return null;
		}
	}
	
	//Sobrecarga de operador 
	public List<BusinessSectorRol>  obtenerSectorRolesUsuario ( String idUsuario, String rol ) throws Exception {
		//se filtra por rol
		List<BusinessSectorRol> lista = new ArrayList<BusinessSectorRol>();
		List<BusinessSectorRol> listaResultado = new ArrayList<BusinessSectorRol>();
		if (idUsuario==null)
			throw new Exception("Usuario invalido");
		if (rol==null)
			throw new Exception("Rol invalido");
		
		lista = this.obtenerSectorRolesUsuario(idUsuario);
		for (BusinessSectorRol sr : lista ){
			if ( sr.getRol().equals(rol) ){
				listaResultado.add(sr);
			}
		}
		return listaResultado;
	}
	
	public List<BusinessSectorRol>  obtenerSectorRolesUsuario ( String idUsuario ) throws Exception {

		List<BusinessSectorRol> lista = new ArrayList<BusinessSectorRol>();
		GAFUFacade gf = GAFUFacade.getInstance();
		System.out.println("inicialice la lista");
		ResultObtenerAreasFuncionalesUsuario result = gf.obtenerAreasFuncionalesUsuario(idUsuario);
		
		if (result!=null ){		
			List<ErrorNegocio> Errores = result.getErroresNegocio();
			if (!Errores.isEmpty()){
				
				String errorresString ="";
				for (ErrorNegocio e : Errores){
					errorresString= e.getDescripcion() +","; 
				}
				errorresString = errorresString.substring(0,errorresString.length()-1 );//saco la ultima coma 
				System.out.println(errorresString);
				throw new Exception(errorresString);
			}
			else{
				AreaFuncional areaFuncional = new AreaFuncional();
				areaFuncional = result.getAreaFuncional();
				this.ObtenerSectorRol(areaFuncional , lista);
			}
		}else
			throw new Exception("Error de conexion con sistema de usuarios (GAFU)");
		
		return lista;
	}
	
	//Metodo Recursivo para obtenerSectorRolesUsuario
	private void ObtenerSectorRol(  AreaFuncional raiz,  List<BusinessSectorRol> resultado  ) throws Exception{
		if  (raiz!=null) {
			List<AreaFuncional> hijos = raiz.getHijos();
			if ( ( raiz.getRestriccion()!=null ) && ( !"".equals(raiz.getRestriccion()) ) ){
								
				String roles = raiz.getRestriccion();
				roles = roles.substring(9);
				String[] listRoles = roles.split(",");

				AgregarHijos(raiz, resultado, listRoles);
				
			}
			
			
			Iterator<AreaFuncional> it = hijos.iterator();
			while (it.hasNext()){
				ObtenerSectorRol(it.next(),resultado );
			}
		}
	}
	
	private void AgregarHijos(AreaFuncional raiz, List<BusinessSectorRol> resultado,String[] listRoles) throws Exception{
		Factory fac = Factory.getInstance();
		AdminActionsController aac = fac.getAdminActionsController();
		List<JSONSector> sect = aac.listarSectores();
		for (JSONSector sectro : sect){
			//System.out.println(sectro.getEsHoja());
			if ( ( sectro.getRutaSector().contains(raiz.getCodigo()) || sectro.getCodigo().equals(raiz.getCodigo()) ) && sectro.getEsHoja()) {
				String seccod = sectro.getCodigo();
		
				for (int i =0 ; i<listRoles.length; i++){

					BusinessSectorRol secRol = new BusinessSectorRol(seccod,listRoles[i]);
					if ( !resultado.isEmpty()  ){
						if (!resultado.contains(secRol))
								resultado.add(secRol);
					}else
						resultado.add(secRol);

				}
			}
		}
	}
}