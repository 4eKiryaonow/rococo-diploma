package qa.guru.rococo.api;

import okhttp3.Interceptor;
import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import qa.guru.rococo.api.context.ThreadLocalCookieStore;
import qa.guru.rococo.config.Config;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.net.CookieManager;
import java.net.CookiePolicy;

import static okhttp3.logging.HttpLoggingInterceptor.Level.BODY;

public abstract class ApiClient {

    protected static final Config CFG = Config.getInstance();

    protected final OkHttpClient okHttpClient;
    protected final Retrofit retrofit;

    public ApiClient(String baseUrl) {
        this(baseUrl, false, JacksonConverterFactory.create(), BODY);
    }

    public ApiClient(String baseUrl, boolean followRedirect, Converter.Factory converter, HttpLoggingInterceptor.Level loggingLevel) {
        this(baseUrl, followRedirect, converter, loggingLevel, null);
    }

    public ApiClient(String baseUrl,
                     boolean followRedirect,
                     Converter.Factory converter,
                     HttpLoggingInterceptor.Level loggingLevel,
                     Interceptor... interceptors) {
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
        if (interceptors != null) {
            for (Interceptor interceptor : interceptors) {
                okHttpClientBuilder.addNetworkInterceptor(interceptor);
            }
        }

        this.okHttpClient = okHttpClientBuilder.addNetworkInterceptor(new HttpLoggingInterceptor().setLevel(loggingLevel))
                .followRedirects(followRedirect)
                .build();

        this.retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(converter)
                .client(this.okHttpClient)
                .build();
    }

    public ApiClient(String baseUrl, boolean followRedirect, Interceptor... interceptors) {
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
                .client(okHttpClient)
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
    }
}