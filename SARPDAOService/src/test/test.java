package test;

import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.sarp.classes.BusinessDisplay;
import com.sarp.classes.BusinessSector;
import com.sarp.dao.controllers.DAODisplayController;
import com.sarp.dao.controllers.DAONumeroController;
import com.sarp.dao.controllers.DAOSectorController;
import com.sarp.dao.factory.DAOServiceFactory;




public class test {
	public static void main(String[] args){
		//crearDisplay();
		//listarDisplays();
		//modificarDisplay(8, "otraruta");
		//eliminarDisplay(3);
		//crearSector(3, "nombre");
		//asociarSectorDisplay(6,4);
		//getSector(6);
		//listarSector();
		//modificarSector(2, "otronombre", "otraruta");
		//crearNumero(2,2,"numeroooooo2", 3);
		obtenerNumero(2);
		System.out.println("FIN");
	}
	
	/*public static void crearDisplay(){
		DAODisplayController ctrl = DAOServiceFactory.getInstance().getDAODisplayController();
		BusinessDisplay d = new BusinessDisplay();
		d.setRutaArchivo("rutaaaa");
		ctrl.crearDisplay(d);
	}
	
	public static void listarDisplays(){
		System.out.println("1");
		DisplayRepository repo = new DisplayRepository();
		System.out.println("2");
		List<Display> lista = repo.selectDisplays();
	}
	
	public static void modificarDisplay(int codigo, String rutaArchivo){
		System.out.println("1");
		DisplayRepository repo = new DisplayRepository();
		System.out.println("2");
		try {
			//repo.updateDisplay(codigo, rutaArchivo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
	}
	
	public static void eliminarDisplay(int codigo){
		System.out.println("1");
		DisplayRepository repo = new DisplayRepository();
		System.out.println("2");
		try {
			repo.deleteDisplay(codigo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
	}

	public static void crearSector(Integer codigo, String nom){
		DAOSectorController ctrl = DAOServiceFactory.getInstance().getDAOSectorController();
		//BusinessSector s = new BusinessSector(c, "sector" + c);
		try {
			ctrl.crearSector(codigo, nom);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void asociarSectorDisplay(int s, int d){
		System.out.println("1");
		SectorRepository repo = new SectorRepository();
		System.out.println("2");
		try {
			repo.asignarDisplay(s, d);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void listarSector(){
		System.out.println("1");
		SectorRepository repo = new SectorRepository();
		System.out.println("2");
		List<Sector> lista = repo.selectSectores();
	}

	public static void modificarSector(int codigo, String nombre, String rutaSector){
		System.out.println("1");
		SectorRepository repo = new SectorRepository();
		System.out.println("2");
		try {
			repo.updateSector(codigo, nombre, rutaSector);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void getSector(int c){
		System.out.println("1");
		SectorRepository repo = new SectorRepository();
		System.out.println("2");
		try {
			Sector s = repo.selectSector(c);
			System.out.println("3");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/
	public static void crearNumero(int sec, int tram, String nombre, Integer codigo){
		DAONumeroController ctrl = DAOServiceFactory.getInstance().getDAONumeroController();
		//BusinessSector s = new BusinessSector(c, "sector" + c);
		try {
			ctrl.crearNumero(sec, tram, nombre, codigo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void obtenerNumero(Integer codigo){
		DAONumeroController ctrl = DAOServiceFactory.getInstance().getDAONumeroController();
		//BusinessSector s = new BusinessSector(c, "sector" + c);
		try {
			ctrl.existeNumero(codigo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
