/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.flink.cep;

import org.apache.flink.api.common.functions.AbstractRichFunction;
import org.apache.flink.api.common.functions.RichFunction;
import org.apache.flink.api.common.functions.RuntimeContext;
import org.apache.flink.util.Collector;
import org.apache.flink.util.Preconditions;

import java.util.List;
import java.util.Map;

/**
 * Rich variant of the {@link PatternFlatSelectFunction}. As a {@link RichFunction}, it gives access to the
 * {@link org.apache.flink.api.common.functions.RuntimeContext} and provides setup and teardown methods:
 * {@link RichFunction#open(org.apache.flink.configuration.Configuration)} and
 * {@link RichFunction#close()}.
 *
 * @param <IN> Type of the input elements
 * @param <OUT> Type of the output element
 */
public abstract class RichPatternFlatSelectFunction<IN, OUT>
		extends AbstractRichFunction
		implements PatternFlatSelectFunction<IN, OUT> {

	private static final long serialVersionUID = 1L;

	@Override
	public void setRuntimeContext(RuntimeContext runtimeContext) {
		Preconditions.checkNotNull(runtimeContext);

		if (runtimeContext instanceof CepRuntimeContext) {
			super.setRuntimeContext(runtimeContext);
		} else {
			super.setRuntimeContext(new CepRuntimeContext(runtimeContext));
		}
	}

	public abstract void flatSelect(Map<String, List<IN>> pattern, Collector<OUT> out) throws Exception;
}