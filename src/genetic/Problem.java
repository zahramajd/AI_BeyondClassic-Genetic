package genetic;

/**
 * Created by zahra on 12/15/2016 AD.
 */
public interface Problem {

    Individual crossover(Individual a,Individual b);

    void mutate(Individual a);

    Individual create();

}
