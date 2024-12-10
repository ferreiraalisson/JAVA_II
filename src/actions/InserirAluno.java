package actions;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import model.Aluno;

public class InserirAluno {

	public static void main(String[] args) {
		
        Scanner sc = new Scanner(System.in);
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("alunos");
        EntityManager manager = factory.createEntityManager();
        Aluno stu = new Aluno();
        
        boolean continuar = true;
        
        do {
        	
        	 System.out.print("Digite o nome do aluno: ");
             stu.setNome(sc.nextLine()); 
             
             System.out.print("Digite o e-mail do aluno: ");
             stu.setEmail(sc.nextLine());
             
             
             System.out.print("Informe o CPF: ");
             stu.setCPF(sc.nextLine());;
                      
             System.out.print("Digite a data de nascimento (dd/MM/yyyy): ");
             String dataStr = sc.nextLine();
             SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	             try {
	                 java.util.Date date = sdf.parse(dataStr);
	                 Calendar dataNascimento = Calendar.getInstance();
	                 dataNascimento.setTime(date);
	                 stu.setDataNascimento(dataNascimento);
	             } catch (ParseException e) {
	             	System.out.println("Erro ao registrar data de nascimento");
	                 e.printStackTrace();
	             }
             
             System.out.print("Digite a naturalidade do aluno: ");
             stu.setNaturalidade(sc.nextLine());
             
             System.out.print("Digite e endereço do aluno: ");
             stu.setEndereco(sc.nextLine());
             
             //inserção 
             manager.getTransaction().begin();
             manager.persist(stu);
             manager.getTransaction().commit();
             
             System.out.println("Aluno inserido com sucesso! Segue o ID correspondente: "+stu.getId());
             
             //loop
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
