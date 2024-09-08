package app.helium.projectplanning.core.domain.utils;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import lombok.experimental.UtilityClass;

@UtilityClass
public class URLUtils {

    static public void validateURL(String url) {
        try {
            new URL(url).toURI();
        } catch (MalformedURLException | URISyntaxException exception) {
            throw new IllegalArgumentException("URL is malformed");
        }
    }

    static public void validateURLs(List<String> urls) {
        urls.forEach(URLUtils::validateURL);
    }
}
