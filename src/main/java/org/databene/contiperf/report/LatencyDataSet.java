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

import java.util.ArrayList;
import java.util.List;

/**
 * Helper class for managing and reducing latency distribution counts
 * to dimensions that can pe handled by the {@link GoogleLatencyRenderer}.<br/><br/>
 * Created: 14.01.2011 12:59:40
 * @since 2.0.0
 * @author Volker Bergmann
 */
public class LatencyDataSet {
	
	int[] xx;
	int[] yy;
	int pointCount;
	List<LabelInfo> labels;
	
	int maxX;
	int maxY;
	
	public LatencyDataSet(int capacity) {
		this.pointCount = 0;
		this.xx = new int[capacity];
		this.yy = new int[capacity];
		this.labels = new ArrayList<LabelInfo>();
	}
	
	public void addPoint(int x, int y) {
		if (pointCount == 0 && y > 0 && x > 0)
			addPoint(x - 1, 0);
		if (pointCount == xx.length)
			throw new RuntimeException("Capacity exceeded");
		xx[pointCount] = x;
		yy[pointCount] = y;
		if (pointCount == 0) {
			maxX = x;
			maxY = y;
		} else {
			if (x > maxX)
				maxX = x;
			if (y > maxY)
				maxY = y;
		}
		pointCount++;
	}

	public int getMaxX() {
		return maxX;
	}

	public int getMaxY() {
		return maxY;
	}

	public int pointCount() {
		return pointCount;
	}

	public int getX(int i) {
		return xx[i];
	}
	
	public int getY(int i) {
		return yy[i];
	}
	
	public void scaleY(int newMax) {
		double scale = (double) newMax / maxY;
		for (int i = 0; i < pointCount; i++)
			yy[i] = (int) (yy[i] * scale);
	}
	
	public void addLabel(String text, int x) {
		labels.add(new LabelInfo(text, indexForX(x)));
	}
	
	private int indexForX(int x) {
		int i = 0;
		while (i < xx.length && xx[i] < x)
			i++;
		return i;
	}

	public class LabelInfo {
		public final String text;
		public final int index;
		
		public LabelInfo(String text, int index) {
			super();
			this.text = text;
			this.index = index;
		}
	}

	public List<LabelInfo> getLabels() {
		return labels;
	}

	public LatencyDataSet reduce(int newSize) {
		int grouping = pointCount / newSize;
		if (grouping <= 1) {
			closeIfNeeded();
			return this;
		}
		LatencyDataSet result = new LatencyDataSet(newSize * 2);
		int sum = 0;
		int nonZeroIndex = -1;
		for (int i = 0; i < pointCount; i++) {
			if (yy[i] > 0 && nonZeroIndex == -1) {
				if (i > 0)
					result.addPoint(xx[i - 1], 0);
				nonZeroIndex = 0;
			}
			sum += yy[i];
			if (nonZeroIndex > -1 && nonZeroIndex % grouping == grouping - 1) {
				result.addPoint(xx[Math.max(i - grouping, 0)], sum);
				sum = 0;
			}
			if (nonZeroIndex > -1)
				nonZeroIndex++;
		}
		closeIfNeeded();
		return result;
	}

	private void closeIfNeeded() {
		if (pointCount > 0 && yy[pointCount - 1] > 0)
			addPoint(xx[pointCount - 1] + 1, 0);
	}

}
