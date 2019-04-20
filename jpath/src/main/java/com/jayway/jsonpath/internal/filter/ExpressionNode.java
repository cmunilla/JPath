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

import com.jayway.jsonpath.Predicate;

public abstract class ExpressionNode implements Predicate {

    public static ExpressionNode createExpressionNode(ExpressionNode right, LogicalOperator operator,  ExpressionNode left){
        if(operator == LogicalOperator.AND){
            if((right instanceof LogicalExpressionNode) && ((LogicalExpressionNode)right).getOperator() == LogicalOperator.AND ){
                LogicalExpressionNode len = (LogicalExpressionNode) right;
                return len.append(left);
            } else {
                return LogicalExpressionNode.createLogicalAnd(left, right);
            }
        } else {
            if((right instanceof LogicalExpressionNode) && ((LogicalExpressionNode)right).getOperator() == LogicalOperator.OR ){
                LogicalExpressionNode len = (LogicalExpressionNode) right;
                return len.append(left);
            } else {
                return LogicalExpressionNode.createLogicalOr(left, right);
            }
        }
    }
}
