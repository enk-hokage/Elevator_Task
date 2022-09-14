public class Passenger implements Comparable{
    private int to;

    public int getTo() {
        return to;
    }

    public void setTo(int to) {
        this.to = to;
    }

    @Override
    public int compareTo(Object o) {
        return to > ((Passenger) o).getTo() ? 1 : -1;
    }
}
