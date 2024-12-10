package actions;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import model.Aluno;

public class ListarAlunos {

	public static void main(String[] args) {
		
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("alunos");
		EntityManager manager = factory.createEntityManager();
		
		String sql = "SELECT a FROM Aluno as a";
		Query consulta = manager.createQuery(sql);
		
		if (consulta!=null) {
			@SuppressWarnings("unchecked")
			List<Aluno> listaAlunos = consulta.getResultList();
			
			System.out.println("\nListagem de alunos cadastrados: \n");
			
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			
			for (Aluno a : listaAlunos) {
				
				 Calendar dataNascimento = a.getDataNascimento(); 
		         String dataNasc = (dataNascimento != null) ? dateFormat.format(dataNascimento.getTime()) : "Data não disponível";
		            
				System.out.println("Nome: "+a.getNome()+" | CPF: "+a.getCPF()+" | E-mail: "+a.getEmail()+" | Data de nascimento: "+ dataNasc +" | Naturalidade: "+a.getNaturalidade()+" | Endereço: "+a.getEndereco());
			}
		}else {
			System.out.println("Não há alunos cadastrados!");
		}
		
		manager.close();
		factory.close();
	}

}
