package com.sarp.test.adminService;

import java.util.List;

import com.sarp.classes.BusinessSectorRol;
import com.sarp.controllers.GAFUController;
import com.sarp.factory.Factory;

public class TestPermisos {

	public static void main(String[] args) {
		
		Factory fac = Factory.getInstance();
		GAFUController GafuCont = fac.GAFUController();
		try{
			List<BusinessSectorRol> result = GafuCont.obtenerSectorRolesUsuario("respsec");
			for (BusinessSectorRol sr : result ){
				System.out.println(sr.toString());
			}
		}catch (Exception e) {
			System.out.println("Exploto "+e.getMessage());
		}
		
		
	}

}
