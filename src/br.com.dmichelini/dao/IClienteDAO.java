/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

package domain;

/**
 *
 * @author dougl
 */
public interface IClienteDAO {

    public boolean cadastrar(Cliente cliente);

    public void excluir(long cpf);

    public void alterar(Cliente cliente);

    public Cliente consultar(long cpf);

    public Collection<Cliente> buscarTodos();
}
