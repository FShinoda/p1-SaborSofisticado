import static javax.swing.JOptionPane.*;

import static java.lang.Integer.*;

public class Principal {
    public static void main(String[] args) throws Exception {
        //Define a capacidade das listas caso necessário para não modificar tudo manualmente em testes
        int resCap = 2, espCap = 2; 

        Reserva listaReserva[] = new Reserva[resCap];
        Reserva listaEspera[] = new Reserva[espCap];

        int opcao;
        int posRes = 0, posEsp = 0;

        do{
            try{
                opcao = parseInt(showInputDialog(mostrarMenu()));

                switch(opcao){
                    case 1: // Reservar mesa

                    //If para decidir em qual lista será alocada a reserva
                    if(posRes < resCap){
                        reservarMesa(listaReserva, posRes);
                        showMessageDialog(null, "Sua reserva foi feita com sucesso!");
                        posRes++;

                    } else if (posEsp < espCap){
                        reservarMesa(listaEspera, posEsp);
                        showMessageDialog(null, "Sua reserva foi feita com sucesso, mas você está na lista de espera.");
                        posEsp++;

                    } else {
                        showMessageDialog(null, "Pedimos desculpas, já há uma longa lista de espera!");
                    }

                    break;
                    case 2: //Pesquisar reserva
                        if(posRes > 0){
                            pesquisarReserva(listaReserva, listaEspera, posRes, posEsp);
                        } else {
                            showMessageDialog(null, "Ainda não há reservas feitas");
                        }

                    break;
                    case 3: //Imprimir Reservas
                        imprimirReservas(listaReserva, posRes);

                    break;
                    case 4: //Imprimir Espera
                        imprimirEspera(listaEspera, posEsp);

                    break;
                    case 5: //Cancelar reserva
                        if(posRes > 0){
                            int aux = cancelarReserva(listaReserva, listaEspera, posRes, posEsp);
                            if(aux == 1){
                                showMessageDialog(null, "Reserva cancelada com sucesso!");
                                posRes--;
                            } else if ( aux == 2){
                                showMessageDialog(null, "Reserva cancelada com sucesso!");
                                posEsp--;
                            } 
                        } else {
                            showMessageDialog(null, "Ainda não há reservas feitas");
                        }
                        
                    break;
                    case 6: //Finaliza o programa
                    break;

                    default:
                        showMessageDialog(null, "Opção inválida! Por favor tente novamente");
                }
                
            } catch(NumberFormatException e){
                showMessageDialog(null, "Por favor digite um número!");
                opcao = 0;
            }


        } while(opcao != 6);
    }


    public static String mostrarMenu(){
        String tela = "Restaurante SABOR SOFISTICADO\n";
        tela += "1.Reservar mesa\n";
        tela += "2.Pesquisar reserva\n";
        tela += "3.Imprimir reservas\n";
        tela += "4.Imprimir lista de espera\n";
        tela += "5.Cancelar Reserva\n";
        tela += "6.Finalizar";

        return tela;
    };

    // aux
    public static Reserva realizarPagamento(Cliente cliente){
        String formaPagamento = "";
        boolean aVista;

        do{
            formaPagamento = showInputDialog(null, "Qual a forma de pagamento?\n1.À Vista\n2.Parcelado");

        } while(formaPagamento.equals("1") != true && formaPagamento.equals("2") != true);

        if(formaPagamento.equalsIgnoreCase("1")){
            aVista = true;
        } else {
            aVista = false;
        }

        Reserva reserva = new Reserva(cliente, aVista);
        
        return reserva;
    }

    public static int procurarReserva(Reserva[] lista, int pos, String codigoProcurado){
        
        for(int i = 0; i < pos; i++){
            if(lista[i].cliente.getCodigo().equals(codigoProcurado)){
                return i;
            } 
            
        } 

        return -1;
    }

    //1.
    public static void reservarMesa(Reserva[] lista, int pos){
        int tipoCliente = 0;

        Reserva resAux = null;
        

        do{
            try{
                tipoCliente = parseInt(showInputDialog(null, "1.Pessoa Jurídica\n2.Pessoa Física"));

                if(tipoCliente != 1 && tipoCliente != 2){
                    showMessageDialog(null, "Opção inválida! Por favor tente novamente");

                }


            } catch (NumberFormatException e){
                showMessageDialog(null, "Por favor digite um número!");
            }
        } while(tipoCliente != 1 && tipoCliente != 2);

        if(tipoCliente == 1){

            String nome = showInputDialog(null, "Digite o nome da empresa:");
            String cnpj = showInputDialog(null, "Digite o cnpj");
            
            PessoaJuridica pJur = new PessoaJuridica(nome, cnpj);

            resAux = realizarPagamento(pJur);

            
        } else {
            String nome = showInputDialog(null, "Digite seu nome");
            String cpf = showInputDialog(null, "Digite seu CPF");

            PessoaFisica pFis = new PessoaFisica(nome, cpf);

            resAux = realizarPagamento(pFis);
        }

        // Finalmente aloca a reserva na lista
        lista[pos] = resAux;
    }

    
    //2. Supondo que os codigos de cpf e cnpj se diferem assim como na realidade:

    public static void pesquisarReserva(Reserva[] listaReserva, Reserva[] listaEspera, int posRes, int posEsp){
        String codigoProcurado = showInputDialog(null, "Digite o CPF/CNPJ a ser procurado");
        
        int aux = procurarReserva(listaReserva, posRes, codigoProcurado);

        if(aux == -1 && posEsp > 0){
            aux = procurarReserva(listaEspera, posEsp, codigoProcurado);
        }

        if(aux == -1){
            showMessageDialog(null, "Não foi encontrada nenhuma reserva no CPF/CNPJ " + codigoProcurado);
        } else {
            showMessageDialog(null, "Sua reserva no CPF/CNPJ " + codigoProcurado + " foi encontrada!");
        }
        
    }

    //3.
    public static void imprimirReservas(Reserva[] listaReserva, int posRes){
        String lista = "Ainda não há reservas registradas!";
        
        if(posRes > 0){
            lista = "";
            for(int i = 0; i < posRes; i++){
                lista += listaReserva[i].toString() + "\n\n";
            }
        }

        showMessageDialog(null, lista);
    }

   //4.
   public static void imprimirEspera(Reserva[] listaEspera, int posEsp){
    String lista = "Ainda não há ninguém na lista de espera!";

    if(posEsp > 0){
        lista = "";
        for(int i = 0; i < posEsp; i++){
            lista += listaEspera[i].toString() + "\nposição de espera: " + (i+1) + "\n\n";
        }
    }

    showMessageDialog(null, lista);
   }

   //5.
   public static int cancelarReserva(Reserva[] listaReserva, Reserva[] listaEspera, int posRes, int posEsp){
        String codigoProcurado = showInputDialog(null, "Digite o CPF/CNPJ da reserva a ser cancelada");

        int aux = procurarReserva(listaReserva, posRes, codigoProcurado);

        if(aux != -1){
            for(int i = aux; i < posRes -1; i++){
                listaReserva[i] = listaReserva[i+1];
                
            }
            return 1;
        }
        else if(aux == -1 && posEsp > 0){
            aux = procurarReserva(listaEspera, posEsp, codigoProcurado);
        }

        if(aux == -1){
            showMessageDialog(null, "Não foi encontrada nenhuma reserva no CPF/CNPJ " + codigoProcurado);
            return -1;
        } else {
            for(int j = aux; j < posEsp - 1; j++){
                listaEspera[j] = listaEspera[j+1];
            }
            return 2;
            
        }

   }
   
}
