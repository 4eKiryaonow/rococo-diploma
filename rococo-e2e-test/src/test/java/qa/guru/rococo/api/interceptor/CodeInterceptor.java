package qa.guru.rococo.api.interceptor;

import okhttp3.Interceptor;
import okhttp3.Response;
import org.apache.commons.lang3.StringUtils;
import qa.guru.rococo.jupiter.extension.ApiLoginExtension;
import qa.guru.rococo.jupiter.extension.ContextHolderExtension;

import java.io.IOException;

public class CodeInterceptor implements Interceptor {
  @Override
  public Response intercept(Chain chain) throws IOException {
    final Response response = chain.proceed(chain.request());
    if (response.isRedirect() && response.header("Location").contains("code=")) {
      ApiLoginExtension.setCode(
          ContextHolderExtension.Holder.INSTANCE.get(),
          StringUtils.substringAfter(
              response.header("Location"), "code="
          )
      );
    }
    return response;
  }
}
