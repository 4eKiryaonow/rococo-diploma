package qa.guru.rococo.api.interceptor;

import okhttp3.Interceptor;
import okhttp3.Response;
import org.apache.commons.lang3.StringUtils;
import qa.guru.rococo.api.context.LocalStorageContext;

import java.io.IOException;

public class RecievedCookieInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Response response = chain.proceed(chain.request());
        String locationHeader = response.header("Location");
        if (response.code() == 302 && locationHeader != null && locationHeader.contains("authorized?code=")) {
            LocalStorageContext.getInstance().setCode(StringUtils.substringAfter(locationHeader, "code="));
        }
        return response;
    }
}
