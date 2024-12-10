package actions;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Scanner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import model.Aluno;

public class EditarAluno {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("alunos");
        EntityManager manager = factory.createEntityManager();
        
        boolean continuarEditando = true; 
        
        do {
            System.out.print("Informe qual ID deseja alterar: ");
            int capId = sc.nextInt();
            sc.nextLine(); 
            
            Aluno student = new Aluno();
    		student.setId(capId);
            student = manager.find(Aluno.class, capId);
            
            if (student != null) {
            	
                Aluno updateAluno = student; 
                
                System.out.println("Qual dos campos deseja editar? \n1- Nome \n2- CPF \n3- E-mail \n4- Data de Nascimento \n5- Naturalidade \n6- Endereço");
                int opcao = sc.nextInt();
                sc.nextLine(); 
                
                switch (opcao) {
                    case 1:
                        System.out.print("Informe o novo nome: ");
                        updateAluno.setNome(sc.nextLine());
                        break;
                    case 2:
                        System.out.print("Informe o novo CPF: ");
                        updateAluno.setCPF(sc.nextLine());
                        break;
                    case 3:
                        System.out.print("Informe o novo E-mail: ");
                        updateAluno.setEmail(sc.nextLine());
                        break;
                    case 4:
                        System.out.print("Informe a atualização da data (dd/MM/yyyy): ");
                        String dataStr = sc.nextLine();
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                        
                        try {
                            java.util.Date date = sdf.parse(dataStr);
                            Calendar dataNascimento = Calendar.getInstance();
                            dataNascimento.setTime(date);
                            updateAluno.setDataNascimento(dataNascimento);
                        } catch (ParseException e) {
                            System.out.println("Erro ao registrar data de nascimento");
                            e.printStackTrace();
                            return; // Sai do método em caso de erro
                        }
                        break;
                    case 5:
                        System.out.print("Informe a nova naturalidade: ");
                        updateAluno.setNaturalidade(sc.nextLine());
                        break;
                    case 6:
                        System.out.print("Informe o novo endereço: ");
                        updateAluno.setEndereco(sc.nextLine());
                        break;
                    default:
                        System.out.println("Opção inválida");
                        return; // Sai do método se a opção for inválida
                }
                
                manager.getTransaction().begin();
                manager.merge(updateAluno);
                manager.getTransaction().commit();
                
                System.out.println("\nDados do aluno atualizados do ID: " + capId);
                
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                
                String sql = "SELECT a FROM Aluno a WHERE a.id = :idIndicado";
                Query query = manager.createQuery(sql);
                query.setParameter("idIndicado", capId);
                
                
                @SuppressWarnings("unchecked")
                List<Aluno> listaAlunos = query.getResultList();
                for (Aluno a : listaAlunos) {
                    Calendar dataNascimento = a.getDataNascimento();
                    String dataFormatada = (dataNascimento != null) ? dateFormat.format(dataNascimento.getTime()) : "Data não disponível";
                    
                    System.out.println("Nome: " + a.getNome() + " | CPF: " + a.getCPF() + " | E-mail: " + a.getEmail() +
                                       " | Data de nascimento: " + dataFormatada + " | Naturalidade: " + a.getNaturalidade() + 
                                       " | Endereço: " + a.getEndereco());
                }
            } else {
                System.out.println("O ID informado não existe no banco!");
            }
            
            
            System.out.print("Deseja realizar uma nova edição? (s/n): ");
            String resposta = sc.nextLine().trim().toLowerCase();
            if (!resposta.equals("s")) {
                continuarEditando = false; 
            }
            
        } while (continuarEditando);
        
        sc.close();
        manager.close();
        factory.close();
    }
}
