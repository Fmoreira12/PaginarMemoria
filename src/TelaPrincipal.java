import java.util.ArrayList;
import java.util.Scanner;

public class TelaPrincipal {
	
	public void tela() {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.print("Quantidade de quadros: ");
	    int	tamQuadros = converterStrtoInt(sc.nextLine());
	        
	    System.out.print("Sequência de acessos: ");
	    String str = sc.nextLine();
	    int[] sequencia = sequenciaToInteiros(str);	//pegar usando o método 'split(" ")'
	    											//a sequência de acesso de páginas
	    System.out.println("[1] FIFO");
	    System.out.println("[2] LRU");
	    System.out.print("Opção: ");
	    int op = converterStrtoInt(sc.nextLine());
	
	    System.out.println("\n");
	    
	    if (op == 1)								//métodos 'fazFIFO' e 'fazLRU' muito semelhantes
	    	fazFIFO(tamQuadros, sequencia);			//feitos apenas para separar e facilitar a visualização
		else if (op == 2)
			fazLRU(tamQuadros, sequencia);
		else
			System.out.println("Informou opção indisponível");
	}
	
	//A ideia é acessar a tabela de páginas através da sequência numérica passada como parâmetro
	//Mostrar como fica a cada acesso/adição de página e contar os 'page faults'
	private void fazFIFO(int tamQuadros, int[] sequencia) {
		FIFO fifo = new FIFO(new int[tamQuadros]);	//criar objeto com vetor para armazenar as páginas 
		int qntdPageFaults = 0;						//quantidade de vezes que é necessário substituir páginas
		
		for (int i = 0; i < sequencia.length; i++) {
	    	boolean pageFault = fifo.pageFault(sequencia[i]);//método 'pageFault' verdadeiro quando não achou página pesquisada
	    	
	    	System.out.print("Acessando página " + sequencia[i] + " -> ");	//print conforme descrição do trabalho
	    	fifo.add(sequencia[i]);		//adiciona antes de mostrar os quadros
	    	fifo.imprimeQuadros();
	    	
	    	if (pageFault) {	//acrescenta informação ao final da linha e/ou incrementa quantidade de page faults 
	    		System.out.println("(page fault)");
	    		qntdPageFaults++;
	    	}else {
	    		System.out.println("");
	    	}
	    }
		
		System.out.println("Número de falhas de página: " + qntdPageFaults);
	}
	
	//Quase igual ao método para FIFO, a diferença está nos argumentos para criar objetos
	//A estratégia para executar esse algorítmo é explicado no método de adcionar na classe 'LRU'
	private void fazLRU(int tamQuadros, int[] sequencia) {
		LRU lru = new LRU(new Node[tamQuadros]);
		int qntdPageFaults = 0;
		
		for (int i = 0; i < sequencia.length; i++) {//fazer para cada acesso da sequência informada 
	    	boolean pageFault = lru.pageFault(sequencia[i]);
	    	
	    	System.out.print("Acessando página " + sequencia[i] + " -> ");
	    	lru.add(new Node(sequencia[i], i));	//sequencia[i] é o valor para a tabela de páginas
	    	lru.imprimeQuadros();				//i é o índice da sequência de acessos 
	    										//explicação em 'add' da classe 'LRU'
	    	if (pageFault) {
	    		System.out.println("(page fault)");
	    		qntdPageFaults++;
	    	}else {
	    		System.out.println("");
	    	}
	    }
		
		System.out.println("Número de falhas de página: " + qntdPageFaults);
	}
	
	private int[] sequenciaToInteiros(String str) {
		int[] s = null;
			
		String[] aux = str.split(" ");	//'str.split(" ")' retorna um vetor de números em formato de string
		s = new int[aux.length];		//que são reconhecidos a cada espaço entre os números da sequência
			
		for (int i = 0; i < aux.length; i++)
			s[i] = converterStrtoInt(aux[i]);	//convertendo os números de string para int
		
		return s;
	}
	
	private int converterStrtoInt(String v) {
		int n = -1;	//quem chamar esse método deve saber como o valor retorna  
		
		try {
			n = Integer.parseInt(v);	//tenta converter, 'try' e 'catch' para evitar a 'quebra' do programa
		} catch (Exception e) {
			System.out.println("Erro na conversão de números.");
		}
		
		return n;
	}
}
