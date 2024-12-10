package actions;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import model.Aluno;

import java.util.List;
import java.util.Scanner;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class FiltrarListaAluno {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("alunos");
        EntityManager manager = factory.createEntityManager();
        
        boolean continuar = true;
        
        do {
        	System.out.print("Digite a letra ou nome que deseja consultar: ");
    		String letraNome = sc.nextLine();
    		
    		//consultar
            String sql = "SELECT a FROM Aluno a WHERE a.nome LIKE :letraNome";
            Query query = manager.createQuery(sql);
            query.setParameter("letraNome", letraNome + "%"); 

            @SuppressWarnings("unchecked")
            List<Aluno> listaAlunos = query.getResultList();
            
            if (listaAlunos.isEmpty()) {
            	System.out.println("\nNão há registros com essas indicações para consulta!");
			}else {
				
				System.out.println("\nListagem de alunos indicado pelo filtro: "+letraNome);
                
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy"); 
                
                for (Aluno a : listaAlunos) {
                    Calendar dataNascimento = a.getDataNascimento(); 
                    String dataFormatada = (dataNascimento != null) ? dateFormat.format(dataNascimento.getTime()) : "Data não disponível";
                    
                    System.out.println("\nNome: " + a.getNome() + " | CPF: " + a.getCPF() + " | E-mail: " + a.getEmail() +
                                       " | Data de nascimento: " + dataFormatada + " | Naturalidade: " + a.getNaturalidade() + 
                                       " | Endereço: " + a.getEndereco());
                }
			}
            
            //loop
            System.out.print("Deseja realizar uma nova edição? (s/n): ");
            String resposta = sc.nextLine().trim().toLowerCase();
            if (!resposta.equals("s")) {
                continuar = false; 
            }
		} while (continuar);
		
        manager.close();
        factory.close();
        sc.close();
	}

}



