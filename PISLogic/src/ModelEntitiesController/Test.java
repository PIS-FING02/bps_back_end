package src.ModelEntitiesController;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import src.DAOService.Funcionario;
import src.DAOService.Puesto;
import src.ModelEntitiesController.FuncionarioService;
import src.ModelEntitiesController.PuestoService;

public class Test {
     private static EntityManagerFactory factory;

     public static void main(String[] args) {
          factory = Persistence.createEntityManagerFactory("postgresUnit");
          EntityManager em = factory.createEntityManager();               
          FuncionarioService funcionarioService = new FuncionarioService();
          PuestoService puestoService = new PuestoService(em);      
          em.getTransaction().begin();
          
        /*  funcionarioService.crearFuncionario("facevedo", "sd", "ssd", "123" , "sdfds@gmail.com");
          puestoService.crearPuesto(3, "estado254");
       */
          Funcionario f = funcionarioService.obtenerFuncionario("mtaboas");
          Funcionario f2 = funcionarioService.obtenerFuncionario("mverdugo");
          puestoService.asignarFuncionario(1, f);
          puestoService.asignarFuncionario(2, f);
          puestoService.asignarFuncionario(3, f2);
          
          
          Puesto p = puestoService.obtenerPuesto(1);
          List<Puesto> lista = f.getPuestos();
          for(Puesto p2 : lista){
        	  System.out.println(p2.getEstado());
          }
          
          em.getTransaction().commit();
          System.out.println("FIN");
          
     }
}