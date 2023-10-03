import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/*
 ! Requisitos:
  
 ? (Vitor Rebula)
 * 1. Cadastrar partidas que irão acontecer em datas futuras; 

 ? (Thiago Cury)
 * 2. Registrar os placares de partidas que já aconteceram; 
 
 ? (Henrique Fadel) 
 * 3. Manter um registro de toda as partidas, mostrando placar e vencedor de cada confronto; 
 
 ? (Bernardo Elias) 
 * 4. Gerar a tabela de classificação em ordem decrescente de pontos, apontando ainda: 
 * quantos jogos cada equipe fez quantos pontos (vitórias/derrotas) cada equipe "obteve
 * o total de pontos marcados e sofridos por cada equipe nas partidas disputadas 
 
 ? (Caio Souza)
 * 5. Indicar a equipe com o melhor ataque e a equipe com a melhor defesa. 
 */

public class LigasDesportivas {

  public static class Equipe {
    int vitorias;
    int derrotas;
    String nome;

    public Equipe(String nome) {
      this.nome = nome;
      this.vitorias = 0;
      this.derrotas = 0;
    }
  }

  public static class Partida {
    LocalDate data;

    public Partida(LocalDate data) {
      this.data = data;
    }
  }

  public static class Campeonato {
    private List<Equipe> listaEquipes = new ArrayList<>();
    private List<Partida> listaPartidas = new ArrayList<>();

    public void cadastrarEquipe(Equipe equipe) {
      listaEquipes.add(equipe);
    }

    public void cadastrarPartida(Partida partida) {

      listaPartidas.add(partida);
    }

    public void listarEquipes() {
      for (Equipe equipe : listaEquipes) {
        System.out.println(equipe.nome);
      }
      System.out.println("dasda");
    }

    public void listarPartidas() {
      for (Partida partida : listaPartidas) {
        System.out.println(partida.data);
      }
      System.out.println("");
    }

  }

  public static void main(String[] args) {

    // * Declaração de variáveis
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    Campeonato campeonato = new Campeonato();
    Scanner scanner = new Scanner(System.in);
    int opcao;

    System.out.println("Bem vindo ao sistema de ligas desportivas!\n");

    do {

      System.out.println(
          "Digite qual operação deseja realizar:\n" +
              "1 - Cadastrar equipe\n" +
              "2 - Cadastrar partida\n" +
              "3 - Listar equipes\n" +
              "4 - Listar partidas\n" +
              "0 - Sair do sistema\n");

      opcao = scanner.nextInt();
      scanner.nextLine();
      System.out.println("");

      switch (opcao) {
        case 1:
          System.out.println("Cadastrar equipe\n");
          break;

        case 2:
          criarCadastrarPartida(scanner, formatter, campeonato);
          break;

        case 3:
          campeonato.listarEquipes();
          break;

        case 4:
          campeonato.listarPartidas();
          break;

        default:
          System.out.println("Opção inválida\n");
          break;
      }
    } while (opcao != 0);

    scanner.close();
  }

  public static void criarCadastrarPartida(Scanner scanner, DateTimeFormatter formatter, Campeonato campeonato) {
    System.out.print("Digite a data que a partida irá acontecer no formato 'dd/MM/yyyy': ");
    String inputData = scanner.nextLine();
    try {
      LocalDate data = LocalDate.parse(inputData, formatter);
      System.out.println("Data cadastrada: " + data.format(formatter) + "\n");
      Partida partida = new Partida(data);
      campeonato.cadastrarPartida(partida);
    } catch (Exception e) {
      System.out.println("Formato de data inválido. Certifique-se de usar o formato 'dd/MM/yyyy'.\n");
    }
  }
}