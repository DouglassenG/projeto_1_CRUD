package domain; // Declara que esta classe pertence ao "pacote" domain

import java.util.Objects; // Importa a classe Objects para usar no equals e hashCode

/**
 * CLASSE DE DOMÍNIO (Model / Entidade)
 *
 * O que é uma Classe?
 * Uma classe é o "molde" para criar objetos. Pense em uma planta de um apartamento:
 * a planta define como será o apartamento (campos), mas o apartamento de verdade
 * é o OBJETO criado a partir dessa planta.
 *
 * O que é uma instância?
 * Quando você faz: Cliente c = new Cliente(...), você está INSTANCIANDO a classe,
 * ou seja, criando um objeto real a partir do molde.
 */
public class Cliente {

    // =========================================================
    // ATRIBUTOS (campos / propriedades do objeto)
    // private = só esta classe pode acessar diretamente
    // =========================================================
    private String nome;
    private Long cpf;
    private Long tel;
    private String end;
    private Integer numero;
    private String cidade;
    private String estado;

    // =========================================================
    // CONSTRUCTOR (Construtor)
    //
    // O que é?
    // É um método especial chamado automaticamente quando você
    // usa "new Cliente(...)". Ele inicializa o objeto com dados.
    //
    // Por que existe?
    // Para garantir que todo objeto Cliente nasça já com dados válidos.
    // Sem construtor, o objeto nasceria "vazio" (null em tudo).
    // =========================================================
    public Cliente(String nome, String cpf, String tel, String end,
                   Integer numero, String cidade, String estado) {

        this.nome   = nome;                      // "this" = este objeto atual
        this.cpf    = Long.valueOf(cpf.trim());  // .trim() remove espaços, Long.valueOf converte String -> Long
        this.tel    = Long.valueOf(tel.trim());
        this.end    = end;
        this.numero = numero;
        this.cidade = cidade;
        this.estado = estado;
    }

    // =========================================================
    // GETTERS e SETTERS
    //
    // O que são?
    // Como os atributos são "private", ninguém de fora acessa diretamente.
    // Os GETTERS permitem LEITURA (get = pegar).
    // Os SETTERS permitem ESCRITA (set = definir).
    //
    // Isso se chama ENCAPSULAMENTO — um dos pilares da OO.
    // Benefício: você controla o que entra e sai do objeto.
    // =========================================================

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Long getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = Long.valueOf(cpf.trim());
    }

    public Long getTel() {
        return tel;
    }

    // CORREÇÃO: Setter de tel agora aceita Long também (para uso interno no DAO)
    public void setTel(Long tel) {
        this.tel = tel;
    }

    // Setter com String mantido para uso com entrada do usuário (JOptionPane)
    public void setTelString(String tel) {
        this.tel = Long.valueOf(tel.trim());
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    // =========================================================
    // equals e hashCode
    //
    // O que é equals?
    // Define quando dois objetos Cliente são IGUAIS.
    // Aqui: dois clientes são iguais se tiverem o mesmo CPF.
    // Sem isso, Java compara o endereço de memória (dois objetos
    // diferentes nunca seriam "iguais" mesmo com o mesmo CPF).
    //
    // O que é hashCode?
    // Número que representa o objeto. Usado em HashMap/HashSet.
    // Regra: se dois objetos são equals, DEVEM ter o mesmo hashCode.
    //
    // @Override = estou substituindo o comportamento padrão do Java.
    // =========================================================
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;                         // Mesmo objeto na memória? São iguais.
        if (o == null || getClass() != o.getClass()) return false; // Null ou tipo diferente? São diferentes.
        Cliente cliente = (Cliente) o;                      // Faz o "cast": trata o Object como Cliente
        return Objects.equals(cpf, cliente.cpf);           // Compara pelo CPF
    }

    @Override
    public int hashCode() {
        return Objects.hash(cpf); // Gera um número único baseado no CPF
    }

    // =========================================================
    // toString
    //
    // O que é?
    // Quando você usa um objeto onde se espera texto (ex: JOptionPane.showMessageDialog),
    // Java chama automaticamente o toString(). Sem ele, exibiria algo como
    // "domain.Cliente@1a2b3c" (endereço de memória — inútil para o usuário).
    //
    // CORREÇÃO: Adicionado — estava faltando no seu código original.
    // =========================================================
    @Override
    public String toString() {
        return "Nome    : " + nome    + "\n" +
               "CPF     : " + cpf    + "\n" +
               "Telefone: " + tel    + "\n" +
               "Endereço: " + end + ", Nº " + numero + "\n" +
               "Cidade  : " + cidade + " - " + estado;
    }
}