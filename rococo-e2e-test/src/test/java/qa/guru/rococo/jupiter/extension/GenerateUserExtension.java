package qa.guru.rococo.jupiter.extension;

import org.junit.jupiter.api.extension.*;
import org.junit.platform.commons.support.AnnotationSupport;
import qa.guru.rococo.jupiter.annotation.ApiLogin;
import qa.guru.rococo.jupiter.annotation.TestUser;
import qa.guru.rococo.jupiter.annotation.User;
import qa.guru.rococo.model.UserJson;

import java.util.*;

public abstract class GenerateUserExtension implements BeforeEachCallback, ParameterResolver {

    public static final ExtensionContext.Namespace GENERATE_USER_NAMESPACE
            = ExtensionContext.Namespace.create(GenerateUserExtension.class);

    @Override
    public void beforeEach(ExtensionContext extensionContext) throws Exception {
        Map<User.Point, TestUser> usersForTest = extractUsersForTest(extensionContext);

        Map<User.Point, UserJson> createdUsers = new HashMap<>();
        for (Map.Entry<User.Point, TestUser> userInfo : usersForTest.entrySet()) {
            UserJson userForPoint = createUser(userInfo.getValue());
            createdUsers.put(userInfo.getKey(), userForPoint);
            }
        extensionContext.getStore(GENERATE_USER_NAMESPACE).put(extensionContext.getUniqueId(), createdUsers);
}


    public abstract UserJson createUser(TestUser user);

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return AnnotationSupport.findAnnotation(parameterContext.getParameter(), User.class).isPresent() &&
                parameterContext.getParameter().getType().isAssignableFrom(UserJson.class);
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        User user = AnnotationSupport.findAnnotation(parameterContext.getParameter(), User.class).get();
        Map<User.Point, UserJson> createdUsers = extensionContext.getStore(GENERATE_USER_NAMESPACE).get(extensionContext.getUniqueId(), Map.class);

        if (parameterContext.getParameter().getType().isAssignableFrom(UserJson.class)) {
            UserJson userJson = createdUsers.get(user.value());
            if (userJson == null) {
                throw new ParameterResolutionException("No UserJson found for the given user point: " + user.value());
            }
            return userJson;
        } else {
            throw new ParameterResolutionException("Unsupported parameter type: " + parameterContext.getParameter().getType());
        }
    }

    private Map<User.Point, TestUser> extractUsersForTest(ExtensionContext context) {
        Map<User.Point, TestUser> result = new HashMap<>();
        AnnotationSupport.findAnnotation(context.getRequiredTestMethod(), ApiLogin.class).ifPresent(
                apiLogin -> {
                    TestUser user = apiLogin.user();
                    if (!user.fake()) {
                        result.put(User.Point.INNER, user);
                    }
                }
        );

        AnnotationSupport.findAnnotation(context.getRequiredTestMethod(), TestUser.class).ifPresent(
                user -> {
                    if (!user.fake()) {
                        result.put(User.Point.OUTER, user);
                    };
                }
        );

        return result;
    }
}
