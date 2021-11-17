package fr.ufc.l3info.oprog;

public class AdjustedTicket  implements ITicket {
    ITicket initial;
    private int amount;

    protected AdjustedTicket(ITicket initial, int amount){
        try {
            this.initial = initial;
            if (amount<0){
                this.amount=0;
            }else  {
                this.amount=amount;
            }
        }catch (NullPointerException e){
            System.out.print("blyat");
        }
    }

    @Override
    public boolean isChild() {
        return this.initial.isChild();
    }

    @Override
    public String getEntryStation() {
        return this.initial.getEntryStation();
    }

    @Override
    public boolean entering(String name) {
        return this.initial.entering(name);
    }

    @Override
    public void invalidate() {
    }

    @Override
    public int getAmount() {
        return amount+initial.getAmount();
    }

    @Override
    public boolean isValid() {
        return this.initial.isValid();
    }
}
