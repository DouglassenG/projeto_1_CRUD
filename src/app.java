// App.java está no pacote padrão (sem "package") — é a classe principal do projeto

import br.com.dmichelini.dao.ClienteMapDAO; // Importa a implementação concreta
import br.com.dmichelini.dao.IClienteDAO;   // Importa a interface (contrato)
import domain.Cliente;                       // Importa a entidade Cliente

import javax.swing.JOptionPane; // JOptionPane: classe do Java que exibe janelas de diálogo
import java.util.Collection;    // Para receber a lista de clientes

/**
 * CLASSE PRINCIPAL — App.java
 *
 * Esta é a classe de ENTRADA do sistema.
 * O método main() é o ponto de início — o Java sempre começa por aqui.
 *
 * Arquitetura do projeto:
 *
 *   App.java (interface com usuário via JOptionPane)
 *       ↓ usa
 *   IClienteDAO (contrato/interface)
 *       ↓ implementado por
 *   ClienteMapDAO (lógica de armazenamento em memória)
 *       ↓ armazena
 *   Cliente (objeto de dados)
 *
 * Isso é o padrão DAO (Data Access Object):
 * separa a lógica de negócio do acesso aos dados.
 */
public class App {

    public static void main(String[] args) {

        // =====================================================
        // INVERSÃO DE DEPENDÊNCIA:
        // Declaramos com o TIPO DA INTERFACE (IClienteDAO)
        // mas instanciamos com a IMPLEMENTAÇÃO (ClienteMapDAO).
        //
        // Vantagem: se amanhã trocar para banco de dados,
        // só muda esta linha — o restante do App não muda.
        // =====================================================
        IClienteDAO dao = new ClienteMapDAO();

        // Loop principal do menu — continua enquanto o usuário não sair
        boolean rodando = true;

        while (rodando) {

            // JOptionPane.showInputDialog: abre uma janela pedindo uma entrada de texto
            String input = JOptionPane.showInputDialog(
                null,                        // componente pai (null = centraliza na tela)
                "╔══════════════════════════╗\n" +
                "║   SISTEMA DE CLIENTES    ║\n" +
                "╠══════════════════════════╣\n" +
                "║  1 - Cadastrar cliente   ║\n" +
                "║  2 - Consultar cliente   ║\n" +
                "║  3 - Alterar cliente     ║\n" +
                "║  4 - Excluir cliente     ║\n" +
                "║  5 - Listar todos        ║\n" +
                "║  0 - Sair                ║\n" +
                "╚══════════════════════════╝\n\n" +
                "Digite a opção:",
                "Menu Principal",             // título da janela
                JOptionPane.PLAIN_MESSAGE     // tipo de ícone (sem ícone)
            );

            // Se o usuário clicou em "Cancelar" ou fechou a janela, input = null
            if (input == null) {
                rodando = false;
                continue;
            }

            // Converte a String digitada para inteiro com segurança
            int opcao;
            try {
                opcao = Integer.parseInt(input.trim());
            } catch (NumberFormatException e) {
                // NumberFormatException: lançado quando tenta converter texto inválido para número
                JOptionPane.showMessageDialog(null,
                    "Digite apenas um número válido!",
                    "Entrada inválida",
                    JOptionPane.WARNING_MESSAGE); // Ícone de atenção (triângulo amarelo)
                continue; // Volta para o início do while
            }

            // =====================================================
            // SWITCH: estrutura de decisão múltipla
            // Mais limpo que vários if/else quando há muitos casos
            // =====================================================
            switch (opcao) {
                case 1: cadastrar(dao);  break;
                case 2: consultar(dao);  break;
                case 3: alterar(dao);    break;
                case 4: excluir(dao);    break;
                case 5: listarTodos(dao); break;
                case 0:
                    JOptionPane.showMessageDialog(null,
                        "Sistema encerrado.\nAté logo!",
                        "Saindo",
                        JOptionPane.INFORMATION_MESSAGE); // Ícone de informação (i azul)
                    rodando = false;
                    break;
                default:
                    JOptionPane.showMessageDialog(null,
                        "Opção inválida! Escolha entre 0 e 5.",
                        "Erro",
                        JOptionPane.ERROR_MESSAGE); // Ícone de erro (X vermelho)
            }
        }
    }

    // =========================================================
    // MÉTODO PRIVADO: cadastrar
    //
    // "private static" = só esta classe pode chamar, e é estático
    // (não precisa de um objeto App criado para chamar).
    //
    // Recebe o DAO como parâmetro para não depender de variável global.
    // =========================================================
    private static void cadastrar(IClienteDAO dao) {

        try {
            // Coleta cada dado do cliente via JOptionPane
            String nome = JOptionPane.showInputDialog(null, "Nome completo:", "Cadastrar Cliente", JOptionPane.PLAIN_MESSAGE);
            if (nome == null || nome.trim().isEmpty()) return; // Usuário cancelou ou não digitou nada

            String cpf = JOptionPane.showInputDialog(null, "CPF (somente números):", "Cadastrar Cliente", JOptionPane.PLAIN_MESSAGE);
            if (cpf == null || cpf.trim().isEmpty()) return;

            String tel = JOptionPane.showInputDialog(null, "Telefone (somente números):", "Cadastrar Cliente", JOptionPane.PLAIN_MESSAGE);
            if (tel == null || tel.trim().isEmpty()) return;

            String end = JOptionPane.showInputDialog(null, "Endereço (rua/av.):", "Cadastrar Cliente", JOptionPane.PLAIN_MESSAGE);
            if (end == null || end.trim().isEmpty()) return;

            String numeroStr = JOptionPane.showInputDialog(null, "Número do endereço:", "Cadastrar Cliente", JOptionPane.PLAIN_MESSAGE);
            if (numeroStr == null || numeroStr.trim().isEmpty()) return;

            String cidade = JOptionPane.showInputDialog(null, "Cidade:", "Cadastrar Cliente", JOptionPane.PLAIN_MESSAGE);
            if (cidade == null || cidade.trim().isEmpty()) return;

            String estado = JOptionPane.showInputDialog(null, "Estado (sigla, ex: RS):", "Cadastrar Cliente", JOptionPane.PLAIN_MESSAGE);
            if (estado == null || estado.trim().isEmpty()) return;

            // Converte número do endereço de String para Integer
            Integer numero = Integer.valueOf(numeroStr.trim());

            // Cria o objeto Cliente usando o construtor (instanciação)
            Cliente cliente = new Cliente(nome, cpf, tel, end, numero, cidade, estado);

            // Envia para o DAO cadastrar e verifica o retorno
            boolean sucesso = dao.cadastrar(cliente);

            if (sucesso) {
                JOptionPane.showMessageDialog(null,
                    "Cliente cadastrado com sucesso!\n\n" + cliente.toString(),
                    "Cadastro realizado",
                    JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null,
                    "CPF já cadastrado!\nUm cliente com este CPF já existe no sistema.",
                    "CPF duplicado",
                    JOptionPane.WARNING_MESSAGE);
            }

        } catch (NumberFormatException e) {
            // Disparado se CPF, telefone ou número do endereço conter letras
            JOptionPane.showMessageDialog(null,
                "Erro nos dados!\nCPF, telefone e número devem conter apenas dígitos.",
                "Dado inválido",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    // =========================================================
    // MÉTODO PRIVADO: consultar
    // READ por CPF — mostra os dados de um cliente específico
    // =========================================================
    private static void consultar(IClienteDAO dao) {

        String cpfStr = JOptionPane.showInputDialog(null,
            "Digite o CPF do cliente (somente números):",
            "Consultar Cliente",
            JOptionPane.PLAIN_MESSAGE);

        if (cpfStr == null || cpfStr.trim().isEmpty()) return;

        try {
            long cpf = Long.parseLong(cpfStr.trim()); // CPF é Long (número grande)
            Cliente cliente = dao.consultar(cpf);      // Busca no DAO

            if (cliente != null) {
                // toString() do Cliente é chamado automaticamente aqui
                JOptionPane.showMessageDialog(null,
                    "Cliente encontrado:\n\n" + cliente.toString(),
                    "Resultado da Consulta",
                    JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null,
                    "Nenhum cliente encontrado com CPF: " + cpfStr,
                    "Não encontrado",
                    JOptionPane.WARNING_MESSAGE);
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null,
                "CPF inválido! Digite somente números.",
                "Erro",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    // =========================================================
    // MÉTODO PRIVADO: alterar
    // UPDATE — atualiza dados de um cliente existente
    // =========================================================
    private static void alterar(IClienteDAO dao) {

        String cpfStr = JOptionPane.showInputDialog(null,
            "Digite o CPF do cliente a alterar:",
            "Alterar Cliente",
            JOptionPane.PLAIN_MESSAGE);

        if (cpfStr == null || cpfStr.trim().isEmpty()) return;

        try {
            long cpf = Long.parseLong(cpfStr.trim());
            Cliente existente = dao.consultar(cpf); // Verifica se existe antes de alterar

            if (existente == null) {
                JOptionPane.showMessageDialog(null,
                    "Cliente não encontrado com CPF: " + cpfStr,
                    "Não encontrado",
                    JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Mostra dados atuais e pede os novos
            // O usuário vê o valor atual entre parênteses como referência
            String nome = JOptionPane.showInputDialog(null,
                "Novo nome:\n(Atual: " + existente.getNome() + ")",
                "Alterar Cliente", JOptionPane.PLAIN_MESSAGE);
            if (nome == null || nome.trim().isEmpty()) return;

            String tel = JOptionPane.showInputDialog(null,
                "Novo telefone (somente números):\n(Atual: " + existente.getTel() + ")",
                "Alterar Cliente", JOptionPane.PLAIN_MESSAGE);
            if (tel == null || tel.trim().isEmpty()) return;

            String end = JOptionPane.showInputDialog(null,
                "Novo endereço:\n(Atual: " + existente.getEnd() + ")",
                "Alterar Cliente", JOptionPane.PLAIN_MESSAGE);
            if (end == null || end.trim().isEmpty()) return;

            String numeroStr = JOptionPane.showInputDialog(null,
                "Novo número:\n(Atual: " + existente.getNumero() + ")",
                "Alterar Cliente", JOptionPane.PLAIN_MESSAGE);
            if (numeroStr == null || numeroStr.trim().isEmpty()) return;

            String cidade = JOptionPane.showInputDialog(null,
                "Nova cidade:\n(Atual: " + existente.getCidade() + ")",
                "Alterar Cliente", JOptionPane.PLAIN_MESSAGE);
            if (cidade == null || cidade.trim().isEmpty()) return;

            String estado = JOptionPane.showInputDialog(null,
                "Novo estado (sigla):\n(Atual: " + existente.getEstado() + ")",
                "Alterar Cliente", JOptionPane.PLAIN_MESSAGE);
            if (estado == null || estado.trim().isEmpty()) return;

            Integer numero = Integer.valueOf(numeroStr.trim());

            // Cria um novo Cliente com os dados atualizados (CPF mantido)
            // O DAO vai usar o CPF para localizar e atualizar o existente
            Cliente atualizado = new Cliente(nome, cpfStr, tel, end, numero, cidade, estado);
            dao.alterar(atualizado);

            JOptionPane.showMessageDialog(null,
                "Cliente alterado com sucesso!\n\n" + atualizado.toString(),
                "Alteração realizada",
                JOptionPane.INFORMATION_MESSAGE);

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null,
                "Dados inválidos! Verifique CPF, telefone e número.",
                "Erro",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    // =========================================================
    // MÉTODO PRIVADO: excluir
    // DELETE — remove um cliente pelo CPF com confirmação
    // =========================================================
    private static void excluir(IClienteDAO dao) {

        String cpfStr = JOptionPane.showInputDialog(null,
            "Digite o CPF do cliente a excluir:",
            "Excluir Cliente",
            JOptionPane.PLAIN_MESSAGE);

        if (cpfStr == null || cpfStr.trim().isEmpty()) return;

        try {
            long cpf = Long.parseLong(cpfStr.trim());
            Cliente existente = dao.consultar(cpf);

            if (existente == null) {
                JOptionPane.showMessageDialog(null,
                    "Cliente não encontrado com CPF: " + cpfStr,
                    "Não encontrado",
                    JOptionPane.WARNING_MESSAGE);
                return;
            }

            // showConfirmDialog: janela com botões "Sim" e "Não"
            int confirmacao = JOptionPane.showConfirmDialog(
                null,
                "Confirmar exclusão do cliente:\n\n" + existente.toString() + "\n\nEsta ação não pode ser desfeita!",
                "Confirmar Exclusão",
                JOptionPane.YES_NO_OPTION,   // Botões: Sim e Não
                JOptionPane.WARNING_MESSAGE  // Ícone de aviso
            );

            // JOptionPane.YES_OPTION = constante que vale 0 (representa o "Sim")
            if (confirmacao == JOptionPane.YES_OPTION) {
                dao.excluir(cpf);
                JOptionPane.showMessageDialog(null,
                    "Cliente excluído com sucesso!",
                    "Exclusão realizada",
                    JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null,
                    "Exclusão cancelada.",
                    "Cancelado",
                    JOptionPane.PLAIN_MESSAGE);
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null,
                "CPF inválido! Digite somente números.",
                "Erro",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    // =========================================================
    // MÉTODO PRIVADO: listarTodos
    // READ ALL — lista todos os clientes cadastrados
    //
    // StringBuilder: forma eficiente de montar uma String grande
    // no Java. Usar + em loop é ineficiente; StringBuilder usa buffer.
    // =========================================================
    private static void listarTodos(IClienteDAO dao) {

        Collection<Cliente> clientes = dao.buscarTodos();

        if (clientes.isEmpty()) { // isEmpty() = retorna true se a coleção não tem elementos
            JOptionPane.showMessageDialog(null,
                "Nenhum cliente cadastrado ainda.",
                "Lista vazia",
                JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // StringBuilder monta o texto da lista completa
        StringBuilder sb = new StringBuilder();
        sb.append("Total de clientes: ").append(clientes.size()).append("\n");
        sb.append("═".repeat(30)).append("\n\n");

        int contador = 1;
        for (Cliente c : clientes) { // for-each: percorre cada elemento da coleção
            sb.append("[ Cliente ").append(contador).append(" ]\n");
            sb.append(c.toString());  // toString() do Cliente é chamado aqui
            sb.append("\n").append("─".repeat(30)).append("\n\n");
            contador++;
        }

        JOptionPane.showMessageDialog(null,
            sb.toString(),
            "Lista de Clientes",
            JOptionPane.INFORMATION_MESSAGE);
    }
}