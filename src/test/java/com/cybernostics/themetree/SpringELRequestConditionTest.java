package com.cybernostics.themetree;

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
import com.cybernostics.themetree.theme.sources.SpringELRequestCondition;
import java.text.ParseException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import javax.servlet.http.HttpServletRequest;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.runners.MockitoJUnit44Runner;
import org.springframework.context.ApplicationContext;

/**
 *
 * @author jason
 */
@RunWith(MockitoJUnit44Runner.class)
public class SpringELRequestConditionTest
{

    @Mock
    private ApplicationContext applicationContext;

    @Mock
    private HttpServletRequest request;

    public SpringELRequestConditionTest()
    {
    }

    @Test
    public void testTruthy()
    {
        SpringELRequestCondition condition = new SpringELRequestCondition("true");
        Assert.assertThat(condition.apply(request), is(true));
    }

    @Test
    public void testFalsy()
    {
        SpringELRequestCondition condition = new SpringELRequestCondition("false");
        Assert.assertThat(condition.apply(request), not(true));
    }

    @Test
    public void testURICheck()
    {
        when(request.getRequestURI()).thenReturn("somevalue");
        SpringELRequestCondition condition = new SpringELRequestCondition("#request.requestURI=='somevalue'");
        Assert.assertThat(condition.apply(request), is(true));
        when(request.getRequestURI()).thenReturn("someOthervalue");
        Assert.assertThat(condition.apply(request), not(true));
    }

    @Test
    public void testChristmas() throws ParseException
    {
        /**
         * Note: In order to do this right, you need to encode the user's
         * TimeZone offset into the request. There are js scripts to do this and
         * poke it into a cookie.
         */
        ZonedDateTime outSideChristmasPeriod = ZonedDateTime.now().withMonth(11);
        ZonedDateTime inSideChristmasPeriod = ZonedDateTime.now().withMonth(12).withDayOfMonth(15);
        when(request.getHeader("Date")).thenReturn(outSideChristmasPeriod.format(DateTimeFormatter.RFC_1123_DATE_TIME));
        SpringELRequestCondition condition = new SpringELRequestCondition("inChristmasPeriod(#clientDateTime)");
        condition.addMethod("inChristmasPeriod", (args) -> inChristmasPeriod((ZonedDateTime) args[0]));

        Assert.assertThat(condition.apply(request), is(false));
        when(request.getHeader("Date")).thenReturn(inSideChristmasPeriod.format(DateTimeFormatter.RFC_1123_DATE_TIME));
        Assert.assertThat(condition.apply(request), is(true));
    }

    public static ZonedDateTime decemberOne = ZonedDateTime.now().withDayOfMonth(1).withMonth(12);
    public static ZonedDateTime christmasDay = ZonedDateTime.now().withDayOfMonth(25).withMonth(12);

    public static boolean inChristmasPeriod(ZonedDateTime dateTime)
    {
        return dateTime.isAfter(decemberOne) && dateTime.isBefore(christmasDay);
    }
}
