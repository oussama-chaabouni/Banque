package banque.services;

import org.apache.commons.math3.exception.MathIllegalArgumentException;
import org.apache.commons.math3.stat.descriptive.rank.Percentile;
import org.apache.commons.math3.stat.ranking.NaNStrategy;
import org.apache.commons.math3.util.KthSelector;
import org.apache.commons.math3.util.MedianOf3PivotingStrategy;

public class PercentileExcel extends Percentile {
    public PercentileExcel() throws MathIllegalArgumentException {

    super(50.0,
          EstimationType.R_7, // use excel style interpolation
          NaNStrategy.REMOVED,
          new KthSelector(new MedianOf3PivotingStrategy()));
    }
}
