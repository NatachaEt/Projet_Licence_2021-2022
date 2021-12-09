package fr.ufc.l3info.oprog;

public class TenIllimitedTripsTicket implements ITicket {

    private int voyage;
    private boolean child;
    private int state;
    final private int ISSUED = 0;
    final private int ENTERED = 1;
    final private int INVALID = 2;
    private String entry = "should not be returned";

    protected TenIllimitedTripsTicket(boolean child){
    this.child = child;
    this.state=ISSUED;
    this.voyage=10;
    }

    @Override
    public boolean isChild() {
        return this.child;
    }

    @Override
    public String getEntryStation() {
        return (state == ENTERED) ? entry : null;
    }

    @Override
    public boolean entering(String name) {
        if (this.state == ISSUED && name != null && !name.trim().equals("") && this.voyage >= 1) {
            entry = name;
            this.state = ENTERED;
            return true;
        }
        if (this.state == ENTERED && name != null && !name.trim().equals("") && this.voyage >= 1) {
            entry = name;
            this.state = ENTERED;
            this.voyage--;
            return true;
        }
        invalidate();
        return false;

    }

    @Override
    public void invalidate() {
        if (voyage>1){
            voyage --;
            state = ISSUED;
            entry = "should not be returned" ;
            return;
        }
        voyage --;

        state = INVALID;
    }

    @Override
    public int getAmount() {
        return 2147483647;
    }

    @Override
    public boolean isValid() {
        return state != INVALID;
    }
}
