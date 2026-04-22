package br.com.dmichelini.dao; // Pacote: caminho lógico que organiza as classes

import domain.Cliente;        // Importa a classe Cliente do pacote domain
import java.util.Collection;  // Collection é a "mãe" de List, Set etc — permite retornar qualquer coleção

/**
 * INTERFACE — IClienteDAO
 *
 * O que é uma Interface?
 * É um "contrato". Ela DEFINE o que deve existir (os métodos),
 * mas NÃO implementa nada — não escreve o corpo dos métodos.
 *
 * Por que usar Interface?
 * Imagine que amanhã você queira trocar o HashMap por um banco de dados.
 * Se o App.java depende de IClienteDAO (e não de ClienteMapDAO diretamente),
 * você troca a implementação sem mudar nada no App.
 * Isso se chama INVERSÃO DE DEPENDÊNCIA — pilar importante da OO.
 *
 * Convenção de nomenclatura:
 * O "I" no início = Interface. É um padrão do mercado para identificar interfaces.
 * O "DAO" = Data Access Object. Padrão de projeto para acesso a dados.
 *
 * CORREÇÃO aplicada: "Boolen" → "boolean" (tipo primitivo correto),
 * adicionados package e imports que estavam faltando.
 */
public interface IClienteDAO {

    // Retorna true se cadastrou com sucesso, false se CPF já existe
    public boolean cadastrar(Cliente cliente);

    // Recebe o CPF para identificar e excluir o cliente
    public void excluir(long cpf);

    // Recebe um Cliente com os dados novos (CPF é a chave para encontrá-lo)
    public void alterar(Cliente cliente);

    // Retorna o Cliente encontrado pelo CPF, ou null se não existir
    public Cliente consultar(long cpf);

    // Retorna todos os clientes cadastrados
    public Collection<Cliente> buscarTodos();
}