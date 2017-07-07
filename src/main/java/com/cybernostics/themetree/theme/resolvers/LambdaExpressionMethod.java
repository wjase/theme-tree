package com.cybernostics.themetree.theme.resolvers;

/*
 * #%L
 * spring-thymeleaf-themetree
 * %%
 * Copyright (C) 1992 - 2017 Cybernostics Pty Ltd
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
import java.util.List;
import java.util.function.Function;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.expression.AccessException;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.MethodExecutor;
import org.springframework.expression.MethodResolver;
import org.springframework.expression.TypedValue;
import org.springframework.expression.spel.support.StandardEvaluationContext;

/**
 *
 * @author jason
 */
public class LambdaExpressionMethod
{

    public static NamedLambdaRegistrar onContext(StandardEvaluationContext aContext)
    {
        return new NamedLambdaRegistrar(aContext);
    }

    public static class NamedLambdaRegistrar
    {

        private final StandardEvaluationContext context;

        public NamedLambdaRegistrar(StandardEvaluationContext context)
        {
            this.context = context;
        }

        public void registerMethod(String methodName, Function< Object[], Object> method)
        {
            context.addMethodResolver(new MethodResolver()
            {
                @Override
                public MethodExecutor resolve(EvaluationContext context, Object targetObject, String name, List<TypeDescriptor> argumentTypes) throws AccessException
                {
                    if (!name.equals(methodName))
                    {
                        return null;
                    }
                    return new MethodExecutor()
                    {
                        @Override
                        public TypedValue execute(EvaluationContext context, Object target, Object... arguments) throws AccessException
                        {
                            return new TypedValue(method.apply(arguments));
                        }
                    };
                }
            });

        }

    }

}
