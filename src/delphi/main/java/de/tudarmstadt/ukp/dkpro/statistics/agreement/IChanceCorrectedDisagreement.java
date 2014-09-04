/*******************************************************************************
 * Copyright 2014
 * Ubiquitous Knowledge Processing (UKP) Lab
 * Technische Universität Darmstadt
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package delphi.main.java.de.tudarmstadt.ukp.dkpro.statistics.agreement;

/**
 * Generic interface to be implemented by all inter-rater agreement measures 
 * that perform a chance correction and thus distinguish between observed
 * disagreement and expected disagreement. The basic idea is that if raters 
 * made a random decision on each annotation unit, then the final inter-rater 
 * agreement should be zero. See also {@link IChanceCorrectedAgreement}
 * for the analogous definition of chance corrected measures with observed
 * and expected agreement.<br><br>
 * References:<ul>
 * <li>Artstein, R. & Poesio, M.: Inter-Coder Agreement for Computational 
 *   Linguistics. Computational Linguistics 34(4):555-596, 2008.</li></ul>
 * @see IAgreementMeasure
 * @see DisagreementMeasure
 * @see IChanceCorrectedAgreement
 * @author Christian M. Meyer
 */
public interface IChanceCorrectedDisagreement extends IAgreementMeasure {
	
	/** Returns the observed disagreement of an annotation study. The observed
	 *  disagreement is basically the proportion of annotation units that the
	 *  raters disagree on divided by the number of units in the given study. */
	public double calculateObservedDisagreement();
	
	/** Returns the expected disagreement of an annotation study. The expected 
	 *  disagreement is the proportion of disagreement that would be expected by 
	 *  chance alone. The expected disagreement should be equal to the observed
	 *  disagreement if each rater makes a random decision for each unit. */
	public double calculateExpectedDisagreement();
	
}