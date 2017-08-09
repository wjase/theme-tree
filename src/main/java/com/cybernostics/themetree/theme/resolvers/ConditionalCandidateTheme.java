package com.cybernostics.themetree.theme.resolvers;

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
import java.util.function.Function;
import javax.servlet.http.HttpServletRequest;

/**
 * A Theme which has a custom supplied function to determine if it is active.
 *
 * @author jason wraxall
 */
public class ConditionalCandidateTheme implements CandidateTheme
{

    private int order;
    private String name;
    private Function<HttpServletRequest, Boolean> isActiveCondition;

    public ConditionalCandidateTheme(int order, String name, Function<HttpServletRequest, Boolean> isActiveCondition)
    {
        this.order = order;
        this.name = name;
        this.isActiveCondition = isActiveCondition;
    }

    @Override
    public int getOrder()
    {
        return order;
    }

    @Override
    public boolean isActive(HttpServletRequest request)
    {
        return isActiveCondition.apply(request);
    }

    @Override
    public String getName(HttpServletRequest request)
    {
        return name;
    }

}
