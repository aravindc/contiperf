/*
 * Copyright (C) 2011-2014 Volker Bergmann (volker.bergmann@bergmann-it.de).
 * All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.databene.contiperf.report;

import java.util.Random;

import org.databene.contiperf.report.GoogleLatencyRenderer;
import org.databene.contiperf.report.LatencyDataSet;
import org.databene.stat.LatencyCounter;
import org.junit.Test;

/**
 * Tests the {@link GoogleLatencyRenderer}.<br/><br/>
 * Created: 14.01.2011 12:57:27
 * @since 2.0.0
 * @author Volker Bergmann
 */
public class GoogleLatencyRendererTest {

	@Test
	public void testDataset() {
		LatencyDataSet dataset = new LatencyDataSet(15);
		dataset.addPoint(4, 0);
		dataset.addPoint(5, 1);
		dataset.addPoint(6, 10);
		dataset.addPoint(7, 134);
		dataset.addPoint(8, 156);
		dataset.addPoint(9, 142);
		dataset.addPoint(10, 126);
		dataset.addPoint(11, 60);
		dataset.addPoint(12, 40);
		dataset.addPoint(13, 30);
		dataset.addPoint(14, 10);
		dataset.addPoint(15, 1);
		dataset.addPoint(16, 0);
		dataset.addLabel("med", 10);
		dataset.addLabel("avg", 11);
		dataset.addLabel("90%", 13);
		String url = new GoogleLatencyRenderer().renderDataset(dataset, getClass().getSimpleName(), 400, 300);
		System.out.println(url);
	}
	
	Random random = new Random();

	@Test
	public void testCounter() {
		LatencyCounter counter = new LatencyCounter("test");
		for (int i = 0; i < 50000; i ++)
			counter.addSample(rand() + rand() + rand() + rand());
		System.out.println(new GoogleLatencyRenderer().render(counter, getClass().getSimpleName(), 400, 300));
	}

	private int rand() {
		return random.nextInt(20) * random.nextInt(20);
	}
	
}
