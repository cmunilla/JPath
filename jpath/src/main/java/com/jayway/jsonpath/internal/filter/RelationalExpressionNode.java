/*
 * Copyright 2011 the original author or authors.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * This source code was originally provided by the Jayway JsonPath project
 * and might have been modified in the JPath project
 */
package com.jayway.jsonpath.internal.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RelationalExpressionNode extends ExpressionNode {

    private static final Logger logger = LoggerFactory.getLogger(RelationalExpressionNode.class);

    private final ValueNode left;
    private final RelationalOperator relationalOperator;
    private final ValueNode right;

    public RelationalExpressionNode(ValueNode left, RelationalOperator relationalOperator, ValueNode right) {
        this.left = left;
        this.relationalOperator = relationalOperator;
        this.right = right;

        logger.trace("ExpressionNode {}", toString());
    }

    @Override
    public String toString() {
        if(relationalOperator == RelationalOperator.EXISTS){
            return left.toString();
        } else {
            return left.toString() + " " + relationalOperator.toString() + " " + right.toString();
        }
    }

    @Override
    public boolean apply(PredicateContext ctx) {
        ValueNode l = left;
        ValueNode r = right;

        if(left.isPathNode()){
            l = left.asPathNode().evaluate(ctx);
        }
        if(right.isPathNode()){
            r = right.asPathNode().evaluate(ctx);
        }
        Evaluator evaluator = EvaluatorFactory.createEvaluator(relationalOperator);
        if(evaluator != null){
            return evaluator.evaluate(l, r, ctx);
        }
        return false;
    }
}