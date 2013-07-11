package pl.edu.icm.cermine.evaluation;

import java.io.IOException;
import java.util.List;
import libsvm.svm_parameter;
import org.apache.commons.cli.ParseException;
import pl.edu.icm.cermine.evaluation.tools.EvaluationUtils.DocumentsIterator;
import pl.edu.icm.cermine.evaluation.tools.PenaltyCalculator;
import pl.edu.icm.cermine.exception.AnalysisException;
import pl.edu.icm.cermine.exception.TransformationException;
import pl.edu.icm.cermine.structure.SVMInitialZoneClassifier;
import pl.edu.icm.cermine.structure.model.BxPage;
import pl.edu.icm.cermine.structure.model.BxZone;
import pl.edu.icm.cermine.structure.model.BxZoneLabel;
import pl.edu.icm.cermine.tools.classification.features.FeatureVectorBuilder;
import pl.edu.icm.cermine.tools.classification.general.BxDocsToTrainingSamplesConverter;
import pl.edu.icm.cermine.tools.classification.general.TrainingSample;
import pl.edu.icm.cermine.tools.classification.svm.SVMZoneClassifier;

public class SVMInitialZoneClassificationEvaluator extends CrossvalidatingZoneClassificationEvaluator {

    @Override
    protected SVMZoneClassifier getZoneClassifier(List<TrainingSample<BxZoneLabel>> trainingSamples) throws IOException, AnalysisException, CloneNotSupportedException {
        for (TrainingSample<BxZoneLabel> trainingSample : trainingSamples) {
            trainingSample.setLabel(trainingSample.getLabel().getGeneralLabel());
        }

        PenaltyCalculator pc = new PenaltyCalculator(trainingSamples);
        int[] intClasses = new int[pc.getClasses().size()];
        double[] classesWeights = new double[pc.getClasses().size()];
                
        Integer labelIdx = 0;
        for(BxZoneLabel label: pc.getClasses()) {
        	intClasses[labelIdx] = label.ordinal();
        	classesWeights[labelIdx] = pc.getPenaltyWeigth(label);
        	++labelIdx;
        }
        
        SVMZoneClassifier zoneClassifier = new SVMZoneClassifier(SVMInitialZoneClassifier.getFeatureVectorBuilder());
        svm_parameter param = SVMZoneClassifier.getDefaultParam();
        param.svm_type = svm_parameter.C_SVC;
        param.gamma = 0.03125;
        param.C = 64.0;
        param.kernel_type = svm_parameter.POLY;
        param.degree = 4;
        param.weight_label = intClasses;
        param.weight = classesWeights;

        zoneClassifier.buildClassifier(trainingSamples);

        return zoneClassifier;
    }

	public static void main(String[] args) 
			throws ParseException, AnalysisException, IOException, TransformationException, CloneNotSupportedException {
		CrossvalidatingZoneClassificationEvaluator.main(args, new SVMInitialZoneClassificationEvaluator());
	}

	@Override
	protected FeatureVectorBuilder<BxZone, BxPage> getFeatureVectorBuilder() {
		return SVMInitialZoneClassifier.getFeatureVectorBuilder();
	}

    @Override
    public List<TrainingSample<BxZoneLabel>> getSamples(String inputFile) throws AnalysisException {
        DocumentsIterator it = new DocumentsIterator(inputFile);
        return BxDocsToTrainingSamplesConverter.getZoneTrainingSamples(it.iterator(), 
                    getFeatureVectorBuilder(),
                    BxZoneLabel.getLabelToGeneralMap());
    }
}

