public class Reserva implements Pagamento{
    Cliente cliente;
    boolean pagamentoAVista;

    public Reserva(Cliente cliente, boolean pagamentoAVista){
        this.cliente = cliente;
        this.pagamentoAVista = pagamentoAVista;
    }

    @Override
    public String toString() {
        return "tipo do Cliente: '" + cliente.getClass().getName() + "'" + ",\n"
            + "nome: '" + cliente.nome + "'" + ",\n"
            + "forma do Pagamento: '" + (pagamentoAVista ? "a vista" : "parcelado") + "'";
    }

    public double calcularPagamento() {
        if(pagamentoAVista){
            return 2880.00; //10% aplicado

        } else {
            return 3200.00;

        }
    }

    
    
}
