package au.com.cybernostics.themetree.theme.sources;

/*
 * #%L
 * theme-tree
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

import java.security.Principal;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;

import org.springframework.context.ApplicationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

/**
 * SpringELRequestCondition represents an expression which is used to evaluate a
 * condition. The expression is evaluated in a context which includes some
 * relevant http request variables:
 * <pre>
 *      clientDateTime: a ZonedDateTime representing the time/date of the
 *                      request in the clients time zone.
 *      app: The application context
 *      username: The current user name or "ANONYMOUS" if noone logged in
 *      request: The current HttpServletRequest
 * </pre>
 *
 * @author jason wraxall
 */
public class SpringELRequestCondition implements Function<HttpServletRequest, Boolean> {

    private static ExpressionParser parser = new SpelExpressionParser();
    private Expression conditionalExpression;
    private StandardEvaluationContext evalContext = new StandardEvaluationContext();
    private Optional<BiConsumer<StandardEvaluationContext, HttpServletRequest>> requestSimpleVariableExtractor = Optional.empty();
    private static Logger log = Logger.getLogger(SpringELRequestCondition.class.getName());

    public SpringELRequestCondition() {
    }

    public SpringELRequestCondition(String expression) {
        conditionalExpression = parser.parseExpression(expression);
        evalContext.setRootObject(this);
    }

    public void addMethod(String methodName, Function<Object[], Object> lambda) {
        LambdaExpressionMethod.onContext(evalContext).registerMethod(methodName, lambda);
    }

    public void setApplicationContext(ApplicationContext context) {
        evalContext.setVariable("app", context);
    }

    /**
     * This lambda provides a hook just before the expression is evaluated, to
     * provide the chance to set any variables in the expression context. That
     * could involve some date processing for example to create a
     * 'clientDateTime'
     *
     * @param requestSimpleVaribaleExtractor
     */
    public void setRequestSimpleVariableExtractor(BiConsumer<StandardEvaluationContext, HttpServletRequest> requestSimpleVaribaleExtractor) {
        this.requestSimpleVariableExtractor = Optional.of(requestSimpleVaribaleExtractor);
    }

    @Override
    public Boolean apply(HttpServletRequest t) {
        evalContext.setVariable("request", t);
        Principal principal = t.getUserPrincipal();
        evalContext.setVariable("username", principal == null ? "ANONYMOUS" : principal.getName());

        final String requestDateString = t.getHeader("Date");
        if (requestDateString != null) {
            try {
                ZonedDateTime requestDate = ZonedDateTime.parse(requestDateString, DateTimeFormatter.RFC_1123_DATE_TIME);
                evalContext.setVariable("clientDateTime", requestDate);
            } catch (Throwable th) {
                log.log(Level.SEVERE, "Bad request date from http request header", th);
                throw th;
            }

        }
        requestSimpleVariableExtractor.ifPresent(variableExtractor -> variableExtractor.accept(evalContext, t));
        return conditionalExpression.getValue(evalContext, Boolean.class);
    }

}
