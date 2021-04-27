import java.util.ArrayList;

public class FIFO {
	private int[] tabela;	//estrutura necessária para guardar as páginas
	private int pivo = 0;	//atributo para saber qual quadro atualizar quando precisa substituir páginas
	
	public FIFO(int[] tabela) {
		this.tabela = tabela;
		
		for (int i = 0; i < tabela.length; i++)
			tabela[i] = -1;	//iniciar todos com -1 para representar que ainda não foi usado o espaço
	}
	
	public void add(int v) {
		
		if (pageFault(v)) {		//só atualiza quadro da tabela se ocorrer 'page fault'
			tabela[pivo] = v;
			
			if ((pivo + 1) < tabela.length)	//atualizar 'pivo' para próxima substituição
				pivo += 1;
			else
				pivo = 0;	//se 'pivo' em último lugar no vetor, então voltar ele para a posição zero
		}
	}
	
	public boolean pageFault(int v) {
		for (int i = 0; i < tabela.length; i++) {
			if (tabela[i] == v)
				return false;	//achou a página buscada na tabela
		}
		
		return true;
	}
	
	//Prints atendendo especificações do trabalho
	public void imprimeQuadros() {
		System.out.print("Quadros: ");
		for (int i = 0; i < tabela.length; i++) {
			if (tabela[i] == -1)
				System.out.print("- ");
			else
				System.out.print(tabela[i] + " ");
		}
	}
}

