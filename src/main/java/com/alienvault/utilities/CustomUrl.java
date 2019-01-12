package com.alienvault.utilities;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.util.Key;

/***
 * Utility class to Encode URL
 */
public class CustomUrl extends GenericUrl {
    public CustomUrl(String encodedUrl) {

        super(encodedUrl);

    }

    /**
     * Url Builder
     * @param userRepo
     * @return url string with accessToken
     * @TODO create methods which can take all these parameters from configuration file
     */
    public static String urlBuilder(String userRepo)
    {
        String resultUrl = "https://api.github.com/repos/" + userRepo + "/issues?&access_token=23d52f07eaded650fc0498de0cc87f1cc811278d" ;
        return resultUrl;
    }

    /**
     * Url Builder
     * @param userRepo
     * @return url string with accessToken
     * @TODO create methods which can take all these parameters from configuration file
     */
    public static String urlBuilder(String userRepo, String url, String token)
    {
        String resultUrl = url + userRepo + "/issues?&access_token=" + token;
        return resultUrl;
    }
    @Override
    public boolean containsKey(Object key) {
        return super.containsKey(key);
    }

    @Key
    public int per_page;
}
