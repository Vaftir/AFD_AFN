# LEIA ATENTATMENTE AS ESPECIFICAÇÕES: 

### Autores 

- Yago Faria
- Lucas Baesse

##    • DESENVOLVIMENTO

            Código desenvolvido em Java que faz uso em usa grande parte de recursos 
        de classes criadas com características das <tags> do arquivo jaflap.xml, tais 
        como a classe de Transitions que recebe os dados da tag <from>,<read> e <to> e 
        a classe Estates que recebe os dados da tag <states > <ID>.
    
             Em sua grande maioria os dados são tratos com ArrayList<String> que são 
        utilizados para exercer a lógica imposta pelo desenvolvedores, tais lógicas 
        referem-se aos métodos de conversão de um AFN para um AFD.  


##      • INPUTS 

            O programa receberá um arquivo jaflap.xml com a desfrição da AFN, tal arquivo 
        recebe o nome de Automato.txt. Todo arquivo jaflap.xml usado para teste deve ser 
        renomeado e convertido de jaflap.xml para Automato.txt.
    
            Dois arquivos de texto contendo Expressões Regulares (Regex) são inputs, porém 
        eles não devem ser alterados em nenhuma hipotese, pois são utilizados para a extração 
        de dados do Automato.txt. 
    
            Um arquivo contendo as Frases que serão testado estará como input do código, o
        mesmo receberá algumas frases de teste que poderão ser editadas pelo usuário com 
        o intuito de retirar ou adicionar novas frases. 
    
            Importante Ressaltar que os arquivos estão organizados em diretórios internos 
        do código, todos inputs seguem o caminho "Dados\Inputs\Arquivo.txt". 


##    • OUTPUTS

            Arquivo AnaliseDeFrases.txt, arquivo de analise das frases, tal analise é refere-se à validação 
        da frase dentro do AFD, verificando se ela é ou não de aceite. 
    
            Um arquivo contendo as transições e os Estados gerados para a nova AFD, tais
        dados estarão convertidos para um estrutura semelhante ao dados do  jaflap.xml. 
    
            Um arquivo registrando todas as transições e estados gerados pelo programa. 
    
            Importante Ressaltar que os arquivos estão organizados em diretórios internos 
        do código, todos output seguem o caminho "Dados\Outputs\Arquivo.txt". 

