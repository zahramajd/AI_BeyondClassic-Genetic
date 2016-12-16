package genetic;

public class Individual {

    private Object data = 0;
    private Double fitness = 0.0;

    public Double probabilty = 0.0;
    public Double weight;


    Individual(Object data, Double fitness) {
        this.data = data;
        this.fitness = fitness;
    }

    public double getDouble() {
        return (double) this.data;
    }

    public Double getFitness() {
        return fitness;
    }

    public void set(Object data) {
        this.data = data;
    }


}
