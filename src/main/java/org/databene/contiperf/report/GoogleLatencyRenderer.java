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

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.databene.contiperf.report.LatencyDataSet.LabelInfo;
import org.databene.stat.LatencyCounter;

/**
 * Formats the latency distribution of a {@link LatencyCounter} using the Google charts API.<br/><br/>
 * Created: 14.01.2011 11:54:18
 * @since 2.0.0
 * @author Volker Bergmann
 */
public class GoogleLatencyRenderer {
	
	public String render(LatencyCounter counter, String title, int width, int height) {
		LatencyDataSet dataset = new LatencyDataSet((int) (counter.maxLatency() - counter.minLatency() + 3));
		for (int i = (int) counter.minLatency(); i <= counter.maxLatency(); i++)
			dataset.addPoint(i, (int) counter.getLatencyCount(i));
		dataset = dataset.reduce(50);
		dataset.addLabel("avg", (int) counter.averageLatency());
		dataset.addLabel("med", (int) counter.percentileLatency(50));
		dataset.addLabel("90%", (int) counter.percentileLatency(90));
		return renderDataset(dataset, title, width, height);
	}
	
	String renderDataset(LatencyDataSet dataset, String title, int width, int height) {
		dataset.scaleY(80);
		try {
			StringBuilder builder = new StringBuilder("http://chart.apis.google.com/chart?cht=lxy"); // xy line chart
			builder.append("&chs=").append(width).append('x').append(height); // image size
			appendData(dataset, builder); // data definition
			builder.append("&chxt=x"); // render x axis only
			builder.append("&chxr=0,0," + dataset.getMaxX()); // x axis scale (#axis, min, max, tick spacing)
			builder.append("&chds=0," + dataset.getMaxX() + ",0,100"); // data scale (min x, max x, min y, max y)
			builder.append("&chf=c,lg,0,FFFFFF,0,FFFF88,1");
			renderLabels(dataset, builder);
			if (title != null)
				builder.append("&chtt=" + URLEncoder.encode(title, "UTF-8")); // title
			return builder.toString();
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("Error encoding title: " + title, e);
		}
	}

	private static void renderLabels(LatencyDataSet dataset, StringBuilder builder) {
		builder.append("&chm=B,FFE69B,0,0,0"); // fill
		for (LabelInfo label : dataset.getLabels())
			builder.append("|A" + label.text + ",666666,0," + label.index + ",15"); // labels
	}

	private static void appendData(LatencyDataSet dataset, StringBuilder builder) {
		builder.append("&chd=t:");
		for (int i = 0; i < dataset.pointCount(); i++) {
			if (i > 0)
				builder.append(',');
			builder.append(dataset.getX(i));
		}
		builder.append('|');
		for (int i = 0; i < dataset.pointCount(); i++) {
			if (i > 0)
				builder.append(',');
			builder.append(dataset.getY(i));
		}
	}

}
