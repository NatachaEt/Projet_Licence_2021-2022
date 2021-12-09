package fr.ufc.l3info.oprog;

public class TicketMachine {

    private static final TicketMachine INSTANCE = new TicketMachine();

    private TicketMachine() { }

    public static TicketMachine getInstance() {
        return INSTANCE;
    }

    public ITicket buyTicket(boolean child, int... amount){
        if(amount.length == 0) return new TenIllimitedTripsTicket(child);
        if(amount[0] > 0) return new BaseTicket(child, amount[0]);
        return  null;
    }

    public ITicket adjustFare(ITicket old, int amount){
        if(old == null) return null;
        if (old instanceof TenIllimitedTripsTicket) return old;
        if(amount <= 0) return old;
        return new AdjustedTicket(old,amount);
    }
}
