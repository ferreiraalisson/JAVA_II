package actions;

import java.util.Scanner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import model.Aluno;

public class RemoverAluno {

	public static void main(String[] args) {
		
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("alunos");
		EntityManager manager = factory.createEntityManager();
		Scanner sc = new Scanner(System.in);
		
		boolean continuar = true; 
		
		do {
			System.out.print("Informe o ID do aluno que deseja remover: ");
			int capId = sc.nextInt();
			sc.nextLine();
			
			Aluno student = new Aluno();
			student.setId(capId);
			student = manager.find(Aluno.class, student.getId());
			
			if (student != null) {
				
				manager.getTransaction().begin();
				manager.remove(student);
				manager.getTransaction().commit();
				System.out.println("Aluno removido com sucesso!");
				
			}else {
				System.out.println("O ID informado não existe no banco!");
			}
					
			System.out.print("Deseja realizar uma nova edição? (s/n): ");
            String resposta = sc.nextLine().trim().toLowerCase();
            if (!resposta.equals("s")) {
                continuar = false; 
            }
		} while (continuar);
		
		sc.close();
		manager.close();
		factory.close();
		
		
	}

}
