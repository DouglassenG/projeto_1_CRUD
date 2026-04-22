package br.com.dmichelini.dao;

import domain.Cliente;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * IMPLEMENTAÇÃO DA INTERFACE — ClienteMapDAO
 *
 * O que é "implements IClienteDAO"?
 * Significa que esta classe ASSINA O CONTRATO da interface.
 * Ela é OBRIGADA a implementar todos os métodos definidos em IClienteDAO.
 * Se faltar qualquer método, o Java recusa compilar o projeto.
 *
 * O que é HashMap?
 * É uma estrutura de dados que armazena pares CHAVE → VALOR.
 * Aqui: CHAVE = CPF (Long), VALOR = objeto Cliente.
 * Vantagem: buscar por CPF é instantâneo (O(1) — complexidade constante).
 *
 * CORREÇÕES APLICADAS:
 * 1. Removido "private Object map" — era variável duplicada e incorreta
 * 2. Todos os this.map foram corrigidos para this.clientes
 * 3. Fechamento de chaves do excluir estava faltando
 * 4. Removidos pontos extras em setEnd.() setCidade.() setEstado.()
 * 5. Removidos métodos duplicados com Object no final
 * 6. Removida classe Cliente interna que conflitava com a real
 * 7. setTel agora passa Long corretamente
 */
public class ClienteMapDAO implements IClienteDAO {

    // MAP<CHAVE, VALOR>: onde a chave é o CPF (Long) e o valor é o objeto Cliente
    // "private" = encapsulamento: só esta classe manipula o mapa diretamente
    private Map<Long, Cliente> clientes;

    // =========================================================
    // CONSTRUTOR
    // Inicializa o HashMap vazio quando o DAO é criado.
    // Sem isso, "clientes" seria null e qualquer chamada causaria NullPointerException.
    // =========================================================
    public ClienteMapDAO() {
        this.clientes = new HashMap<>();
    }

    // =========================================================
    // CREATE — Cadastrar
    //
    // Fluxo:
    // 1. Verifica se já existe um cliente com esse CPF (chave do Map)
    // 2. Se já existe → retorna false (não cadastra duplicado)
    // 3. Se não existe → insere no Map e retorna true
    //
    // containsKey(chave) → verifica se a chave existe no Map
    // put(chave, valor)  → insere o par chave-valor no Map
    // =========================================================
    @Override
    public boolean cadastrar(Cliente cliente) {
        if (this.clientes.containsKey(cliente.getCpf())) {
            return false; // CPF já cadastrado — não sobrescreve
        }
        this.clientes.put(cliente.getCpf(), cliente);
        return true;      // Cadastro realizado com sucesso
    }

    // =========================================================
    // DELETE — Excluir
    //
    // remove(chave) → remove o par chave-valor do Map pelo CPF.
    // Se não existir, simplesmente não faz nada (sem exceção).
    //
    // CORREÇÃO: estava faltando fechar o "}" do método — causava erro de compilação.
    // =========================================================
    @Override
    public void excluir(long cpf) {
        this.clientes.remove(cpf); // Forma direta e correta — remove pela chave
    }

    // =========================================================
    // UPDATE — Alterar
    //
    // Fluxo:
    // 1. Busca o cliente existente pelo CPF
    // 2. Se encontrar → atualiza campo a campo usando setters
    // 3. Se não encontrar → não faz nada (cliente inexistente)
    //
    // Por que atualizar campo a campo e não substituir o objeto inteiro?
    // Porque o CPF (chave do Map) não pode mudar — é o identificador único.
    //
    // CORREÇÕES: removidos pontos extras em setEnd.() setCidade.() setEstado.()
    //            setTel agora recebe Long (tipo correto que getTel() retorna)
    // =========================================================
    @Override
    public void alterar(Cliente cliente) {
        Cliente clienteCadastrado = this.clientes.get(cliente.getCpf()); // Busca pelo CPF

        if (clienteCadastrado != null) { // Só altera se o cliente existir
            clienteCadastrado.setNome(cliente.getNome());
            clienteCadastrado.setTel(cliente.getTel());      // Long → Long (correto)
            clienteCadastrado.setNumero(cliente.getNumero());
            clienteCadastrado.setEnd(cliente.getEnd());      // CORREÇÃO: era setEnd.(...)
            clienteCadastrado.setCidade(cliente.getCidade()); // CORREÇÃO: era setCidade.(...)
            clienteCadastrado.setEstado(cliente.getEstado()); // CORREÇÃO: era setEstado.(...)
        }
    }

    // =========================================================
    // READ — Consultar por CPF
    //
    // get(chave) → retorna o valor associado à chave, ou null se não existir.
    // =========================================================
    @Override
    public Cliente consultar(long cpf) {
        return this.clientes.get(cpf); // Retorna o Cliente ou null
    }

    // =========================================================
    // READ ALL — Buscar Todos
    //
    // values() → retorna todos os VALORES do Map (os objetos Cliente)
    // sem as chaves (CPFs). Perfeito para listar todos os clientes.
    // =========================================================
    @Override
    public Collection<Cliente> buscarTodos() {
        return this.clientes.values();
    }
}