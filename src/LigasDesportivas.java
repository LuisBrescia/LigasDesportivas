import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
// import org.junit.Test;

/*
 ! Requisitos:
  
 ? (Vitor Rebula)
 * 1. Cadastrar partidas que irão acontecer em datas futuras; - CONCLUÍDO

 ? (Thiago Cury)
 * 2. Registrar os placares de partidas que já aconteceram; - CONCLUÍDO
 
 ? (Henrique Fadel) 
 * 3. Manter um registro de toda as partidas, mostrando placar e vencedor de cada confronto; - CONCLUÍDO
 
 ? (Bernardo Elias) 
 * 4. Gerar a tabela de classificação em ordem decrescente de pontos, apontando ainda: 
 * quantos jogos cada equipe fez quantos pontos (vitórias/derrotas) cada equipe "obteve
 * o total de pontos marcados e sofridos por cada equipe nas partidas disputadas  - CONCLUÍDO
 
 ? (Caio Souza)
 * 5. Indicar a equipe com o melhor ataque e a equipe com a melhor defesa. - CONCLUÍDO
 */

 /*
  ? Documentação:
  *
  * Classe Equipe:
  *   Atributos:
  *     - nome: String
  *     - vitorias: int
  *     - derrotas: int
  *     - golsMarcados: int
  *     - golsSofridos: int
  *     - pontos: int
  *   Métodos:
  *     - getNome(): String
  *   Construtor:
  *     - Equipe(String nome, int vitorias, int derrotas, int golsMarcados, int golsSofridos, int pontos)
  *
  * Classe Partida:
  *   Atributos:
  *     - data: LocalDate
  *     - equipe1: Equipe
  *     - equipe2: Equipe
  *     - placarEquipe1: int
  *     - placarEquipe2: int
  *   Métodos:
  *     - registrarPlacar(int placarEquipe1, int placarEquipe2): void
  *   Construtor:
  *     - Partida(LocalDate data, Equipe equipe1, Equipe equipe2, int placarEquipe1, int placarEquipe2)
  *
  * Classe Campeonato:
  *   Atributos:
  *     - listaEquipes: List<Equipe>
  *     - listaPartidas: List<Partida>
  *   Métodos:
  *     - cadastrarEquipe(Equipe equipe): void
  *     - cadastrarPartida(Partida partida): void
  *     - equipeCSV(): void
  *     - partidaCSV(): void
  *     - listarEquipes(): void
  *     - listarPartidas(): void
  *     - retornaEquipe(String nomeEquipe): Equipe
  *     - retornaPartida(LocalDate data, String nomeEquipe1, String nomeEquipe2): Partida
  *     - tabelaClassificacao(): void
  *     - melhorAtaqueDefesa(): void
  *   Construtor:
  *     - Campeonato()
  *
  * Classe LigasDesportivas:
  *   Atributos:
  *     - formatter: DateTimeFormatter
  *   Métodos:
  *     - main(String[] args): void
  *     - criarCadastrarEquipe(Scanner scanner, Campeonato campeonato): void
  *     - criarCadastrarPartida(Scanner scanner, Campeonato campeonato): void
  *     - registrarPlacarPartida(Scanner scanner, Campeonato campeonato): void
  */

public class LigasDesportivas {

  public static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

  public static class Equipe {
    int vitorias, derrotas, golsMarcados, golsSofridos, pontos;
    String nome;

    public Equipe(String nome, int vitorias, int derrotas, int golsMarcados, int golsSofridos, int pontos) {
      this.nome = nome;
      this.vitorias = vitorias;
      this.derrotas = derrotas;
      this.golsMarcados = golsMarcados;
      this.golsSofridos = golsSofridos;
      this.pontos = pontos;
    }

    public String getNome() {
      return this.nome;
    }
  }

  public static class Partida {

    LocalDate data;

    Equipe equipe1;
    Equipe equipe2;

    int placarEquipe1;
    int placarEquipe2;

    public Partida(LocalDate data, Equipe equipe1, Equipe equipe2, int placarEquipe1, int placarEquipe2) {
      this.data = data;
      this.equipe1 = equipe1;
      this.equipe2 = equipe2;
      this.placarEquipe1 = placarEquipe1;
      this.placarEquipe2 = placarEquipe2;
    }

    public void registrarPlacar(int placarEquipe1, int placarEquipe2) {
      this.placarEquipe1 = placarEquipe1;
      this.placarEquipe2 = placarEquipe2;

      if (placarEquipe1 > placarEquipe2) {
        equipe1.vitorias++;
        equipe2.derrotas++;
        equipe1.pontos += 2;
        equipe2.pontos += 1;
      } else if (placarEquipe1 < placarEquipe2) {
        equipe2.vitorias++;
        equipe1.derrotas++;
        equipe2.pontos += 2;
        equipe1.pontos += 1;
      } 

      equipe1.golsMarcados += placarEquipe1;
      equipe1.golsSofridos += placarEquipe2;

      equipe2.golsMarcados += placarEquipe2;
      equipe2.golsSofridos += placarEquipe1;

    }
  }

  public static class Campeonato {
    private List<Equipe> listaEquipes = new ArrayList<>();
    private List<Partida> listaPartidas = new ArrayList<>();

    public void cadastrarEquipe(Equipe equipe) {
      listaEquipes.add(equipe);
      listaEquipes.sort((e1, e2) -> e1.nome.compareTo(e2.nome));
      equipeCSV();
    }

    public void cadastrarPartida(Partida partida) {
      listaPartidas.add(partida);
      listaPartidas.sort((p1, p2) -> p1.data.compareTo(p2.data));
      partidaCSV();
    }

    public void equipeCSV() {
      try {
        BufferedWriter writer = new BufferedWriter(new FileWriter("equipes.csv"));
        for (Equipe equipe : listaEquipes) {
          writer.append(equipe.nome + "," + equipe.vitorias + "," + equipe.derrotas + "," + equipe.golsMarcados + ","
              + equipe.golsSofridos + "," + equipe.pontos);

          writer.append("\n");
        }
        writer.close();
      } catch (IOException e) {
        System.out.println("Erro ao salvar a equipe em equipes.csv");
      }
    }

    public void partidaCSV() {
      try {
        BufferedWriter writer = new BufferedWriter(new FileWriter("partidas.csv"));
        for (Partida partida : listaPartidas) {
          writer.append(partida.data.format(formatter) + "," + partida.equipe1.nome + "," + partida.equipe2.nome);
          if (partida.placarEquipe1 != -1 && partida.placarEquipe2 != -1) {
            writer.append("," + partida.placarEquipe1 + "," + partida.placarEquipe2 + "\n");
          } else {
            writer.append("\n");
          }
        }
        writer.close();
      } catch (IOException e) {
        System.out.println("Erro ao salvar a partida em partidas.csv");
      }
    }

    public void listarEquipes() {
      for (Equipe equipe : listaEquipes) {
        System.out.println(equipe.nome);
      }
      System.out.println();
    }

    public void listarPartidas() {
      for (Partida partida : listaPartidas) {
        System.out.println(partida.data.format(formatter) + " - " + partida.equipe1.nome + " x " + partida.equipe2.nome);
        if (partida.placarEquipe1 != -1 && partida.placarEquipe2 != -1) {
          System.out.print("Placar: " + partida.placarEquipe1 + " x " + partida.placarEquipe2);
          if (partida.placarEquipe1 > partida.placarEquipe2) {
            System.out.print(" - " + partida.equipe1.nome + " venceu\n");
          } else if (partida.placarEquipe1 < partida.placarEquipe2) {
            System.out.print(" - " + partida.equipe2.nome + " venceu\n");
          } else {
            System.out.print(" - Empate\n");
          }
        }
      }
      System.out.println();
    }

    public Equipe retornaEquipe(String nomeEquipe) {
      for (Equipe equipe : listaEquipes) {
        if (equipe.nome.equals(nomeEquipe)) {
          return equipe;
        }
      }
      return null;
    }

    public Partida retornaPartida(LocalDate data, String nomeEquipe1, String nomeEquipe2) {
      for (Partida partidaLista : listaPartidas) {
        if (partidaLista.data.equals(data) && partidaLista.equipe1.nome.equals(nomeEquipe1)
            && partidaLista.equipe2.nome.equals(nomeEquipe2)) {
          return partidaLista;
        }
      }
      return null;
    }
    
    public void tabelaClassificacao() {
      listaEquipes.sort((e1, e2) -> e2.pontos - e1.pontos);
      System.out.println("Tabela de classificação:\n");
      for (Equipe equipe : listaEquipes) {
        System.out.println(equipe.nome + " (" + (equipe.vitorias + equipe.derrotas) + " partida ) - " + equipe.pontos + " pontos, com " + equipe.vitorias + " vitórias e " + equipe.derrotas + " derrotas");
      }
      System.out.println();
    }

    public void melhorAtaqueDefesa() {
      listaEquipes.sort((e1, e2) -> e2.golsMarcados - e1.golsMarcados);
      System.out.println("Equipe com melhor ataque: " + listaEquipes.get(0).nome + " (" + listaEquipes.get(0).golsMarcados + " gols marcados)");

      listaEquipes.sort((e1, e2) -> e1.golsSofridos - e2.golsSofridos);
      System.out.println("Equipe com melhor defesa: " + listaEquipes.get(0).nome + " (" + listaEquipes.get(0).golsSofridos + " gols sofridos)\n");
    }
 
  }

  public static void main(String[] args) {

    // * Declaração de variáveis
    Campeonato campeonato = new Campeonato();
    Scanner scanner = new Scanner(System.in);
    int opcao;

    // * Carregar equipes do arquivo equipes.csv
    try {
      Scanner scannerEquipes = new Scanner(new java.io.File("equipes.csv"));
      while (scannerEquipes.hasNextLine()) {
        String[] dadosEquipe = scannerEquipes.nextLine().split(",");
        
        Equipe equipe = new Equipe(dadosEquipe[0], Integer.parseInt(dadosEquipe[1]), Integer.parseInt(dadosEquipe[2]),
            Integer.parseInt(dadosEquipe[3]), Integer.parseInt(dadosEquipe[4]), Integer.parseInt(dadosEquipe[5]));

        campeonato.listaEquipes.add(equipe);
      }
      scannerEquipes.close();
    } catch (Exception e) {
      System.out.println("Erro ao carregar equipes do arquivo equipes.csv");
    }

    // * Carregar partidas do arquivo partidas.csv
    try {
      Scanner scannerPartidas = new Scanner(new java.io.File("partidas.csv"));
      while (scannerPartidas.hasNextLine()) {
        String[] dadosPartida = scannerPartidas.nextLine().split(",");
        LocalDate data = LocalDate.parse(dadosPartida[0], formatter);
        Equipe equipe1 = campeonato.retornaEquipe(dadosPartida[1]);
        Equipe equipe2 = campeonato.retornaEquipe(dadosPartida[2]);

        int placarEquipe1 = -1, placarEquipe2 = -1;

        if (dadosPartida.length == 5) {
          placarEquipe1 = Integer.parseInt(dadosPartida[3]);
          placarEquipe2 = Integer.parseInt(dadosPartida[4]);
        } 

        Partida partida = new Partida(data, equipe1, equipe2, placarEquipe1, placarEquipe2);
        campeonato.listaPartidas.add(partida);
      }
      scannerPartidas.close();
    } catch (Exception e) {
      System.out.println("Erro ao carregar partidas do arquivo partidas.csv");
    }
    
    System.out.println("\nBem vindo ao sistema de ligas desportivas!\n");

    do {

      System.out.println(
          "Digite qual operação deseja realizar:\n" +
              "1 - Cadastrar equipe\n" +
              "2 - Cadastrar partida\n" +
              "3 - Listar equipes\n" +
              "4 - Listar partidas\n" +
              "5 - Registrar placar de partida\n" +
              "6 - Tabela de classificação\n" +
              "7 - Equipe com melhor ataque e melhor defesa\n" +
              "0 - Sair do sistema\n\n");

      opcao = scanner.nextInt();
      scanner.nextLine();
      System.out.println("");

      switch (opcao) {
        case 1:
          criarCadastrarEquipe(scanner, campeonato);
          break;

        case 2:
          criarCadastrarPartida(scanner, campeonato);
          break;

        case 3:
          System.out.println("Equipes cadastradas:\n");
          campeonato.listarEquipes();
          break;

        case 4:
          System.out.println("Partidas cadastradas:\n");
          campeonato.listarPartidas();
          break;

        case 5:
          registrarPlacarPartida(scanner, campeonato);
          break;
        
        case 6:
          campeonato.tabelaClassificacao();
          break;

        case 7:
          campeonato.melhorAtaqueDefesa();
          break;

        case 0:
          System.out.println("Saindo do sistema...\n");
          break;

        default:
          System.out.println("Opção inválida\n");
          break;
      }
    } while (opcao != 0);

    scanner.close();
  }

  public static void criarCadastrarEquipe(Scanner scanner, Campeonato campeonato) {
    System.out.print("Digite o nome da equipe que deseja cadastrar: ");
    String nomeEquipe = scanner.nextLine();

    if (campeonato.retornaEquipe(nomeEquipe) == null) {
      Equipe equipe = new Equipe(nomeEquipe, 0, 0, 0, 0, 0);
      campeonato.cadastrarEquipe(equipe);
      System.out.println("Equipe cadastrada com sucesso!\n");
    } else {
      System.out.println("Já existe uma equipe cadastrada com esse nome\n");
    }
  }

  public static void criarCadastrarPartida(Scanner scanner, Campeonato campeonato) {

    String nomeEquipe1;
    String nomeEquipe2;

    do {
      System.out.print("Digite o nome da primeira equipe: ");
      nomeEquipe1 = scanner.nextLine();

      if (campeonato.retornaEquipe(nomeEquipe1) == null) {
        System.out.println("Não existe uma equipe cadastrada com esse nome\n");
      }

    } while (campeonato.retornaEquipe(nomeEquipe1) == null);

    do {
      System.out.print("Digite o nome da segunda equipe: ");
      nomeEquipe2 = scanner.nextLine();

      if (nomeEquipe1.equals(nomeEquipe2)) {
        System.out.println("Você não pode cadastrar uma partida entre a mesma equipe\n");
      } else if (campeonato.retornaEquipe(nomeEquipe2) == null) {
        System.out.println("Não existe uma equipe cadastrada com esse nome\n");
      }

    } while (campeonato.retornaEquipe(nomeEquipe2) == null);

    Equipe equipe1 = campeonato.retornaEquipe(nomeEquipe1);
    Equipe equipe2 = campeonato.retornaEquipe(nomeEquipe2);

    System.out.print("Digite a data que a partida irá acontecer no formato 'dd/MM/yyyy': ");
    String inputData = scanner.nextLine();

    try {
      LocalDate data = LocalDate.parse(inputData, formatter);
      System.out.println("Data cadastrada: " + data.format(formatter) + "\n");
      Partida partida = new Partida(data, equipe1, equipe2, -1, -1);

      if (campeonato.retornaPartida(data, nomeEquipe1, nomeEquipe2) == null) {
        campeonato.cadastrarPartida(partida);
      } else {
        System.out.println("Já existe uma partida cadastrada com essas equipes e nessa data\n");
      }

    } catch (Exception e) {
      System.out.println("Formato de data inválido. Certifique-se de usar o formato 'dd/MM/yyyy'.\n");
    }

  }

  public static void registrarPlacarPartida(Scanner scanner, Campeonato campeonato) {
    System.out.print("Digite o nome da primeira equipe: ");
    String nomeEquipe1 = scanner.nextLine();

    System.out.print("Digite o nome da segunda equipe: ");
    String nomeEquipe2 = scanner.nextLine();

    System.out.print("Digite a data que a partida aconteceu 'dd/MM/yyyy': ");
    String inputData = scanner.nextLine();

    LocalDate data = LocalDate.parse(inputData, formatter);

    Partida partida = campeonato.retornaPartida(data, nomeEquipe1, nomeEquipe2);

    System.out.print("Digite o placar da primeira equipe: ");
    int placarEquipe1 = scanner.nextInt();

    System.out.print("Digite o placar da segunda equipe: ");
    int placarEquipe2 = scanner.nextInt();

    partida.registrarPlacar(placarEquipe1, placarEquipe2);

    campeonato.partidaCSV();
    campeonato.equipeCSV();

    System.out.println("Placar registrado com sucesso!\n");
  }
}