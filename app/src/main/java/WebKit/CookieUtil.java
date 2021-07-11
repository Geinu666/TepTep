package WebKit;

import android.content.Context;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;

public class CookieUtil {

    public static void removeCookie(Context context) {
        CookieSyncManager.createInstance(context);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeAllCookie();
        CookieSyncManager.getInstance().sync();
    }

    /**
     * 登陆成功，抓取Cookie同步到全局WebView
     * @param url
     * @param cookie
     */
    public static void syncCookie(String url, String cookie, Context context) {
        CookieSyncManager.createInstance(context);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        cookieManager.removeAllCookie();
        cookieManager.setCookie(url, cookie);
        CookieSyncManager.getInstance().sync();
        Log.i("test", "cookie sync" + cookie);
    }
}
