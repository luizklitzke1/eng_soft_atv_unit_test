package testes;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

//Libs para os testes
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import negocio.Cliente;
//Imports das classes de negocio
import negocio.ContaCorrente;
import negocio.GerenciadoraContas;
import negocio.IdadeNaoPermitidaException;

@DisplayName("Testes da classe GerenciadoraContasTeste")
public class GerenciadoraContasTeste 
{
	private List<ContaCorrente> listaContas       ;
    private GerenciadoraContas  gerenciadoraContas;

    @BeforeEach
    void setUp() 
    {
        this.listaContas = new ArrayList<ContaCorrente>();
        
        //Lista de contas para ser reutilizada em todos os testes
        this.listaContas.add(new ContaCorrente(1, 10.22 , true ));
        this.listaContas.add(new ContaCorrente(2, 33.99 , false));
        this.listaContas.add(new ContaCorrente(3, 1021.1, true ));
        this.listaContas.add(new ContaCorrente(4, 0.01  , false));
        this.listaContas.add(new ContaCorrente(5, 4.20  , true ));
        
        this.gerenciadoraContas = new GerenciadoraContas(this.listaContas);
    }

    @AfterEach 
    void tearDown() 
    {
        this.listaContas        = null;
        this.gerenciadoraContas = null;
    }
    
    @Test
    @DisplayName("GerenciadoraContas getContasDoBanco")
    public void getContasDoBanco_Test() 
    {
        Assertions.assertEquals(this.gerenciadoraContas.getContasDoBanco(), this.listaContas);
    }
    
    @DisplayName("GerenciadoraContas pesquisaConta  Existentes")
    @ParameterizedTest(name = "id: {0}")
    @ValueSource      (ints = { 1, 2, 3, 4, 5})
    public void pesquisaContaExistentes_Test(int id) 
    {
    	Assertions.assertNotNull(this.gerenciadoraContas.pesquisaConta(id));
    }
    
    @DisplayName("GerenciadoraContas pesquisaConta Inexistentes")
    @ParameterizedTest(name = "id: {0}")
    @ValueSource      (ints = { 7, 22, 9 })
    public void pesquisaContaInexistentes_Test(int id) 
    {
    	Assertions.assertNull(this.gerenciadoraContas.pesquisaConta(id));
    }
    
    @Test
    @DisplayName("GerenciadoraContas adicionaConta")
    public void adicionaConta_Test() 
    {
    	ContaCorrente conta6 = new ContaCorrente(6, 2  , false);
    	this.gerenciadoraContas.adicionaConta(conta6);
    	
    	Assertions.assertEquals(6, this.gerenciadoraContas.getContasDoBanco().size());
    	Assertions.assertEquals(conta6, this.listaContas.get(5));
    }
    
    @DisplayName("GerenciadoraContas removeConta Existente")
    @ParameterizedTest(name = "id: {0}")
    @ValueSource      (ints = { 1, 2, 3, 4, 5 })
    public void removeContaExistente_Test(int id) 
    {
    	Assertions.assertTrue(this.gerenciadoraContas.removeConta(id));
    	Assertions.assertEquals(5, this.gerenciadoraContas.getContasDoBanco().size());
    }
    
    @DisplayName("GerenciadoraContas removeConta Inexistente")
    @ParameterizedTest(name = "id: {0}")
    @ValueSource      (ints = { 99, 0, 23 })
    public void removeContaInexistente_Test(int id) 
    {
    	Assertions.assertFalse(this.gerenciadoraContas.removeConta(id));
    	Assertions.assertEquals(5, this.gerenciadoraContas.getContasDoBanco().size());
    }
    
    @DisplayName("GerenciadoraContas contaAtiva Ativo")
    @ParameterizedTest(name = "id: {0}")
    @ValueSource      (ints = { 1, 3, 5 })
    public void contaAtivaAtivo_Test(int id) 
    {
    	Assertions.assertTrue(this.gerenciadoraContas.contaAtiva(id));
    }
    
    @DisplayName("GerenciadoraContas contaAtiva Inativo")
    @ParameterizedTest(name = "id: {0}")
    @ValueSource      (ints = { 2, 4})
    public void contaAtivaInativo_Test(int id) 
    {
    	Assertions.assertFalse(this.gerenciadoraContas.contaAtiva(id));
    }
    
    @DisplayName("GerenciadoraContas contaAtiva Inexistente")
    @ParameterizedTest(name = "id: {0}")
    @ValueSource      (ints = { 99, 82, 22, 0 })
    public void contaAtivaInexistente_Test(int id) 
    {
    	Assertions.assertFalse(this.gerenciadoraContas.contaAtiva(id));
    }
    
    private static Stream<Arguments> transfereValor_Test() {
        return Stream.of(
            Arguments.arguments(true , 1, 2, 1.00  , 9.22 , 34.99 ),
            Arguments.arguments(false, 1, 2, 100.00, 10.22, 33.99 ),
            Arguments.arguments(true , 2, 3, 10.00 , 23.99, 1031.1),
            Arguments.arguments(true , 5, 1, 4.20  , 0.00 , 14.42 )
        );
    }
    
    @DisplayName("GerenciadoraContas transfereValor")
    @ParameterizedTest(name = "resultado: {0}, idOrigem: {1}, idDestino: {2}, valorTransf: {3}, esperadoOrigem: {4}, esperadoDestino: {5}")
    @MethodSource()
    public void transfereValor_Test(boolean resultado, int idOrigem, int idDestino, double valorTransf, double esperadoOrigem, double esperadoDestino) 
    {
    	Assertions.assertEquals(resultado, this.gerenciadoraContas.transfereValor(idOrigem, valorTransf, idDestino));
    	
    	Assertions.assertEquals(esperadoOrigem , this.gerenciadoraContas.pesquisaConta(idOrigem ).getSaldo(), 0.001);
    	Assertions.assertEquals(esperadoDestino, this.gerenciadoraContas.pesquisaConta(idDestino).getSaldo(), 0.001);
    }
}
