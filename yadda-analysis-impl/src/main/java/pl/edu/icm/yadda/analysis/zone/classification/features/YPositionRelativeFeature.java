package pl.edu.icm.yadda.analysis.zone.classification.features;

import pl.edu.icm.yadda.analysis.hmm.features.FeatureCalculator;
import pl.edu.icm.yadda.analysis.textr.model.BxPage;
import pl.edu.icm.yadda.analysis.textr.model.BxZone;

/**
 *
 * @author Dominika Tkaczyk (d.tkaczyk@icm.edu.pl)
 */
public class YPositionRelativeFeature implements FeatureCalculator<BxZone, BxPage> {

    private static String featureName = "YPositionRelative";

    @Override
    public String getFeatureName() {
        return featureName;
    }

    @Override
    public double calculateFeatureValue(BxZone zone, BxPage page) {
        return zone.getBounds().getY() / page.getBounds().getHeight();
    }

}