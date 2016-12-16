package genetic;

/**
 * Created by zahra on 12/15/2016 AD.
 */
public class SolveEquation implements Problem {


    @Override
    public Individual crossover(Individual a, Individual b) {
        return create((a.getDouble() + b.getDouble()) / 2);
    }

    @Override
    public void mutate(Individual a,double step) {
        double current = a.getDouble();
        double val;
      //  double step = 0.01;

        do {
            double rand = (Math.random() - .5) * 2; // -1 , 1
            rand = step * Math.rint(rand / step);
            val = current + rand;
        } while (val < .2 || val > 3.14);

        a.set(val);
    }


    public Individual create(Double val) {
        double fitness = 1 / (1 + Math.abs(Math.sin(val) - Math.pow(val, 2) + val));
        return new Individual(val, fitness);
    }

    @Override
    public Individual create() {
        return create((Math.random() * (3.14 - .2)) + 0.2);
    }

    @Override
    public boolean check_fit_enough(Individual individual) {

        double val = individual.getDouble();
        if (Math.abs(Math.sin(val) - Math.pow(val, 2) + val) < 0.001)
            return true;

        return false;
    }


}