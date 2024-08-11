package qa.guru.rococo.api;

import okhttp3.Interceptor;
import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import qa.guru.rococo.config.Config;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.net.CookieManager;
import java.net.CookiePolicy;

public abstract class ApiClient {

    protected static final Config CFG = Config.getInstance();

    protected final OkHttpClient okHttpClient;
    protected final Retrofit retrofit;

    public ApiClient(@Nonnull String baseUrl) {
        this(baseUrl, false, JacksonConverterFactory.create());
    }

    public ApiClient(@Nonnull String baseUrl, boolean followRedirect, @Nonnull Interceptor... interceptors) {
        this(baseUrl, followRedirect, JacksonConverterFactory.create(), interceptors);
    }

    public ApiClient(@Nonnull String baseUrl,
                       boolean followRedirect,
                       @Nonnull Converter.Factory converterFactory,
                       @Nullable Interceptor... interceptors) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .followRedirects(followRedirect);

        if (interceptors != null) {
            for (Interceptor interceptor : interceptors) {
                builder.addNetworkInterceptor(interceptor);
            }
        }

        builder.cookieJar(new JavaNetCookieJar(new CookieManager(ThreadLocalCookieStore.INSTANCE, CookiePolicy.ACCEPT_ALL)));
        builder.addNetworkInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY));

        this.okHttpClient = builder.build();
        this.retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(this.okHttpClient)
                .addConverterFactory(converterFactory)
                .build();
    }
}