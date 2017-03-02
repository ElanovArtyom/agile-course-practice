package ru.unn.agile.IntersectTwoLine.viewmodel;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

/**
 * Created by Artyom on 001 01.03.17.
 */

public class Regexmatchers extends BaseMatcher {
    private final String regex;

    public Regexmatchers(final String regex) {
        this.regex = regex;
    }

    public boolean matches(final Object a) {
        return ((String) a).matches(regex);
    }

    public void describeTo(final Description description) {
        description.appendText("Matches regex = ");
        description.appendText(regex);
    }

    public static Matcher<? super String> matchesPattern(final String regex) {
        Regexmatchers matcher = new Regexmatchers(regex);
        //NOTE: this ugly cast is needed to workaround 'unchecked' Java warning
        @SuppressWarnings (value = "unchecked")
        Matcher<? super String> castedMatcher = (Matcher<? super String>)   matcher;
        return castedMatcher;
    }
}
