public class LRU {
	private Node[] tabela;
	
	public LRU(Node[] tabela) {
		this.tabela = tabela;
	}
	
	//A ideia é armazenar 'nodes' na tabela de páginas
	//Um node tem dois atributos, são eles 'value' e 'indexAcesso'
	//'value' representa a página como no algorítmo FIFO, mas
	//'indexAcesso' é necessário aqui para saber quando foi a última vez que página foi acessada
	//para isso, atribui-se o índice da sequência informada
	//ex: vetor sequencia = {1,2,2,1,3};
	//durante o loop de leitura do vetor sequencia,
	
	/*
	Acessando página 1: indexAcesso=0
	Acessando página 2: indexAcesso=1
	Acessando página 2: indexAcesso=2
	Acessando página 1: indexAcesso=3
	*/

	//Note que página '2' será substituída quando a '3' for acessada(page fault),
	//pois nesse momento tem como 'indexAcesso' igual a 2 e é menor do que o 'indexAcesso' da página '1'.
	
	public void add(Node n) {
		if (!isFull()) {				//se tabela não cheia, ver se página acessada não está armazenada em 'tabela,
										//senão, simplesmente armazena no primeiro espaço disponível
			if (pageFault(n.value)) {
				int p = primeiroQuadroNaoPreenchido();	//página ausente
				if (p < tabela.length)
					tabela[p] = n;
			}else {	//se valor já está presente em algum dos quadros, então basta alterar seu índice de acesso  
				Node a = buscaQuadro(n.value);
				
				a.indexAcesso = n.indexAcesso;
			}
		}
		else {//se tabela cheia, ver se página acessada não está armazenada em 'tabela', se está, só atualiza seu último acesso
			if (pageFault(n.value)) {
				int lru = Integer.MAX_VALUE;//garantir que na primeira comparação 'lru' seja o maior
				
				for (int i = 0; i < tabela.length; i++) {//procurar por menor 'indexAcesso' para substituir página em 'tabela'
					Node temp = tabela[i];	//pega 'node' da tabela que tem a página(value) e 'indexAcesso'
					
					if (temp.indexAcesso < lru) {
						lru = temp.indexAcesso;//encontrou menor, então, atribui a 'lru'
					}
				}
				
				for (int i = 0; i < tabela.length; i++) {//buscar e atualizar valor da página
					if (tabela[i].indexAcesso == lru)
						tabela[i].value = n.value;//encontrou página, então muda o valor
				}
				
			}else {	//se valor já está presente em algum dos quadros, então basta alterar seu índice de acesso  
				Node a = buscaQuadro(n.value);
				
				a.indexAcesso = n.indexAcesso;
			}
		}
		
	}
	
	private Node buscaQuadro(int value) {
		for (int i = 0; i < tabela.length; i++) {
			if (tabela[i] != null && tabela[i].value == value)
				return tabela[i];//achou a página pesquisada
		}
		
		return null;//não achou
	}
	
	public boolean pageFault(int v) {//verifica, se preciso, toda a tabela de páginas
		for (int i = 0; i < tabela.length; i++) {
			if (tabela[i] != null && tabela[i].value == v)
				return false;//achou, portanto, não é um 'page fault'
		}
		return true;//não achou
	}
	
	//Prints atendendo especificações do trabalho
	public void imprimeQuadros() {
		System.out.print("Quadros: ");
		for (int i = 0; i < tabela.length; i++) {
			if (tabela[i] == null)
				System.out.print("- ");
			else
				System.out.print(tabela[i].value + " ");
		}
	}

	private int primeiroQuadroNaoPreenchido() {
		for (int i = 0; i < tabela.length; i++) {
			if (tabela[i] == null)
				return i;
		}
		
		return -1;
	}
	
	private boolean isFull() {
		return (tabela[tabela.length-1] != null);	//vendo se tem algo na última posição do vetor, se sim, então vetor cheio
	}												//expressão entre parênteses retorna boolean
}
